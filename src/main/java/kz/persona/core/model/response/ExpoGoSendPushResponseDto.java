package kz.persona.core.model.response;

import java.util.List;

public record ExpoGoSendPushResponseDto(
        List<ExpoGoPushStatusResponseDto> data) {
    record ExpoGoPushStatusResponseDto(
            String status,
            String id) {
    }
}
