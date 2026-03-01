package kz.persona.core.service;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.mapper.UserMapper;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.request.UserUpsertRequestDto;
import kz.persona.core.model.response.UserJwtInfoResponseDto;
import kz.persona.core.repository.UserRepository;
import kz.persona.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public Optional<User> findByLoginOrEmail(String login, String email) {
        return userRepository.findByLoginIgnoreCaseOrEmailIgnoreCase(login, email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLoginIgnoreCase(login);
    }

    @Transactional
    public Long createUser(UserUpsertRequestDto requestDto, String keycloakId) {
        User user = userMapper.map(requestDto);
        user.setKeycloakId(keycloakId);
        return save(user).getId();
    }

    public boolean checkExistence(String login) {
        return this.findByLogin(login)
                .isPresent();
    }

    public User getCurrentUser() {
        return Optional.ofNullable(JwtUtils.getUserInfo())
                .map(UserJwtInfoResponseDto::username)
                .flatMap(this::findByLogin)
                .orElseThrow(() -> new PersonaCoreException("Не найден пользователь"));
    }

    public User getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new PersonaCoreException("Не найден пользователь по идентификатору %s".formatted(id)));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
