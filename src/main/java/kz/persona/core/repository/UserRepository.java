package kz.persona.core.repository;

import kz.persona.core.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIgnoreCaseOrEmailIgnoreCase(String login, String email);

    Optional<User> findByLoginIgnoreCase(String login);
}
