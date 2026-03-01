package kz.persona.core.service;

import kz.persona.core.model.MessageDto;
import kz.persona.core.model.entity.Request;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.enumaration.RequestStatus;
import kz.persona.core.model.request.RequestRequestDto;
import kz.persona.core.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    private final UserService userService;

    public MessageDto create(RequestRequestDto requestDto) {
        User currentUser = userService.getCurrentUser();
        Request request = Request.builder()
                .message(requestDto.message())
                .title(requestDto.title())
                .status(RequestStatus.CREATED)
                .user(currentUser)
                .build();
        requestRepository.save(request);
        return new MessageDto("Успешно создан");
    }

}
