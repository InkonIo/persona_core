package kz.persona.core.service;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.search.*;
import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.mapper.ProfileDocumentMapper;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.entity.mongo.ProfileDocument;
import kz.persona.core.model.request.ProfileFilterRequestDto;
import kz.persona.core.model.response.ProfileFullResponseDto;
import kz.persona.core.model.response.ProfileResponseDto;
import kz.persona.core.repository.mongo.ProfileDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static kz.persona.core.constants.DocumentConstants.PROFILE_IDX_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileDocumentService {

    private final ProfileDocumentRepository profileDocumentRepository;

    private final UserService userService;

    private final MongoTemplate mongoTemplate;
    private final ProfileDocumentMapper profileDocumentMapper;

    @Transactional
    public void reindex() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            profileDocumentRepository.deleteAll();
            List<User> users = userService.findAll();
            List<ProfileDocument> documents = users.stream()
                    .map(profileDocumentMapper::map)
                    .toList();
            profileDocumentRepository.saveAll(documents);
        } catch (Exception ex) {
            log.error("Error while reindexing profile documents", ex);
        }
        stopWatch.stop();
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("Reindexing all profiles takes: {} seconds", totalTimeSeconds);
    }

    public Page<ProfileResponseDto> filter(ProfileFilterRequestDto request, String sort, Pageable pageable) {
        List<AggregationOperation> operations = new ArrayList<>(this.prepareExactFilters(request));
        operations.add(this.excludeCurrentUser());
        Long total = this.count(new ArrayList<>(operations));
        operations.add(this.addSort(sort));
        operations.add(Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()));
        operations.add(Aggregation.limit(pageable.getPageSize()));
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<ProfileResponseDto> results = mongoTemplate.aggregate(aggregation, PROFILE_IDX_NAME, ProfileResponseDto.class);
        List<ProfileResponseDto> mappedResults = results.getMappedResults();

        return new PageImpl<>(mappedResults, pageable, total);
    }

    private AggregationOperation addSort(String sortProperty) {
        List<Sort.Order> orders = new ArrayList<>();
        if ("RATING".equals(sortProperty)) {
            Sort.Order ratingOrder = Sort.Order.by(ProfileDocument.Fields.rating)
                    .with(Sort.Direction.DESC)
                    .nullsLast();
            Sort.Order ratingCountOrder = Sort.Order.by(ProfileDocument.Fields.ratingCount)
                    .with(Sort.Direction.DESC)
                    .nullsLast();
            orders.addAll(List.of(ratingOrder, ratingCountOrder));
        } else if ("STATUS".equals(sortProperty)) {
            orders.add(Sort.Order.by(ProfileDocument.Fields.status + "." + "_id")
                    .with(Sort.Direction.DESC));
        }
        orders.add(Sort.Order.by(ProfileDocument.Fields.createdAt)
                .with(Sort.Direction.DESC));
        Sort sort = Sort.by(orders);
        return Aggregation.sort(sort);
    }

    private AggregationOperation excludeCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return Aggregation.match(Criteria.where(ProfileDocument.Fields.internalId).ne(currentUser.getId()));
    }

    private List<AggregationOperation> prepareExactFilters(ProfileFilterRequestDto request) {
        List<AggregationOperation> operations = new ArrayList<>();
        AggregationOperation searchOperation = this.searchText(request);
        if (searchOperation != null) {
            operations.add(searchOperation);
        }

        DateOperators.DateDiff years = DateOperators.DateDiff.diffValue("$$NOW", "year")
                .toDateOf(ProfileDocument.Fields.dateOfBirth);
        DateOperators.DateDiff days = DateOperators.DateDiff.diffValue("$$NOW", "day")
                .toDateOf(ProfileDocument.Fields.createdAt);
        AddFieldsOperation addFields = Aggregation.addFields()
                .addField("age")
                .withValue(years)
                .addField("days")
                .withValue(days)
                .build();

        operations.add(addFields);

        operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.visible).is(Boolean.TRUE)));
        if (Objects.nonNull(request.getId())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.internalId).is(request.getId())));
        }
        if (Objects.nonNull(request.getStatusId())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.status + "." + "_id").is(request.getStatusId())));
        }
        if (Objects.nonNull(request.getCityId())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.city + "." + "_id").is(request.getCityId())));
        }
        if (Objects.nonNull(request.getCountryId())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.country + "." + "_id").is(request.getCountryId())));
        }
        if (Objects.nonNull(request.getWorkFieldId())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.workField + "." + "_id").is(request.getWorkFieldId())));
        }
        if (Objects.nonNull(request.getMaritalStatusId())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.maritalStatus + "." + "_id").is(request.getMaritalStatusId())));
        }
        if (CollectionUtils.isNotEmpty(request.getProfessionsIds())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.professions + "." + "_id").in(request.getProfessionsIds())));
        }
        if (Objects.nonNull(request.getSalaryFrom())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.salaryFrom).gte(request.getSalaryFrom())));
        }
        if (Objects.nonNull(request.getSalaryTo())) {
            operations.add(Aggregation.match(Criteria.where(ProfileDocument.Fields.salaryTo).lte(request.getSalaryTo())));
        }
        if (Objects.nonNull(request.getAgeFrom())) {
            operations.add(Aggregation.match(Criteria.where("age").gte(request.getAgeFrom())));
        }
        if (Objects.nonNull(request.getAgeTo())) {
            operations.add(Aggregation.match(Criteria.where("age").lte(request.getAgeTo())));
        }

        return operations;
    }

    private AggregationOperation searchBson(List<String> texts, List<String> fields) {
        if (CollectionUtils.isNotEmpty(texts) && CollectionUtils.isNotEmpty(fields)) {
            List<FieldSearchPath> fieldPaths = fields.stream()
                    .map(SearchPath::fieldPath)
                    .toList();
            Bson search = Aggregates.search(
                    SearchOperator.text(fieldPaths, texts)
                            .fuzzy(FuzzySearchOptions.fuzzySearchOptions()
                                    .maxExpansions(50)
                                    .prefixLength(0)
                                    .maxEdits(1)),
                    SearchOptions.searchOptions()
                            .index("default")
                            .count(SearchCount.total()));
            return Aggregation.stage(search);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Long count(List<AggregationOperation> operations) {
        operations.add(Aggregation.count().as("cnt"));
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<ProfileDocument> results = mongoTemplate.aggregate(aggregation, PROFILE_IDX_NAME, ProfileDocument.class);
        Integer count = Optional.of(results)
                .map(AggregationResults::getRawResults)
                .map(e -> e.get("results"))
                .filter(ArrayList.class::isInstance)
                .map(e -> (ArrayList<Document>) e)
                .orElse(new ArrayList<>())
                .stream()
                .findFirst()
                .map(e -> e.get("cnt"))
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .orElse(0);
        return count.longValue();
    }

    public ProfileFullResponseDto getUserInfo(Long id) {
        ProfileFilterRequestDto requestDto = new ProfileFilterRequestDto();
        requestDto.setId(id);
        List<AggregationOperation> operations = new ArrayList<>(this.prepareExactFilters(requestDto));
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<ProfileFullResponseDto> results = mongoTemplate.aggregate(aggregation, PROFILE_IDX_NAME, ProfileFullResponseDto.class);
        List<ProfileFullResponseDto> mappedResults = results.getMappedResults();
        if (mappedResults.isEmpty()) {
            throw new PersonaCoreException("Пользователь не найден");
        }
        return mappedResults.get(0);
    }

    @Transactional
    public void insertUser(Long userId) {
        User user = userService.getById(userId);
        ProfileDocument profileDocument = profileDocumentMapper.map(user);
        profileDocumentRepository.save(profileDocument);
    }

    private AggregationOperation searchText(ProfileFilterRequestDto request) {
        List<String> fields = new ArrayList<>();
        List<String> queries = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getFullName())) {
            fields.add(ProfileDocument.Fields.fullName);
            queries.add(request.getFullName());
        }
        if (StringUtils.isNotBlank(request.getEducation())) {
            fields.add(ProfileDocument.Fields.education);
            queries.add(request.getEducation());
        }
        if (StringUtils.isNotBlank(request.getSkills())) {
            fields.add(ProfileDocument.Fields.skills);
            queries.add(request.getSkills());
        }
        if (StringUtils.isNotBlank(request.getDreamWork())) {
            fields.add(ProfileDocument.Fields.dreamWork);
            queries.add(request.getDreamWork());
        }
        if (StringUtils.isNotBlank(request.getHobby())) {
            fields.add(ProfileDocument.Fields.hobby);
            queries.add(request.getHobby());
        }
        return this.searchBson(queries, fields);
    }
}
