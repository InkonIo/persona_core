package kz.persona.core.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.request.RequestRequestDto;
import kz.persona.core.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
@Tag(name = "Request Controller", description = "Контроллер для работы с запросами пользователей, закрытый доступ оп JWT токену")
public class RequestController {

    private final RequestService requestService;

    @Operation(
            summary = "Эндпойнт для создание запроса")
    @ApiResponse(
            description = "Успешно",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping
    public ResponseEntity<MessageDto> createRequest(@RequestBody @Valid RequestRequestDto requestDto) {
        return ResponseEntity.ok(requestService.create(requestDto));
    }

}
