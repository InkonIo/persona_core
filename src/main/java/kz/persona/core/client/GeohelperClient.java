package kz.persona.core.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeohelperClient {

    private static final String API_KEY = "rFR8jRqgnAmhBS9Zvlaz4bqorm1c7CGA";
    private static final String BASE_URL = "https://geohelper.info/api/v1";

    private final RestTemplate restTemplate;

    public List<GeoCountry> fetchCountries() {
        try {
            String url = BASE_URL + "/countries?apiKey=" + API_KEY
                    + "&locale[lang]=ru&locale[fallbackLang]=en&pagination[limit]=300";
            GeoResponse<GeoCountry> response = restTemplate.getForObject(url, GeoCountryResponse.class);
            if (response != null && response.isSuccess()) {
                return response.getResult();
            }
        } catch (Exception e) {
            log.error("Ошибка при загрузке стран из geohelper: {}", e.getMessage());
        }
        return List.of();
    }

    public List<GeoRegion> fetchRegionsByCountry(String countryIso, String lang) {
        try {
            String url = BASE_URL + "/regions?apiKey=" + API_KEY
                    + "&locale[lang]=" + lang
                    + "&locale[fallbackLang]=ru"
                    + "&filter[countryIso]=" + countryIso
                    + "&pagination[limit]=200";
            GeoRegionResponse response = restTemplate.getForObject(url, GeoRegionResponse.class);
            if (response != null && response.isSuccess()) {
                return response.getResult();
            }
        } catch (Exception e) {
            log.error("Ошибка при загрузке регионов из geohelper: {}", e.getMessage());
        }
        return List.of();
    }

    public List<GeoCity> fetchCitiesByRegion(Long regionId, String lang) {
        try {
            String url = BASE_URL + "/cities?apiKey=" + API_KEY
                    + "&locale[lang]=" + lang
                    + "&locale[fallbackLang]=ru"
                    + "&filter[regionId]=" + regionId
                    + "&pagination[limit]=200";
            GeoCityResponse response = restTemplate.getForObject(url, GeoCityResponse.class);
            if (response != null && response.isSuccess()) {
                return response.getResult();
            }
        } catch (Exception e) {
            log.error("Ошибка при загрузке городов из geohelper: {}", e.getMessage());
        }
        return List.of();
    }

    // Base response
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoResponse<T> {
        private boolean success;
        private List<T> result;
    }

    // Typed responses
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoCountryResponse extends GeoResponse<GeoCountry> {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoRegionResponse extends GeoResponse<GeoRegion> {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoCityResponse extends GeoResponse<GeoCity> {}

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoCountry {
        private Long id;
        private String name;
        private String iso;
        private Map<String, String> localizedNames;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoRegion {
        private Long id;
        private String name;
        private String iso;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoCity {
        private Long id;
        private String name;
    }
}