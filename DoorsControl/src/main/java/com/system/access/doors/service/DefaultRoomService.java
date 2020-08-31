package com.system.access.doors.service;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import com.system.access.doors.entities.dto.AccessInfoDto;
import com.system.access.doors.exception.CheckException;
import com.system.access.doors.exception.RoomNotFoundException;
import com.system.access.doors.exception.UserNotFoundException;
import com.system.access.doors.repository.RoomRepository;
import com.system.access.doors.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Реализация по-умолчанию сервиса пропускной системы.
 *
 * @author anikls
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRoomService implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public AccessInfoDto checkEnterRoom(Long roomId, Long keyId) {

        log.debug("check enter in room #{} by reyId#{}", roomId, keyId);

        // Проверка введеных значений на наличие в БД
        final Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.warn("Room #{} not found", roomId);
                    return new RoomNotFoundException();
                });
        final User user = userRepository.findById(keyId)
                .orElseThrow(() -> {
                    log.warn("User #{} not found", keyId);
                    return new UserNotFoundException();
                });

        // Впустить в комнату можем, если
        // 1. пользователь уже находится в комнате
        // 2. проходит основное условие "Пользователю можно входить только в те помещения, на номер которого делится его ид"
        if (Objects.nonNull(user.getRoomId())) {
            throw new CheckException("the user has not left the room yet");
        }

        if (keyId % roomId == 0) {
           // Если проверки прошли, пускаем пользователя в комнату
           user.enterToRoom(roomId);
           userRepository.save(user);

           return AccessInfoDto.builder()
                   .room(room)
                   .user(user)
                   .msg("access to enter is allowed")
                   .accessed(true)
                   .build();
        } else {
            return AccessInfoDto.builder()
                    .room(room)
                    .user(user)
                    .msg("Access is denied")
                    .accessed(false)
                    .build();
        }
    }

    @Override
    public AccessInfoDto checkExitRoom(Long roomId, Long keyId) {
        log.debug("check exit from room #{} by reyId#{}", roomId, keyId);

        // Проверка введеных значений на наличие в БД
        final Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.warn("Room #{} not found", roomId);
                    return new RoomNotFoundException();
                });
        final User user = userRepository.findById(keyId)
                .orElseThrow(() -> {
                    log.warn("User #{} not found", keyId);
                    return new UserNotFoundException();
                });

        // Выпустить из комнаты можем только тех пользователей, которые
        // 1. уже вошли в комнату
        // 2. находятся в той же комнате, что указана в запросе
        if (Objects.isNull(user.getRoomId())) {
            throw new CheckException("the user has not entered the room yet");
        }
        if (!user.getRoomId().equals(roomId)) {
            throw new CheckException("the user is in another room");
        }
        // Если все проверки прошли успешно, выпускаем пользователя из комнаты
        user.exitFromRoom();
        userRepository.save(user);

        return AccessInfoDto.builder()
                .room(room)
                .user(user)
                .msg("access to exit is allowed")
                .accessed(true)
                .build();
    }
}
