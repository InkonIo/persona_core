package kz.persona.core.repository;

import kz.persona.core.model.entity.Rating;
import kz.persona.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByFromUserAndToUser(User fromUser, User toUser);

    @Modifying
    @Transactional
    @Query(value = "delete from Rating r where r.fromUser = :user or r.toUser = :user")
    void deleteAllByUser(@Param(value = "user") User user);
}
