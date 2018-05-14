package patterra.domain;

public class Role {
    private Integer id;
    private String roleName;
    private String roleAlias;

    public Role() {
    }

    public Role(String roleAlias, Integer id, String roleName) {
        this.roleAlias = roleAlias;
        this.id = id;
        this.roleName = roleName;
    }

    public Integer getPK() {
        return id;
    }

    public void setPK(Integer key) {
        id = key;
    }

    public String getRoleAlias() {
        return roleAlias;
    }

    public void setRoleAlias(String roleAlias) {
        this.roleAlias = roleAlias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer roleId) {
        this.id = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return !(id != null ? !id.equals(role.id) : role.id != null);

    }

    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
