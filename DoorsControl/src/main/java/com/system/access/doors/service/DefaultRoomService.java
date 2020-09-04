package com.system.access.doors.service;

import com.system.access.doors.component.validator.ValidationChainExistsRoom;
import com.system.access.doors.component.validator.ValidationChainExistsUser;
import com.system.access.doors.component.validator.ValidationChainMainCheck;
import com.system.access.doors.component.validator.AbstractValidationChain;
import com.system.access.doors.component.validator.ValidationChainUserInAnotherRoom;
import com.system.access.doors.component.validator.ValidationChainUserInRoom;
import com.system.access.doors.component.validator.ValidationChainUserOutOfRoom;
import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import com.system.access.doors.entities.dto.AccessInfoDto;
import com.system.access.doors.exception.CheckException;
import com.system.access.doors.repository.RoomRepository;
import com.system.access.doors.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        // Цепочка проверок. Впустить в комнату можем, если
        // 1. пользователь уже находится в комнате
        // 2. проходит основное условие "Пользователю можно входить только в те помещения, на номер которого делится его ид"
        final AbstractValidationChain validationChain = new ValidationChainExistsRoom(roomId);
        validationChain
                .linkWith(new ValidationChainExistsUser(keyId))
                .linkWith(new ValidationChainUserInRoom());


        final Room room = roomRepository.findById(roomId).orElse(null);
        final User user = userRepository.findById(keyId).orElse(null);

        final String error = validationChain.check(room, user);

        if (StringUtils.isEmpty(error)) {

            AbstractValidationChain mainCheck = new ValidationChainMainCheck();

            if (mainCheck.check(room, user) == null) { // Если проверки прошли, пускаем пользователя в комнату
                log.info("access to enter is allowed");
                user.enterToRoom(roomId);
                userRepository.save(user);

                return AccessInfoDto.builder()
                        .room(room)
                        .user(user)
                        .msg("access to enter is allowed")
                        .accessed(true)
                        .build();
            } else {
                log.info("access is denied");
                return AccessInfoDto.builder()
                        .room(room)
                        .user(user)
                        .msg("Access is denied")
                        .accessed(false)
                        .build();
            }

        } else { // Есть не пройденные проверки
            throw new CheckException(error);
        }
    }

    @Override
    public AccessInfoDto checkExitRoom(Long roomId, Long keyId) {
        log.debug("check exit from room #{} by keyId #{}", roomId, keyId);

        final Room room = roomRepository.findById(roomId).orElse(null);
        final User user = userRepository.findById(keyId).orElse(null);

        // Выпустить из комнаты можем только тех пользователей, которые
        // 1. уже вошли в комнату
        // 2. находятся в той же комнате, что указана в запросе
        AbstractValidationChain validationChain = new ValidationChainExistsRoom(roomId);
        validationChain
                .linkWith(new ValidationChainExistsUser(keyId))
                .linkWith(new ValidationChainUserOutOfRoom())
                .linkWith(new ValidationChainUserInAnotherRoom(roomId));

        final String error = validationChain.check(room, user);

        if (StringUtils.isEmpty(error)) {

            // Если все проверки прошли успешно, выпускаем пользователя из комнаты
            user.exitFromRoom();
            userRepository.save(user);

            return AccessInfoDto.builder()
                    .room(room)
                    .user(user)
                    .msg("access to exit is allowed")
                    .accessed(true)
                    .build();

        } else { // Есть не пройденные проверки
            throw new CheckException(error);
        }
    }
}
