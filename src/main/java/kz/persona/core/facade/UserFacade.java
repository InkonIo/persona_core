package kz.persona.core.facade;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.mapper.UserMapper;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.entity.UserDeviceToken;
import kz.persona.core.model.request.*;
import kz.persona.core.model.response.MentorRequestStatusResponseDto;
import kz.persona.core.model.response.SubscriptionStatusResponseDto;
import kz.persona.core.model.response.UserInfoResponseDto;
import kz.persona.core.model.response.UserLoginResponseDto;
import kz.persona.core.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final RatingService ratingService;
    private final KeycloakService keycloakService;
    private final SubscriberService subscriberService;
    private final NotificationService notificationService;
    private final MentorRequestService mentorRequestService;
    private final ProfileDocumentService profileDocumentService;
    private final UserDeviceTokenService userDeviceTokenService;

    private final UserMapper userMapper;

    public MessageDto createUser(UserUpsertRequestDto requestDto) {
        userService.findByLoginOrEmail(requestDto.login(), requestDto.email())
                .ifPresent(e -> {
                    throw new PersonaCoreException("Существует логин или почта");
                });
        String keycloakId = keycloakService.createKeycloakUser(requestDto);
        Long userId = userService.createUser(requestDto, keycloakId);
        profileDocumentService.insertUser(userId);
        return new MessageDto("Пользователь успешно создан");
    }

    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        if (Boolean.FALSE.equals(userService.checkExistence(requestDto.login()))) {
            throw new PersonaCoreException("Пользователь с таким логином не найден");
        }
        return keycloakService.login(requestDto.login(), requestDto.password());
    }

    public UserLoginResponseDto refresh(UserLoginRefreshRequestDto requestDto) {
        return keycloakService.refresh(requestDto);
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto getCurrentUser() {
        return userMapper.map(userService.getCurrentUser());
    }

    @Transactional
    public MessageDto updateUser(UserUpsertRequestDto requestDto) {
        User user = userService.getCurrentUser();
        userMapper.update(requestDto, user);
        userService.save(user);
        return new MessageDto("Успешно обновлены данные пользователя");
    }

    @Transactional
    public MessageDto changeVisibility() {
        User user = userService.getCurrentUser();
        user.setVisible(BooleanUtils.negate(user.getVisible()));
        userService.save(user);
        return new MessageDto("Успешно изменен статус видимости");
    }

    @Transactional
    public MessageDto subscribeToUser(SubscribeRequestDto requestDto) {
        User toUser = userService.getById(requestDto.toUser());
        User fromUser = userService.getCurrentUser();
        if (toUser.getId().equals(fromUser.getId())) {
            throw new PersonaCoreException("Нельзя подписаться на самого себя");
        }
        subscriberService.subscribe(fromUser, toUser);
        return new MessageDto("Пользователь успешно подписан на пользователя");
    }

    @Transactional
    public MessageDto unsubscribeToUser(SubscribeRequestDto requestDto) {
        User toUser = userService.getById(requestDto.toUser());
        User fromUser = userService.getCurrentUser();
        if (toUser.getId().equals(fromUser.getId())) {
            throw new PersonaCoreException("Нельзя отписаться от самого себя");
        }
        subscriberService.unsubscribe(fromUser, toUser);
        return new MessageDto("Пользователь успешно отписан от пользователя");
    }

    @Transactional
    public MessageDto requestMentor(MentorRequestDto requestDto) {
        User toUser = userService.getById(requestDto.userId());
        User fromUser = userService.getCurrentUser();
        if (toUser.getId().equals(fromUser.getId())) {
            throw new PersonaCoreException("Нельзя отправить запрос на самого себя");
        }
        mentorRequestService.request(toUser, fromUser);
        return new MessageDto("Успешно отправлен запрос на наставничество");
    }

    @Transactional
    public MessageDto cancelRequestMentor(MentorRequestDto requestDto) {
        User toUser = userService.getById(requestDto.userId());
        User fromUser = userService.getCurrentUser();
        if (toUser.getId().equals(fromUser.getId())) {
            throw new PersonaCoreException("Нельзя отменить запрос от самого себя");
        }
        mentorRequestService.cancelRequest(toUser, fromUser);
        return new MessageDto("Успешно отменен запрос на наставничество");
    }

    @Transactional(readOnly = true)
    public SubscriptionStatusResponseDto checkSubscription(SubscriptionStatusRequestDto requestDto) {
        User fromUser = userService.getCurrentUser();
        User toUser = userService.getById(requestDto.userId());
        return new SubscriptionStatusResponseDto(subscriberService.isSubscribedToUser(fromUser, toUser));
    }

    @Transactional(readOnly = true)
    public MentorRequestStatusResponseDto checkRequestMentor(MentorRequestDto requestDto) {
        User fromUser = userService.getCurrentUser();
        User toUser = userService.getById(requestDto.userId());
        return new MentorRequestStatusResponseDto(mentorRequestService.getRequestStatus(toUser, fromUser));
    }

    @Transactional
    public MessageDto registerToken(RegisterTokenRequestDto requestDto) {
        User currentUser = userService.getCurrentUser();
        userDeviceTokenService.create(currentUser, requestDto.token());
        return new MessageDto("Успешно подключен");
    }

    public MessageDto delete(Long id) {
        User user = userService.getById(id);
        ratingService.deleteAllByUser(user);
        subscriberService.deleteAllByUser(user);
        notificationService.deleteAllByUser(user);
        mentorRequestService.deleteAllByUser(user);
        userDeviceTokenService.deleteAllByUser(user);
        keycloakService.deleteUser(user.getKeycloakId());
        userService.delete(user);
        return new MessageDto("Успешно удален");
    }

    public List<String> getUserPushToken(Long id) {
        User user = userService.getById(id);
        return userDeviceTokenService.getAllByUser(user)
                .stream()
                .map(UserDeviceToken::getToken)
                .distinct()
                .toList();
    }
}
