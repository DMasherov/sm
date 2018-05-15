package patterra.bp.tasks;

import org.springframework.stereotype.Repository;
import patterra.domain.User;

import java.util.List;

@Repository
public interface TaskRepository<T extends Task> {
    List<T> findByUser(User user);

    void assignToUser(User user, T task);

    void delete(T task);

}
