package kz.persona.core.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.request.ProfileFilterRequestDto;
import kz.persona.core.model.response.ProfileFullResponseDto;
import kz.persona.core.model.response.ProfileResponseDto;
import kz.persona.core.service.ProfileDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/docs/profile")
@Tag(name = "Profile Controller", description = "Mongo Рест Контроллер для работы с профайлами")
public class ProfileController {

    private final ProfileDocumentService profileDocumentService;

    @Operation(summary = "Эндпойнт для реиндексации профилей пользователей в Mongo")
    @ApiResponse(
            description = "Успешно обновлены профили пользователей",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class)
            )
    )
    @PostMapping(value = "/reindex")
    public ResponseEntity<MessageDto> reindexAllProfiles() {
        profileDocumentService.reindex();
        return ResponseEntity.ok(new MessageDto("Успешно обновлено"));
    }

    @Operation(
            summary = "Эндпойнт для фильтрации профилей пользователей",
            parameters = {
                    @Parameter(name = "page", description = "Страница"),
                    @Parameter(name = "size", description = "Кол-во элементов на странице")
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(implementation = ProfileFilterRequestDto.class)
            )
    )
    @PostMapping("/filter")
    public ResponseEntity<Page<ProfileResponseDto>> filter(@RequestBody ProfileFilterRequestDto request,
                                                           @RequestParam(name = "sort", required = false) String sort,
                                                           @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(profileDocumentService.filter(request, sort, pageable));
    }

    @Operation(
            summary = "Эндпойнт для получения данных о пользователе по идентификатор")
    @ApiResponse(
            description = "Данные о пользователе успешно получены",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ProfileFullResponseDto.class)
            )
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProfileFullResponseDto> getUserInfo(
            @Parameter(description = "Идентификатор пользователя", example = "23")
            @PathVariable Long id) {
        return ResponseEntity.ok(profileDocumentService.getUserInfo(id));
    }

}
