package patterra.tasks;

import patterra.domain.User;

import java.util.List;

public interface TaskService {
    List<Task> findByUser(User user);

    void assignToUser(User user, Task task);

    void delete(Task task);

}
