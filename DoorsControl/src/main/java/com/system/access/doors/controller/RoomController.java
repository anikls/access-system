package com.system.access.doors.controller;

import com.system.access.doors.entities.dto.AccessInfoDto;
import com.system.access.doors.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

/**
 * Контроллер пропускной системы.
 * Набор интерфейсов для проверки прав доступа к двери помещения.
 *
 * @author anikls
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
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
    public ResponseEntity<AccessInfoDto> checkDoor(@RequestParam("roomId") @Min(1) Long roomId,
                                                   @RequestParam("keyId") @Min(1) Long keyId,
                                                   @RequestParam("entrance") boolean entrance) {

        log.info("Room #{} verification request by user #{} for {}", roomId, keyId, entrance?"enter":"exit");

        final AccessInfoDto accessInfoDto = entrance
                ?roomService.checkEnterRoom(roomId, keyId)
                :roomService.checkExitRoom(roomId, keyId);

        if (accessInfoDto.isAccessed()) {
            log.info("Enter to #{} by #{} assessed",
                    entrance?"enter":"exit", accessInfoDto.getRoom(), accessInfoDto.getUser());
            return ResponseEntity.ok(accessInfoDto);
        }

        log.warn("#{} by #{} for {} forbidden",
                accessInfoDto.getRoom(), accessInfoDto.getUser(), entrance?"enter":"exit");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
