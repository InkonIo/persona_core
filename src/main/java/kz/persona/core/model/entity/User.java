package kz.persona.core.model.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import kz.persona.core.model.entity.dictionaries.*;
import kz.persona.core.model.entity.jsonb.SalaryBound;
import kz.persona.core.model.entity.mapped.AuditableModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends AuditableModel {

    @Column
    private String login;

    @Column
    private String keycloakId;

    @Column
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_field_id")
    private WorkField workField;

    @ManyToMany
    @JoinTable(name = "users_professions",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profession_id", referencedColumnName = "id"))
    private Set<Profession> professions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marital_status_id")
    private MaritalStatus maritalStatus;

    @Column
    private String education;

    @Column
    private String skills;

    @Type(value = JsonBinaryType.class)
    @Column(name = "salary", columnDefinition = "jsonb", nullable = false)
    private SalaryBound salary;

    @Column(name = "dream_work")
    private String dreamWork;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "social_links")
    private String socialLinks;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "is_mentor")
    private Boolean isMentor = Boolean.FALSE;

    @OneToMany(mappedBy = "toUser")
    private List<Rating> ratings;

}
