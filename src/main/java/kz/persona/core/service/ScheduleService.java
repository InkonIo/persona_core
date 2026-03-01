package kz.persona.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ProfileDocumentService profileDocumentService;

    @Scheduled(fixedRateString = "${reindex.rate}")
    public void reindex() {
        profileDocumentService.reindex();
    }

}
