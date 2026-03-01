package kz.persona.core.service;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.mapper.NotificationMapper;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.entity.Notification;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.request.NotificationRequestDto;
import kz.persona.core.model.request.NotificationSeenRequestDto;
import kz.persona.core.model.response.NotificationResponseDto;
import kz.persona.core.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    private final NotificationMapper notificationMapper;

    @Transactional
    public void createNotification(NotificationRequestDto requestDto, User user) {
        Notification notification = Notification.builder()
                .title(requestDto.title())
                .description(requestDto.body())
                .data(requestDto.data())
                .user(user)
                .build();
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getUserNotifications(Long userId) {
        User user = userService.getById(userId);
        List<Notification> notifications = notificationRepository.findAllByUserAndDeletedIsFalseOrderByCreatedAtDesc(user);
        return notificationMapper.map(notifications);
    }

    @Transactional
    public MessageDto readNotificationsByUserId(NotificationSeenRequestDto requestDto) {
        List<Long> notificationIds = requestDto.notificationIds();
        if (CollectionUtils.isNotEmpty(notificationIds)) {
            List<Notification> notifications = notificationIds.stream()
                    .map(this::getNotificationById)
                    .toList();
            notifications.forEach(e -> e.setIsActive(Boolean.FALSE));
            notificationRepository.saveAll(notifications);
        }
        return new MessageDto("Успешно");
    }

    public Notification getNotificationById(Long id) {
        return this.findNotificationById(id)
                .orElseThrow(() ->
                        new PersonaCoreException("Уведомление по идентификатору \"%s\" не найден".formatted(id))
                );
    }

    public Optional<Notification> findNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public void deleteAllByUser(User user) {
        notificationRepository.deleteAllByUser(user);
    }
}
