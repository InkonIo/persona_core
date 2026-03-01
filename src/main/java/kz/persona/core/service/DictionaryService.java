package kz.persona.core.service;

import kz.persona.core.client.GeohelperClient;
import kz.persona.core.mapper.DictionaryMapper;
import kz.persona.core.model.entity.dictionaries.City;
import kz.persona.core.model.entity.dictionaries.Country;
import kz.persona.core.model.entity.dictionaries.Region;
import kz.persona.core.model.entity.mapped.DictModel;
import kz.persona.core.model.enumaration.DictionaryType;
import kz.persona.core.model.response.dictionaries.DictDto;
import kz.persona.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DictionaryService {

    private final CityRepository cityRepository;
    private final StatusRepository statusRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final WorkFieldRepository workFieldRepository;
    private final ProfessionRepository professionRepository;
    private final MaritalStatusRepository maritalStatusRepository;
    private final DictionaryMapper dictionaryMapper;
    private final GeohelperClient geohelperClient;

    public List<DictDto> getByType(DictionaryType type) {
        return dictionaryMapper.map(findByType(type));
    }

    @Transactional
    public List<DictDto> getRegionsByCountry(Long countryId, String lang) {
        Country country = countryRepository.getReferenceById(countryId);

        List<Region> dbRegions = regionRepository.findAllByCountry(country);
        if (!dbRegions.isEmpty()) {
            return dbRegions.stream().map(r -> toDto(r, lang)).toList();
        }

        // Тянем на трёх языках
        List<GeohelperClient.GeoRegion> regionsRu = geohelperClient.fetchRegionsByCountry(country.getCode(), "ru");
        List<GeohelperClient.GeoRegion> regionsEn = geohelperClient.fetchRegionsByCountry(country.getCode(), "en");
        List<GeohelperClient.GeoRegion> regionsKz = geohelperClient.fetchRegionsByCountry(country.getCode(), "kk");

        Map<Long, String> enNames = regionsEn.stream()
            .collect(Collectors.toMap(GeohelperClient.GeoRegion::getId, GeohelperClient.GeoRegion::getName));
        Map<Long, String> kzNames = regionsKz.stream()
            .collect(Collectors.toMap(GeohelperClient.GeoRegion::getId, GeohelperClient.GeoRegion::getName));

        List<Region> toSave = regionsRu.stream().map(r -> {
            Region region = new Region();
            region.setCode(r.getIso() != null ? r.getIso() : String.valueOf(r.getId()));
            region.setName(r.getName());
            region.setNameRu(r.getName());
            region.setNameEn(enNames.getOrDefault(r.getId(), r.getName()));
            region.setNameKz(kzNames.getOrDefault(r.getId(), r.getName()));
            region.setCountry(country);
            return region;
        }).toList();

        regionRepository.saveAll(toSave);

        return toSave.stream().map(r -> toDto(r, lang)).toList();
    }

    @Transactional
    public List<DictDto> getCitiesByRegion(Long regionId, String lang) {
        Region region = regionRepository.getReferenceById(regionId);

        var dbCities = cityRepository.findAllByRegion(region);
        if (!dbCities.isEmpty()) {
            return dbCities.stream().map(c -> toDto(c, lang)).toList();
        }

        // Тянем на трёх языках
        List<GeohelperClient.GeoCity> citiesRu = geohelperClient.fetchCitiesByRegion(regionId, "ru");
        List<GeohelperClient.GeoCity> citiesEn = geohelperClient.fetchCitiesByRegion(regionId, "en");
        List<GeohelperClient.GeoCity> citiesKz = geohelperClient.fetchCitiesByRegion(regionId, "kk");

        Map<Long, String> enNames = citiesEn.stream()
            .collect(Collectors.toMap(GeohelperClient.GeoCity::getId, GeohelperClient.GeoCity::getName));
        Map<Long, String> kzNames = citiesKz.stream()
            .collect(Collectors.toMap(GeohelperClient.GeoCity::getId, GeohelperClient.GeoCity::getName));

        List<City> toSave = citiesRu.stream().map(c -> {
            City city = new City();
            city.setCode(String.valueOf(c.getId()));
            city.setName(c.getName());
            city.setNameRu(c.getName());
            city.setNameEn(enNames.getOrDefault(c.getId(), c.getName()));
            city.setNameKz(kzNames.getOrDefault(c.getId(), c.getName()));
            city.setRegion(region);
            return city;
        }).toList();

        cityRepository.saveAll(toSave);

        return toSave.stream().map(c -> toDto(c, lang)).toList();
    }

    private DictDto toDto(DictModel model, String lang) {
        DictDto dto = dictionaryMapper.map(model);
        if ("en".equals(lang) && model.getNameEn() != null) dto.setName(model.getNameEn());
        else if ("kz".equals(lang) && model.getNameKz() != null) dto.setName(model.getNameKz());
        else if (model.getNameRu() != null) dto.setName(model.getNameRu());
        return dto;
    }

    private String toGeoLang(String lang) {
        if (lang == null) return "ru";
        return switch (lang) {
            case "en" -> "en";
            case "kz" -> "kk";
            default -> "ru";
        };
    }

    private List<? extends DictModel> findByType(DictionaryType type) {
        return switch (type) {
            case CITY -> cityRepository.findAll();
            case STATUS -> statusRepository.findAll();
            case REGION -> regionRepository.findAll();
            case COUNTRY -> countryRepository.findAll();
            case WORK_FIELD -> workFieldRepository.findAll();
            case PROFESSION -> professionRepository.findAll();
            case MARITAL_STATUS -> maritalStatusRepository.findAll();
        };
    }
}