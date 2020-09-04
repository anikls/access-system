package com.system.access.doors.component.validator;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Проверка комнаты на наличие в БД.
 *
 * @author anikls
 */
@AllArgsConstructor
@Slf4j
public class ValidationChainExistsRoom extends AbstractValidationChain {

    private static final String ROOM_NOT_FOUND = "room not found";
    private long roomId;

    @Override
    public String check(Room room, User user) {
        if (Objects.isNull(room)) {
            log.warn("Room #{} not found", roomId);
            return ROOM_NOT_FOUND;
        }
        return checkNext(room, user);
    }
}
