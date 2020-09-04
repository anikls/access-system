package com.system.access.doors.component.validator;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Проверка пользователя на наличие в БД.
 *
 * @author anikls
 */
@AllArgsConstructor
@Slf4j
public class ValidationChainExistsUser extends AbstractValidationChain {

    private static final String USER_NOT_FOUND = "user not found";
    private long userId;

    @Override
    public String check(Room room, User user) {
        if (Objects.isNull(user)) {
            log.warn("User #{} not found", userId);
            return USER_NOT_FOUND;
        }
        return checkNext(room, user);
    }
}
