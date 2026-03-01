package kz.persona.core.repository;

import kz.persona.core.model.entity.Notification;
import kz.persona.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserAndDeletedIsFalseOrderByCreatedAtDesc(User user);

    @Modifying
    @Transactional
    @Query(value = "delete from Notification n where n.user = :user")
    void deleteAllByUser(@Param(value = "user") User user);
}
