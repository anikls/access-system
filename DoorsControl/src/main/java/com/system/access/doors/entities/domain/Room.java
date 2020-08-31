package com.system.access.doors.entities.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Комната.
 *
 * @author anikls
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rooms")
@Data
public class Room {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Наименование.
     */
    String name;
}
