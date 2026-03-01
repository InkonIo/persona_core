package kz.persona.core.service;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.entity.Rating;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.request.RatingRequestDto;
import kz.persona.core.model.request.UserCheckRatingRequestDto;
import kz.persona.core.model.response.RatingDto;
import kz.persona.core.model.response.RatingSummaryResponseDto;
import kz.persona.core.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    private final UserService userService;

    @Transactional
    public MessageDto createRating(RatingRequestDto requestDto) {
        User toUser = userService.getById(requestDto.toUser());
        User fromUser = userService.getCurrentUser();
        if (toUser.getId().equals(fromUser.getId())) {
            throw new PersonaCoreException("Нельзя поставить рейтинг на самого себя");
        }
        Rating rating = ratingRepository.findByFromUserAndToUser(fromUser, toUser)
                .orElseGet(() -> Rating.builder()
                        .fromUser(fromUser)
                        .toUser(toUser)
                        .build());
        rating.setValue(requestDto.value().shortValue());
        ratingRepository.save(rating);
        return new MessageDto("Успешно выставлен");
    }

    @Transactional(readOnly = true)
    public RatingSummaryResponseDto getUserRatings(Long userId) {
        RatingSummaryResponseDto summary = new RatingSummaryResponseDto();
        User source = userService.getById(userId);
        List<Rating> ratings = source.getRatings();
        int size = ratings.size();
        double rating = ratings.stream()
                .mapToInt(Rating::getValue)
                .average()
                .orElse(0);
        List<RatingDto> ratingDtos = ratings.stream()
                .map(RatingDto::new)
                .toList();

        summary.setTotalRating(BigDecimal.valueOf(rating)
                .multiply(BigDecimal.valueOf(10))
                .setScale(2, RoundingMode.HALF_UP));
        summary.setTotalRatingCount(size);
        summary.setRatings(ratingDtos);
        return summary;
    }

    public void deleteAllByUser(User user) {
        ratingRepository.deleteAllByUser(user);
    }

    public RatingDto checkUserRating(UserCheckRatingRequestDto requestDto) {
        User toUser = userService.getById(requestDto.toUserId());
        User currentUser = userService.getCurrentUser();
        return ratingRepository.findByFromUserAndToUser(currentUser, toUser)
                .map(RatingDto::new)
                .orElse(null);
    }
}
