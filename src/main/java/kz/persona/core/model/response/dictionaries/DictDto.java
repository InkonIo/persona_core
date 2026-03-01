package kz.persona.core.model.response.dictionaries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictDto {

    @Schema(description = "Идентификатор справочника", example = "1")
    private Long id;

    @Schema(description = "Название справочника", example = "Алматы")
    private String name;

    @Schema(description = "Код справочника", example = "ALMATY")
    private String code;

    @Schema(description = "Название на русском", example = "Алматы")
    private String nameRu;

    @Schema(description = "Название на английском", example = "Almaty")
    private String nameEn;

    @Schema(description = "Название на казахском", example = "Алматы")
    private String nameKz;

}