package patterra.tasks;

import patterra.domain.User;

import java.util.Date;

public class Task<T> {
    private Date until;
    private User user;
    private T todo;
}
