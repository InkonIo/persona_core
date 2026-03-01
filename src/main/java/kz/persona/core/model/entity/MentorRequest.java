package kz.persona.core.model.entity;

import jakarta.persistence.*;
import kz.persona.core.model.entity.mapped.AuditableModel;
import kz.persona.core.model.enumaration.MentorRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mentor_requests")
@EqualsAndHashCode(callSuper = true)
public class MentorRequest extends AuditableModel {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MentorRequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    private User toUser;

}
