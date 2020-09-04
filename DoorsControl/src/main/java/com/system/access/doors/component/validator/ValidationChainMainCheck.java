package com.system.access.doors.component.validator;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;

/**
 * Основное условие проверки права доступа.
 *
 * @author anikls
 */
public class ValidationChainMainCheck extends AbstractValidationChain {

    private static final String ACCESS_DENIED = "access is denied";

    @Override
    public String check(Room room, User user) {
        if (user.getId() % room.getId() == 0) {
            return checkNext(room, user);
        }
        return ACCESS_DENIED;
    }
}
