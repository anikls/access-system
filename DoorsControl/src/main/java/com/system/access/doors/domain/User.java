package com.system.access.doors.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Пользователь системы безопасности.
 *
 * @author anikls
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Data
public class User {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Комната, в которой находится пользователь.
     */
    @Column(name = "room_id")
    Long roomId;

    /**
     * Выйти из комнаты.
     */
    public void exitFromRoom() {
        this.roomId = null;
    }

    /**
     * Войти в комнату.
     * @param roomId идентификатор комнаты
     */
    public void enterToRoom(Long roomId) {
        this.roomId = roomId;
    }
}
