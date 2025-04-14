package com.cos.cercat.domain.alarm;

import com.cos.cercat.domain.user.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Alarm {

    private final Long id;
    private final User receiver;
    private final Long originId;
    private final AlarmType alarmType;
    private final LocalDateTime alarmTime;
    private final boolean isRead = false;
}
