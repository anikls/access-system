package com.system.access.doors.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, возникающее при указании неверного идентификатора комнаты.
 *
 * @author anikls
 */
@Slf4j
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "room not found")
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long roomId) {
        super("Room #{} not found");
        log.warn("Room #{} not found", roomId);
    }
}
