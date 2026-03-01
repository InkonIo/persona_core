package kz.persona.core.repository;

import kz.persona.core.model.entity.dictionaries.City;
import kz.persona.core.model.entity.dictionaries.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByRegion(Region region);

}
