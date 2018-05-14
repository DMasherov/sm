package patterra.tasks;

import org.springframework.stereotype.Component;
import patterra.domain.User;

import java.util.List;

@Component
public class TaskServiceImpl implements TaskService {
    @Override
    public List<Task> findByUser(User user) {
        return null;
    }

    @Override
    public void assignToUser(User user, Task task) {

    }

    @Override
    public void delete(Task task) {

    }
}
