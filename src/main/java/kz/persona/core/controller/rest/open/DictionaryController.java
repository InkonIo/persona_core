package kz.persona.core.controller.rest.open;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.enumaration.DictionaryType;
import kz.persona.core.model.response.dictionaries.DictDto;
import kz.persona.core.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/dictionary")
@Tag(name = "Dictionary Controller", description = "Рест Контроллер для работы со справочниками")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/{type}")
    public ResponseEntity<List<DictDto>> getDictionary(@PathVariable String type) {
        DictionaryType dictionaryType = DictionaryType.parse(type.toUpperCase());
        if (dictionaryType == null) {
            throw new PersonaCoreException("Нет такого типа справочника");
        }
        return ResponseEntity.ok(dictionaryService.getByType(dictionaryType));
    }

    @GetMapping("/city/filter")
    public ResponseEntity<List<DictDto>> getCitiesByRegion(
            @RequestParam(name = "regionId") Long regionId,
            @RequestParam(name = "lang", defaultValue = "ru") String lang) {
        return ResponseEntity.ok(dictionaryService.getCitiesByRegion(regionId, lang));
    }

    @GetMapping("/region/filter")
    public ResponseEntity<List<DictDto>> getRegionsByCountry(
            @RequestParam(name = "countryId") Long countryId,
            @RequestParam(name = "lang", defaultValue = "ru") String lang) {
        return ResponseEntity.ok(dictionaryService.getRegionsByCountry(countryId, lang));
    }
}