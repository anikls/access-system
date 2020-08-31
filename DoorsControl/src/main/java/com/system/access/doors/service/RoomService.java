package com.system.access.doors.service;

import com.system.access.doors.entities.dto.AccessInfoDto;

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
     * @return разрешение системы безопасности
     */
    AccessInfoDto checkEnterRoom(Long roomId, Long keyId);

    /**
     * Метод проверки прав доступа для выходы из комнаты.
     *
     * @param roomId идентификатор комнаты
     * @param keyId идентификатор пользователя
     * @return разрешение системы безопасности
     */
    AccessInfoDto checkExitRoom(Long roomId, Long keyId);
}
