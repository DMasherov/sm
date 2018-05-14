package patterra.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User {
    private Integer id;
    private String userName;
    private String fullName;
    private Set<String> roles = new HashSet<>();
    private Date loggedIn;
    private Date loggedOut;

    public User() {
    }

    public Integer getPK() {
        return id;
    }

    public void setPK(Integer key) {
        this.id = key;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Date loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Date getLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(Date loggedOut) {
        this.loggedOut = loggedOut;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}

