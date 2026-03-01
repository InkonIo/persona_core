package kz.persona.core.facade;

import kz.persona.core.model.MessageDto;
import kz.persona.core.model.entity.UserDeviceToken;
import kz.persona.core.model.request.NotificationRequestDto;
import kz.persona.core.model.request.NotificationSeenRequestDto;
import kz.persona.core.model.response.NotificationResponseDto;
import kz.persona.core.service.MessagingService;
import kz.persona.core.service.NotificationService;
import kz.persona.core.service.UserDeviceTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationFacade {

    private final MessagingService messagingService;
    private final NotificationService notificationService;
    private final UserDeviceTokenService userDeviceTokenService;

    @Transactional
    public MessageDto sendNotification(NotificationRequestDto requestDto) {
        requestDto.to().stream()
                .map(userDeviceTokenService::getAllByToken)
                .flatMap(Collection::stream)
                .map(UserDeviceToken::getUser)
                .forEach(user -> notificationService.createNotification(requestDto, user));
        return messagingService.sendNotification(requestDto);
    }

    public List<NotificationResponseDto> getUserNotifications(Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    public MessageDto readNotifications(NotificationSeenRequestDto requestDto) {
        return notificationService.readNotificationsByUserId(requestDto);
    }
}
