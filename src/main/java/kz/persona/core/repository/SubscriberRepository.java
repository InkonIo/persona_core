package kz.persona.core.repository;

import kz.persona.core.model.entity.Subscriber;
import kz.persona.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    Optional<Subscriber> findByFromUserAndToUser(User fromUser, User toUser);

    @Modifying
    @Transactional
    @Query(value = "delete from Subscriber s where s.fromUser = :user or s.toUser = :user")
    void deleteAllByUser(@Param(value = "user") User user);
}
