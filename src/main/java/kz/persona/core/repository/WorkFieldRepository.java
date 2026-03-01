package kz.persona.core.repository;

import kz.persona.core.model.entity.dictionaries.WorkField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkFieldRepository extends JpaRepository<WorkField, Long> {
}
