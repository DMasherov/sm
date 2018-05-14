package patterra.domain;

import java.util.Objects;

public class Group {
    private Integer id;
    private String groupName;

    public Integer getPK() {
        return id;
    }

    public void setPK(Integer key) {
        this.id = key;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupName, group.groupName);
    }

    public int hashCode() {
        return Objects.hash(groupName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
