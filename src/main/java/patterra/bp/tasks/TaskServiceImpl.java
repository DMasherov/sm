package patterra.bp.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    @Override
    public List<Task> findByUsername(String userName) {
        return new ArrayList<>();
    }

    @Override
    public void assignToUser(String userName, Task task) {
    }

    @Override
    public void delete(Task task) {

    }
}
