package com.system.access.doors.service;

/**
 * Сервис пропускной системы.
 *
 * @author anikls
 */
public interface RoomService {

    /**
     * Метод проверки прав доступа для входа в комнату.
     *
     * @param roomId идентификатор комнаты
     * @param keyId идентификатор пользователя
     * @return true - доступ разрешен, false - доступ запрещен
     */
    boolean checkEnterRoom(Long roomId, Long keyId);

    /**
     * Метод проверки прав доступа для выходы из комнаты.
     *
     * @param roomId идентификатор комнаты
     * @param keyId идентификатор пользователя
     * @return true - доступ разрешен, false - доступ запрещен
     */
    boolean checkExitRoom(Long roomId, Long keyId);
}
