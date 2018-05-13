package patterra.bp.tasks;

import java.util.List;

public interface TaskService {
    List<Task> findByUsername(String userName);

    void assignToUser(String userName, Task task);

    void delete(Task task);

}
