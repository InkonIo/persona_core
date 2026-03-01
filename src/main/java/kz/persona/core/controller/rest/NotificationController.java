package kz.persona.core.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.persona.core.facade.NotificationFacade;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.request.NotificationRequestDto;
import kz.persona.core.model.request.NotificationSeenRequestDto;
import kz.persona.core.model.response.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@Tag(name = "Notification Controller", description = "Рест Контроллер для работы с уведомлениями")
public class NotificationController {

    private final NotificationFacade notificationFacade;

    @Operation(summary = "Эндпойнт для отправки пуш уведомления пользователю")
    @ApiResponse(
            description = "Успешно",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping("/push")
    public ResponseEntity<MessageDto> sendPush(@RequestBody NotificationRequestDto requestDto) {
        return ResponseEntity.ok(notificationFacade.sendNotification(requestDto));
    }

    @Operation(summary = "Эндпойнт для получения уведомления пользователя")
    @ApiResponse(
            description = "Успешно",
            responseCode = "200",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = NotificationResponseDto.class)
                    )
            )
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDto>> getUserNotifications(
            @Parameter(description = "Идентификатор пользователя", example = "23")
            @PathVariable Long userId) {
        return ResponseEntity.ok(notificationFacade.getUserNotifications(userId));
    }

    @Operation(summary = "Эндпойнт для прочтение уведомлений пользователя")
    @ApiResponse(
            description = "Успешно",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping("/seen")
    public ResponseEntity<MessageDto> readUserNotification(
            @Parameter(description = "Идентификатор уведомления", example = "23")
            @RequestBody NotificationSeenRequestDto requestDto) {
        return ResponseEntity.ok(notificationFacade.readNotifications(requestDto));
    }

}
