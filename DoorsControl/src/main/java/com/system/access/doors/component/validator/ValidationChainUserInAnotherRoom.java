package com.system.access.doors.component.validator;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Проверка наличия пользователя в другой комнате.
 *
 * @author anikls
 */
@AllArgsConstructor
@Slf4j
public class ValidationChainUserInAnotherRoom extends AbstractValidationChain {

    private static final String USER_IN_ANOTHER_ROOM = "the user is in another room";
    private long roomId;

    @Override
    public String check(Room room, User user) {

        if (!user.getRoomId().equals(roomId)) {
            log.warn("#{} is in another room #{}", user, user.getRoomId());
            return USER_IN_ANOTHER_ROOM;
        }
        return checkNext(room, user);
    }
}
