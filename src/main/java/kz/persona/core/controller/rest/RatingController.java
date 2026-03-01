package kz.persona.core.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.request.RatingRequestDto;
import kz.persona.core.model.request.UserCheckRatingRequestDto;
import kz.persona.core.model.response.RatingDto;
import kz.persona.core.model.response.RatingSummaryResponseDto;
import kz.persona.core.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ratings")
@Tag(
        name = "Rating Controller",
        description = "Контроллер для работы с рейтингами пользователей, закрытый доступ оп JWT токену"
)
public class RatingController {

    private final RatingService ratingService;

    @Operation(
            summary = "Эндпойнт для выставления рейтинга")
    @ApiResponse(
            description = "Успешно рейтинг поставлен",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping
    public ResponseEntity<MessageDto> createRating(@RequestBody @Valid RatingRequestDto requestDto) {
        return ResponseEntity.ok(ratingService.createRating(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для получения рейтинга")
    @ApiResponse(
            description = "Успешно рейтинг поставлен",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = RatingSummaryResponseDto.class)
            )
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<RatingSummaryResponseDto> getUserRatings(
            @Parameter(description = "Идентификатор пользователя", example = "23")
            @PathVariable(name = "id") Long userId) {
        return ResponseEntity.ok(ratingService.getUserRatings(userId));
    }

    @PostMapping("/user-check")
    public ResponseEntity<RatingDto> checkUserRating(@RequestBody UserCheckRatingRequestDto requestDto) {
        return ResponseEntity.ok(ratingService.checkUserRating(requestDto));
    }

}
