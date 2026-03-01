package kz.persona.core.client;

import kz.persona.core.model.request.NotificationRequestDto;
import kz.persona.core.model.response.ExpoGoSendPushResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "expo-go-client", url = "https://exp.host/--/api/v2")
public interface ExpoGoFeignClient {

    @PostMapping(value = "/push/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ExpoGoSendPushResponseDto> sendPush(@RequestBody NotificationRequestDto requestDto);

}
