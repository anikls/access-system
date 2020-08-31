package com.system.access.doors.repository;

import com.system.access.doors.entities.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Хранилище пользователей.
 *
 * @author anikls
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
