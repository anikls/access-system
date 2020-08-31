package com.system.access.doors.entities.dto;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информация о полученном доступе.
 *
 * @author anikls
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessInfoDto {
    /**
     * Пользователь системы безопасности.
     */
    private User user;
    /**
     * Комната, к оторой осуществляется доступ.
     */
    private Room room;
    /**
     * Информационное сообщение.
     */
    private String msg;
    /**
     * Вердикт системы безопасности (true - доступ разрешен, false - доступ запрещен).
     */
    private boolean accessed;

}
