package com.cos.cercat.domain.common.event.inbox;

import com.cos.cercat.domain.common.event.EventStatus;
import java.util.List;

public interface InboxEventRepository {

    void save(InboxEvent inboxEvent);

    boolean exists(String id);

    List<InboxEvent> findByStatus(EventStatus status);
}
