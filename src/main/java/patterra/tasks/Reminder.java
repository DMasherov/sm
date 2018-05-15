package patterra.tasks;

import patterra.domain.User;

import java.time.LocalDate;

public abstract class Reminder {
    private User user;
    private LocalDate until;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getUntil() {
        return until;
    }

    public void setUntil(LocalDate until) {
        this.until = until;
    }
}
