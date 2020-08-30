package com.system.access.doors.controller;

import com.system.access.doors.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер пропускной системы.
 * Набор интерфейсов для проверки прав доступа к двери помещения.
 *
 * @author anikls
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

    /**
     * Сервис для проверки прав доступа.
     */
    private final RoomService roomService;

    /**
     * Проверка права доступа в комнату.
     *
     * @param roomId идентификатор комнаты
     * @param keyId идентификатор пользователя
     * @param entrance попытка входа/выхода (true - зафиксирован вход в комнату, false - выход из комнаты)
     * @return Статус 200 - доступ к двери разрешен
     */
    @GetMapping("/check")
    public ResponseEntity<String> checkDoor(@RequestParam("roomId") Long roomId,
                                    @RequestParam("keyId") Long keyId,
                                    @RequestParam("entrance") boolean entrance) {

        log.info("Room #{} verification request by user #{} for {}", roomId, keyId, entrance?"enter":"exit");

        if (entrance) {
            if (roomService.checkEnterRoom(roomId, keyId)) {
                log.info("Enter to room #{} by user #{} assessed", roomId, keyId);
                return ResponseEntity.ok("access to enter is allowed");
            }
        } else {
            if (roomService.checkExitRoom(roomId, keyId)) {
                log.info("Exit from room #{} by user #{} assessed", roomId, keyId);
                return ResponseEntity.ok("access to exit is allowed");
            }
        }

        log.warn("Room #{} by user #{} for {} forbidden", roomId, keyId, entrance?"enter":"exit");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }
}