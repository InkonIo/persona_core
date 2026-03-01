package kz.persona.core.service;

import kz.persona.core.model.entity.User;
import kz.persona.core.model.entity.UserDeviceToken;
import kz.persona.core.repository.UserDeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDeviceTokenService {

    private final UserDeviceTokenRepository userDeviceTokenRepository;

    public void create(User user, String token) {
        if (!userDeviceTokenRepository.existsByUserAndToken(user, token)) {
            UserDeviceToken userDeviceToken = UserDeviceToken.builder()
                    .user(user)
                    .token(token)
                    .build();
            userDeviceTokenRepository.save(userDeviceToken);
        }
    }

    public List<UserDeviceToken> getAllByToken(String token) {
        return userDeviceTokenRepository.findAllByToken(token);
    }

    public List<UserDeviceToken> getAllByUser(User user) {
        return userDeviceTokenRepository.findAllByUser(user);
    }

    public void deleteAllByUser(User user) {
        userDeviceTokenRepository.deleteAllByUser(user);
    }
}
