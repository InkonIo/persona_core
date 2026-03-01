package kz.persona.core.model.entity;

import jakarta.persistence.*;
import kz.persona.core.model.entity.mapped.AuditableModel;
import kz.persona.core.model.enumaration.RequestStatus;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
@EqualsAndHashCode(callSuper = true)
public class Request extends AuditableModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
