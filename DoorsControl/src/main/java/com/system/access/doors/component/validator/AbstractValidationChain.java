package com.system.access.doors.component.validator;

import com.system.access.doors.entities.domain.Room;
import com.system.access.doors.entities.domain.User;
import lombok.Data;
import java.util.Objects;


/**
 * Базовый класс цепочки проверок.
 */
@Data
public abstract class AbstractValidationChain {

    /**
     * Ссылка на следующую проверку.
     */
    private AbstractValidationChain next;

    /**
     * Помогает строить цепь из объектов-проверок.
     */
    public AbstractValidationChain linkWith(AbstractValidationChain next) {
        this.next = next;
        return next;
    }

    /**
     * Подклассы реализуют в этом методе конкретные проверки.
     * @param room
     * @param user
     * @return текст ошибки или null
     */
    public abstract String check(Room room, User user);

    /**
     * Запускает проверку в следующем объекте или завершает проверку, если мы в
     * последнем элементе цепи.
     */
    protected String checkNext(Room room, User user) {
        return Objects.isNull(next) ? null : next.check(room, user);
    }

}
