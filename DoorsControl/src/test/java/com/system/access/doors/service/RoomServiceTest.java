package com.system.access.doors.service;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import com.system.access.doors.exception.CheckException;
import com.system.access.doors.exception.RoomNotFoundException;
import com.system.access.doors.exception.UserNotFoundException;
import com.system.access.doors.repository.RoomRepository;
import com.system.access.doors.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit-тесты.
 *
 * @author anikls
 */
public class RoomServiceTest {

    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        roomRepository = mock(RoomRepository.class);
        userRepository = mock(UserRepository.class);
        roomService = new DefaultRoomService(roomRepository, userRepository);
    }

    @Test
    public void checkEnterWithNotExistingRoomMustThrowException() {
        final long idRoom = 12L;
        final long idUser = 1L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.empty());
        when(userRepository.findById(idUser)).thenReturn(Optional.of(new User(idUser, null)));
        final Throwable throwable
                = catchThrowable(() -> roomService.checkEnterRoom(idRoom, idUser));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(CheckException.class);
    }

    @Test
    public void checkEnterWithNotExistingUserMustThrowException() {
        final long idRoom = 12L;
        final long idUser = 1L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.of(new Room(idRoom, "room")));
        when(userRepository.findById(idUser)).thenReturn(Optional.empty());
        final Throwable throwable
                = catchThrowable(() -> roomService.checkEnterRoom(idRoom, idUser));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(CheckException.class);
    }

    @Test
    public void checkEnterUserInRoomMustThrowException() {
        final long idRoom = 12L;
        final long idUser = 1L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.of(new Room(idRoom, "room")));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(new User(idUser, idUser)));
        final Throwable throwable
                = catchThrowable(() -> roomService.checkEnterRoom(idRoom, idUser));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(CheckException.class);
    }

    @Test
    public void checkEnterUserMustReturnTrue() {
        final long idRoom = 2L;
        final long idUser = 10L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.of(new Room(idRoom, "room")));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(new User(idUser, null)));
        assertThat(roomService.checkEnterRoom(idRoom, idUser).isAccessed()).isTrue();
    }

    @Test
    public void checkEnterUserMustReturnFalse() {
        final long idRoom = 3L;
        final long idUser = 10L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.of(new Room(idRoom, "room")));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(new User(idUser, null)));
        assertThat(roomService.checkEnterRoom(idRoom, idUser).isAccessed()).isFalse();
    }

    @Test
    public void checkExitWithNotExistingRoomMustThrowException() {
        final long idRoom = 12L;
        final long idUser = 1L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.empty());
        when(userRepository.findById(idUser)).thenReturn(Optional.of(new User(idUser, null)));
        final Throwable throwable
                = catchThrowable(() -> roomService.checkExitRoom(idRoom, idUser));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(CheckException.class);
    }

    @Test
    public void checkExitWithNotExistingUserMustThrowException() {
        final long idRoom = 12L;
        final long idUser = 1L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.of(new Room(idRoom, "room")));
        when(userRepository.findById(idUser)).thenReturn(Optional.empty());
        final Throwable throwable
                = catchThrowable(() -> roomService.checkExitRoom(idRoom, idUser));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(CheckException.class);
    }

    @Test
    public void checkExitWithUserOutRoomMustThrowException() {
        final long idRoom = 12L;
        final long idUser = 1L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.of(new Room(idRoom, "room")));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(new User(idUser, null)));
        final Throwable throwable
                = catchThrowable(() -> roomService.checkExitRoom(idRoom, idUser));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(CheckException.class);
    }

    @Test
    public void checkExitWithUserInOtherRoomMustThrowException() {
        final long idRoom = 12L;
        final long idUser = 1L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.of(new Room(idRoom, "room")));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(new User(idUser, idRoom + 1)));
        final Throwable throwable
                = catchThrowable(() -> roomService.checkExitRoom(idRoom, idUser));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(CheckException.class);
    }

    @Test
    public void checkExitMustReturnTrue() {
        final long idRoom = 12L;
        final long idUser = 1L;
        when(roomRepository.findById(idRoom)).thenReturn(Optional.of(new Room(idRoom, "room")));
        when(userRepository.findById(idUser)).thenReturn(Optional.of(new User(idUser, idRoom)));
        assertThat(roomService.checkExitRoom(idRoom, idUser).isAccessed()).isTrue();
    }
}
