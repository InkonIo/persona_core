package kz.persona.core.controller.rest.open;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.persona.core.model.response.ImageUrlResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/images")
@Tag(name = "Image Controller", description = "Рест Контроллер для работы с фотографиями")
public class ImageController {

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;
    @Value("${amazon.s3.location}")
    private String bucketLocation;

    private final S3Client client;

    @Operation(
            summary = "Эндпойнт для загрузки фотографии",
            description = """
                    Файл должен отправляться в типе multipart/form-data и называться поле file
                                        
                    В конце генерирует ссылку типа https://s3.amazonaws.com/persona.com/987119ea-c4f3-478f-a716-0ef403d531b5
                    """)
    @ApiResponse(
            description = "Успешно загружена фотография",
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ImageUrlResponseDto.class)
            )
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUrlResponseDto> uploadImage(@RequestParam(name = "file") MultipartFile file) {
        try {
            String key = UUID.randomUUID().toString();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .key(key)
                    .build();
            client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return ResponseEntity.ok(new ImageUrlResponseDto(bucketLocation + key));
        } catch (Exception ex) {
            log.error("Error occurred while put object to s3, message: {}", ex.getMessage(), ex);
            return ResponseEntity.badRequest().build();
        }
    }

}
