package kz.persona.core.model.entity.dictionaries;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kz.persona.core.model.entity.mapped.DictModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "status")
@EqualsAndHashCode(callSuper = true)
public class Status extends DictModel {
}
