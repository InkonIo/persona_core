package kz.persona.core.service;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.entity.Subscriber;
import kz.persona.core.model.entity.User;
import kz.persona.core.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public void subscribe(User from, User to) {
        Subscriber sub = subscriberRepository.findByFromUserAndToUser(from, to)
                .orElseGet(() -> {
                    Subscriber subscriber = new Subscriber();
                    subscriber.setFromUser(from);
                    subscriber.setToUser(to);
                    return subscriber;
                });
        sub.setIsActive(Boolean.TRUE);
        subscriberRepository.save(sub);
    }

    public void unsubscribe(User from, User to) {
        Subscriber sub = subscriberRepository.findByFromUserAndToUser(from, to)
                .orElseThrow(() -> new PersonaCoreException("Пользователь должен быть подписан чтобы отписаться"));
        sub.setIsActive(Boolean.FALSE);
        subscriberRepository.save(sub);
    }

    public Boolean isSubscribedToUser(User from, User to) {
        return subscriberRepository.findByFromUserAndToUser(from, to)
                .map(Subscriber::getIsActive)
                .orElse(Boolean.FALSE);
    }

    public void deleteAllByUser(User user) {
        subscriberRepository.deleteAllByUser(user);
    }
}
