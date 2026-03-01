package kz.persona.core.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.persona.core.facade.UserFacade;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.request.*;
import kz.persona.core.model.response.MentorRequestStatusResponseDto;
import kz.persona.core.model.response.SubscriptionStatusResponseDto;
import kz.persona.core.model.response.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Контроллер для работы с пользователями, закрытый доступ оп JWT токену")
public class UserController {

    private final UserFacade userFacade;

    @Operation(
            summary = "Эндпойнт для получения данных о пользователе",
            description = """
                    ## Использует информацию из Bearer jwt токена
                    """)
    @ApiResponse(
            description = "Данные о пользователе успешно получены",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = UserInfoResponseDto.class)
            )
    )
    @GetMapping(value = "/me")
    public ResponseEntity<UserInfoResponseDto> userInfo() {
        return ResponseEntity.ok(userFacade.getCurrentUser());
    }

    @Operation(
            summary = "Эндпойнт для изменения данных о пользователе",
            description = """
                    # Использовать только те поля которые были изменены или отправлять в изначальном виде
                                        
                    ## Текущего пользователя берет из Bearer jwt токена и сам определяет user id
                    """)
    @ApiResponse(
            description = "Данные о пользователе успешно изменены",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PutMapping
    public ResponseEntity<MessageDto> updateUserInfo(@RequestBody UserUpsertRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.updateUser(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для изменения видимости профиля",
            description = """
                    Изменять видимость поля с true -> false и наоборот
                    """)
    @ApiResponse(
            description = "Данные о пользователе успешно изменены",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping("/visible")
    public ResponseEntity<MessageDto> toggleVisibility() {
        return ResponseEntity.ok(userFacade.changeVisibility());
    }

    @Operation(
            summary = "Эндпойнт для подписки к пользователю")
    @ApiResponse(
            description = "Успешно подписан на пользователя",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping(value = "/subscribe")
    public ResponseEntity<MessageDto> subscribeToUser(@RequestBody @Valid SubscribeRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.subscribeToUser(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для отписки от пользователю")
    @ApiResponse(
            description = "Успешно отписан от пользователя",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping(value = "/unsubscribe")
    public ResponseEntity<MessageDto> unsubscribeToUser(@RequestBody @Valid SubscribeRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.unsubscribeToUser(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для запроса на менторство")
    @ApiResponse(
            description = "Успешно отправлен запрос",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping(value = "/request-mentor")
    public ResponseEntity<MessageDto> requestMentor(@RequestBody @Valid MentorRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.requestMentor(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для отмены запросы на наставничество")
    @ApiResponse(
            description = "Успешно отписан от пользователя",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping(value = "/cancel-request-mentor")
    public ResponseEntity<MessageDto> cancelRequestMentor(@RequestBody @Valid MentorRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.cancelRequestMentor(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для проверки статуса подписчика")
    @ApiResponse(
            description = "Успешно получен ответ",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = SubscriptionStatusResponseDto.class)
            )
    )
    @PostMapping(value = "/check-subscription")
    public ResponseEntity<SubscriptionStatusResponseDto> checkSubscriptionToProfile(
            @RequestBody @Valid SubscriptionStatusRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.checkSubscription(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для проверки запросы на наставничество")
    @ApiResponse(
            description = "Успешно отписан от пользователя",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MentorRequestStatusResponseDto.class)
            )
    )
    @PostMapping(value = "/check-request-mentor")
    public ResponseEntity<MentorRequestStatusResponseDto> checkRequestMentor(@RequestBody @Valid MentorRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.checkRequestMentor(requestDto));
    }

    @Operation(summary = "Эндпойнт для регистрации токена пользователя")
    @ApiResponse(
            description = "Успешно",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping(value = "/register-token")
    public ResponseEntity<MessageDto> registerPushToken(@RequestBody @Valid RegisterTokenRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.registerToken(requestDto));
    }

    @Operation(summary = "Эндпойнт для удаления пользователя")
    @ApiResponse(
            description = "Успешно",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userFacade.delete(id));
    }

    @GetMapping("/{id}/push-token")
    public ResponseEntity<List<String>> userPushToken(@PathVariable Long id) {
        return ResponseEntity.ok(userFacade.getUserPushToken(id));
    }

}
