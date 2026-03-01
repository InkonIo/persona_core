package kz.persona.core.model.entity;

import jakarta.persistence.*;
import kz.persona.core.model.entity.mapped.AuditableModel;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_device_tokens")
@EqualsAndHashCode(callSuper = true)
public class UserDeviceToken extends AuditableModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token")
    private String token;

    @Builder.Default
    @Column(name = "enabled")
    private Boolean enabled = Boolean.TRUE;

}
