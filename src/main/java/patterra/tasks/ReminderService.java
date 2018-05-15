package patterra.tasks;

import patterra.domain.User;

import java.util.List;

public interface ReminderService<T extends Reminder> {
    List<T> findByUser(User user);

    void assignToUser(User user, T task);

    void delete(T task);

}
