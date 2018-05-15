package patterra.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import patterra.bp.config.InventionEvents;
import patterra.domain.User;
import patterra.domain.repos.InventionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class InventionTaskServiceImpl implements ReminderService<InventionTask> {

    @Autowired
    private InventionRepository inventionRepository;

    private List<InventionTask> tasks;
    {
        User admin = new User();
        admin.setId(1);
        admin.setUserName("admin");
        User someUser = new User();
        someUser.setId(2);
        someUser.setUserName("sveta z.");

        InventionTask t1 = new InventionTask();
        t1.setUser(admin);
        t1.setEvent(InventionEvents.FORMAL_REGISTER_OFFICE_REQUEST);
        t1.setInvention(inventionRepository.findAll().get(0));
        t1.setUntil(LocalDate.now().plus(1, DAYS));
        tasks.add(t1);

        InventionTask t2 = new InventionTask();
        t2.setUser(someUser);
        t2.setEvent(InventionEvents.PAY_ANNUAL_FEE);
        t2.setInvention(inventionRepository.findAll().get(1));
        t2.setUntil(LocalDate.now().plus(1, DAYS));
        tasks.add(t2);
    }

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
