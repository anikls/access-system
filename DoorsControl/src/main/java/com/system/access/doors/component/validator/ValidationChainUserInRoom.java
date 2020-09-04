package com.system.access.doors.component.validator;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Проверка наличия пользователя в комнате.
 *
 * @author anikls
 */
@AllArgsConstructor
@Slf4j
public class ValidationChainUserInRoom extends AbstractValidationChain {

    private static final String USER_NOT_LEFT_ROOM = "the user has not left the room yet";

    @Override
    public String check(Room room, User user) {
        if (Objects.nonNull(user.getRoomId())) {
            log.warn("#{} has not left the #{} yet", user, room);
            return USER_NOT_LEFT_ROOM;
        }
        return checkNext(room, user);
    }
}
