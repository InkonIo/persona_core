package kz.persona.core.repository;

import kz.persona.core.model.entity.User;
import kz.persona.core.model.entity.UserDeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserDeviceTokenRepository extends JpaRepository<UserDeviceToken, Long> {

    List<UserDeviceToken> findAllByToken(String token);

    boolean existsByUserAndToken(User user, String token);

    @Modifying
    @Transactional
    @Query(value = "delete from UserDeviceToken udt where udt.user = :user")
    void deleteAllByUser(@Param(value = "user") User user);

    List<UserDeviceToken> findAllByUser(User user);
}
