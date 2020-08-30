package com.system.access.doors.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, возникающее в логике проверки прав доступа.
 *
 * @author anikls
 */
@Slf4j
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CheckException extends RuntimeException {
    public CheckException(String errorMsg) {
        super(errorMsg);
        log.warn(errorMsg);
    }
}
