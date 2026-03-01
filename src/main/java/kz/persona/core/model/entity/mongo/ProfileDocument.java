package kz.persona.core.model.entity.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Id;
import kz.persona.core.model.response.dictionaries.DictDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static kz.persona.core.constants.DocumentConstants.PROFILE_IDX_NAME;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Document(collection = PROFILE_IDX_NAME)
public class ProfileDocument {

    @Id
    private String id;

    private Long internalId;

    private String login;

    private String email;

    private String fullName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    private DictDto city;

    private DictDto region;

    private DictDto country;

    private DictDto workField;

    private Set<DictDto> professions;

    private DictDto status;

    private DictDto maritalStatus;

    private String education;

    private String skills;

    private Long salaryFrom;

    private Long salaryTo;

    private String dreamWork;

    private String hobby;

    private String imageUrl;

    private String socialLinks;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    private Boolean visible;

    private Boolean isMentor = Boolean.FALSE;

    private Boolean hasSubscription = Boolean.FALSE;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal rating;

    private Integer ratingCount;

}
