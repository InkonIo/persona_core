package kz.persona.core.model.enumaration;

import java.util.Arrays;

public enum DictionaryType {
    CITY,
    REGION,
    COUNTRY,
    MARITAL_STATUS,
    PROFESSION,
    STATUS,
    WORK_FIELD;

    public static DictionaryType parse(String type) {
        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }

}
