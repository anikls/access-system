package com.system.access.doors.component.validator;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Проверка отсутствия пользователя в комнате.
 *
 * @author anikls
 */
@AllArgsConstructor
@Slf4j
public class ValidationChainUserOutOfRoom extends AbstractValidationChain {

    private static final String USER_NOT_ENTERED_ROOM = "the user has not entered the room yet";

    @Override
    public String check(Room room, User user) {
        if (Objects.isNull(user.getRoomId())) {
            log.warn("#{} has not entered the room #{} yet", user, room);
            return USER_NOT_ENTERED_ROOM;
        }
        return checkNext(room, user);
    }
}
