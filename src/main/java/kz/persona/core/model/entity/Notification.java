package kz.persona.core.model.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import kz.persona.core.model.entity.mapped.AuditableModel;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Map;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
@EqualsAndHashCode(callSuper = true)
public class Notification extends AuditableModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Type(value = JsonBinaryType.class)
    @Column(name = "data", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> data;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = Boolean.TRUE;

    @Builder.Default
    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;
}
