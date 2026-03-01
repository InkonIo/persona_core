package kz.persona.core.repository;

import kz.persona.core.model.entity.MentorRequest;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.enumaration.MentorRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorRequestRepository extends JpaRepository<MentorRequest, Long> {

    Optional<MentorRequest> findByFromUserAndToUserAndStatusIn(User fromUser, User toUser, List<MentorRequestStatus> status);

    Optional<MentorRequest> findFirstByFromUserAndToUserOrderByCreatedAtDesc(User fromUser, User toUser);

    @Modifying
    @Transactional
    @Query(value = "delete from MentorRequest mr where mr.fromUser = :user or mr.toUser = :user")
    void deleteAllByUser(@Param(value = "user") User user);

}
