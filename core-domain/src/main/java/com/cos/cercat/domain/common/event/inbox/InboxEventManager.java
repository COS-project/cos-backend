package com.cos.cercat.domain.common.event.inbox;

import com.cos.cercat.domain.common.event.EventStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InboxEventManager {

    private final InboxEventRepository inboxEventRepository;

    public void append(InboxEvent inboxEvent) {
        inboxEventRepository.save(inboxEvent);
    }

    public boolean exists(String id) {
        return inboxEventRepository.exists(id);
    }

    public List<InboxEvent> readByStatus(EventStatus status) {
        return inboxEventRepository.findByStatus(status);
    }

}
