package kz.persona.core.model.entity;

import jakarta.persistence.*;
import kz.persona.core.model.entity.mapped.AuditableModel;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
@EqualsAndHashCode(callSuper = true)
public class Rating extends AuditableModel {

    @Column(name = "value")
    private Short value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    private User toUser;

}
