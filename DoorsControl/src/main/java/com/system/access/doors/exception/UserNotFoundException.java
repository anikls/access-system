package com.system.access.doors.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, возникающее при указании не существующего пользователя системы.
 *
 * @author anikls
 */
@Slf4j
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "user not found")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super();
    }
}
