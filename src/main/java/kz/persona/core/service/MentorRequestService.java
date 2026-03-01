package kz.persona.core.service;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.entity.MentorRequest;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.enumaration.MentorRequestStatus;
import kz.persona.core.repository.MentorRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorRequestService {

    private final MentorRequestRepository mentorRequestRepository;

    public void request(User toUser, User fromUser) {
        boolean hasRequest = mentorRequestRepository.findByFromUserAndToUserAndStatusIn(fromUser, toUser, List.of(MentorRequestStatus.ACCEPTED, MentorRequestStatus.WAITING))
                .isPresent();
        if (hasRequest) {
            throw new PersonaCoreException("Уже есть активная заявка");
        }
        MentorRequest mentorRequest = new MentorRequest();
        mentorRequest.setStatus(MentorRequestStatus.WAITING);
        mentorRequest.setFromUser(fromUser);
        mentorRequest.setToUser(toUser);
        mentorRequestRepository.save(mentorRequest);
    }

    public void cancelRequest(User toUser, User fromUser) {
        MentorRequest mentorRequest = mentorRequestRepository.findFirstByFromUserAndToUserOrderByCreatedAtDesc(fromUser, toUser)
                .orElseThrow(() -> new PersonaCoreException("Чтобы отменить запрос нужно иметь активный запрос"));
        mentorRequest.setStatus(MentorRequestStatus.CANCELED);
        mentorRequestRepository.save(mentorRequest);
    }

    public MentorRequestStatus getRequestStatus(User toUser, User fromUser) {
        return mentorRequestRepository.findFirstByFromUserAndToUserOrderByCreatedAtDesc(fromUser, toUser)
                .map(MentorRequest::getStatus)
                .orElse(MentorRequestStatus.NOT_REQUESTED);
    }

    public void deleteAllByUser(User user) {
        mentorRequestRepository.deleteAllByUser(user);
    }
}
