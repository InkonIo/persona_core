package kz.persona.core.controller.rest.open;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.persona.core.facade.UserFacade;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.request.UserLoginRefreshRequestDto;
import kz.persona.core.model.request.UserLoginRequestDto;
import kz.persona.core.model.request.UserUpsertRequestDto;
import kz.persona.core.model.response.UserLoginResponseDto;
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
@RequestMapping("/public/users")
@Tag(name = "Public User Controller", description = "Контроллер для работы с пользователями с открытым доступом")
public class PublicUserController {

    private final UserFacade userFacade;

    @Operation(
            summary = "Эндпойнт для создания пользователя")
    @ApiResponse(
            description = "Пользователь успешно создан",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping(value = "/create")
    public ResponseEntity<MessageDto> createUser(@RequestBody @Valid UserUpsertRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.createUser(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для авторизации пользователя",
            description = """
                    ## Поле login не должно быть пустым
                    ## Поле password не должно быть пустым
                    """)
    @ApiResponse(
            description = "Успешно авторизовался",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = UserLoginResponseDto.class)
            )
    )
    @PostMapping(value = "/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.login(requestDto));
    }

    @Operation(
            summary = "Эндпойнт для обновления токена",
            description = """
                    ## Поле refresh_token не должно быть пустым
                    """)
    @ApiResponse(
            description = "Успешно обновлен токен",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = UserLoginResponseDto.class)
            )
    )
    @PostMapping(value = "/refresh")
    public ResponseEntity<UserLoginResponseDto> refresh(@RequestBody @Valid UserLoginRefreshRequestDto requestDto) {
        return ResponseEntity.ok(userFacade.refresh(requestDto));
    }
}
