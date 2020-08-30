package com.system.access.doors.repository;

import com.system.access.doors.domain.Room;
import org.springframework.data.repository.CrudRepository;

/**
 * Хранилище комнат.
 *
 * @author anikls
 */
public interface RoomRepository extends CrudRepository<Room, Long> {
}
