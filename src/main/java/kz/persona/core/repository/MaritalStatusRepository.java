package kz.persona.core.repository;

import kz.persona.core.model.entity.dictionaries.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long> {
}
