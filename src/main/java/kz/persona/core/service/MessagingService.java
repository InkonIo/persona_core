package kz.persona.core.service;

import kz.persona.core.client.ExpoGoFeignClient;
import kz.persona.core.model.MessageDto;
import kz.persona.core.model.request.NotificationRequestDto;
import kz.persona.core.model.response.ExpoGoSendPushResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagingService {

    private final ExpoGoFeignClient expoGoFeignClient;

    public MessageDto sendNotification(NotificationRequestDto notificationRequestDto) {
        ResponseEntity<ExpoGoSendPushResponseDto> response = expoGoFeignClient.sendPush(notificationRequestDto);
        ExpoGoSendPushResponseDto expoGoSendPushResponseDto = Optional.ofNullable(response)
                .filter(resp -> resp.getStatusCode().is2xxSuccessful())
                .map(HttpEntity::getBody)
                .orElse(null);
        log.info("Push send response: {}", expoGoSendPushResponseDto);
        return new MessageDto("Успешно отправлено");
    }

}
