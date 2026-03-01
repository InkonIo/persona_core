package kz.persona.core.service;

import kz.persona.core.client.GeohelperClient;
import kz.persona.core.model.entity.dictionaries.Country;
import kz.persona.core.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountrySyncService implements ApplicationRunner {

    private final GeohelperClient geohelperClient;
    private final CountryRepository countryRepository;

    private static final Map<String, String> KZ_NAMES = Map.ofEntries(
        Map.entry("RU", "Ресей"),
        Map.entry("KZ", "Қазақстан"),
        Map.entry("US", "АҚШ"),
        Map.entry("DE", "Германия"),
        Map.entry("FR", "Франция"),
        Map.entry("GB", "Ұлыбритания"),
        Map.entry("CN", "Қытай"),
        Map.entry("TR", "Түркия"),
        Map.entry("AE", "БАӘ"),
        Map.entry("IT", "Италия"),
        Map.entry("ES", "Испания"),
        Map.entry("JP", "Жапония"),
        Map.entry("KR", "Оңтүстік Корея"),
        Map.entry("IN", "Үндістан"),
        Map.entry("BR", "Бразилия"),
        Map.entry("CA", "Канада"),
        Map.entry("AU", "Австралия"),
        Map.entry("UA", "Украина"),
        Map.entry("BY", "Беларусь"),
        Map.entry("UZ", "Өзбекстан"),
        Map.entry("KG", "Қырғызстан"),
        Map.entry("TJ", "Тәжікстан"),
        Map.entry("TM", "Түрікменстан"),
        Map.entry("AZ", "Әзірбайжан"),
        Map.entry("GE", "Грузия"),
        Map.entry("AM", "Армения"),
        Map.entry("PL", "Польша"),
        Map.entry("CZ", "Чехия"),
        Map.entry("AT", "Австрия"),
        Map.entry("CH", "Швейцария"),
        Map.entry("NL", "Нидерланды"),
        Map.entry("PT", "Португалия"),
        Map.entry("SE", "Швеция"),
        Map.entry("NO", "Норвегия"),
        Map.entry("FI", "Финляндия"),
        Map.entry("DK", "Дания"),
        Map.entry("MX", "Мексика"),
        Map.entry("AR", "Аргентина"),
        Map.entry("EG", "Египет"),
        Map.entry("ZA", "Оңтүстік Африка"),
        Map.entry("NG", "Нигерия"),
        Map.entry("SA", "Сауд Арабиясы"),
        Map.entry("IR", "Иран"),
        Map.entry("IQ", "Ирак"),
        Map.entry("PK", "Пәкістан"),
        Map.entry("TH", "Тайланд"),
        Map.entry("VN", "Вьетнам"),
        Map.entry("ID", "Индонезия"),
        Map.entry("MY", "Малайзия"),
        Map.entry("SG", "Сингапур")
    );

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Начинаем синхронизацию стран из geohelper...");
        List<GeohelperClient.GeoCountry> geoCountries = geohelperClient.fetchCountries();

        if (geoCountries.isEmpty()) {
            log.warn("Geohelper вернул пустой список стран");
            return;
        }

        // Получаем существующие коды стран
        Set<String> existingCodes = countryRepository.findAll().stream()
            .map(Country::getCode)
            .collect(Collectors.toSet());

        // Маппим и фильтруем только новые страны
        List<Country> newCountries = geoCountries.stream()
            .filter(geo -> !existingCodes.contains(geo.getIso()))
            .map(geo -> {
                Country country = new Country();
                country.setCode(geo.getIso());
                country.setName(geo.getName());

                Map<String, String> localized = geo.getLocalizedNames();
                String nameRu = localized != null ? localized.getOrDefault("ru", geo.getName()) : geo.getName();
                String nameEn = localized != null ? localized.getOrDefault("en", geo.getName()) : geo.getName();
                String nameKz = KZ_NAMES.getOrDefault(geo.getIso(), nameRu);

                country.setNameRu(nameRu);
                country.setNameEn(nameEn);
                country.setNameKz(nameKz);

                return country;
            })
            .toList();

        if (!newCountries.isEmpty()) {
            countryRepository.saveAll(newCountries);
            log.info("Синхронизация завершена, добавлено {} новых стран", newCountries.size());
        } else {
            log.info("Все страны уже загружены, ничего не добавлено");
        }
    }
}