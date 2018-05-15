package patterra.bp.invention.config.tasks;

import org.springframework.stereotype.Repository;
import patterra.bp.tasks.TaskRepository;
import patterra.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InventionTaskRepositoryStub implements TaskRepository<InventionTask> {
    private List<InventionTask> tasks;

    @Override
    public List<InventionTask> findByUser(User user) {
        return tasks.stream()
                .filter(t -> user.equals(t.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public void assignToUser(User user, InventionTask task) {
        task.setUser(user);
    }

    @Override
    public void delete(InventionTask task) {
        tasks.remove(task);
    }
}
