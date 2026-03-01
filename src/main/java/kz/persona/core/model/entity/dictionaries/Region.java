package kz.persona.core.model.entity.dictionaries;

import jakarta.persistence.*;
import kz.persona.core.model.entity.mapped.DictModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "regions")
@EqualsAndHashCode(callSuper = true)
public class Region extends DictModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

}
