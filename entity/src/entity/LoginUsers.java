package entity;

import annotation.Label;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "LoginUsers.findAll", query = "select o from LoginUsers o") })
@Table(name = "LOGIN_USERS", schema = "comtest")
public class LoginUsers implements Serializable {
    private static final long serialVersionUID = 1100563608832825057L;
    @Id
    @Column(nullable = false, length = 20)
    @Label("登陆工号")
    private String name;
    @Column(length = 20)
    @Label("密码")
    private String password;
    
    
    private String isvalid;

    @Column(name = "MANAGER_TARGET_ID")
    private BigDecimal managerTargetId;

    @ManyToOne
    @JoinColumn(name = "EMP_ID")
    @Label("工号所有人")
    private Employees employee;

    @ManyToMany
    @JoinTable(catalog = "JEMTEST", name = "J_USER_ROlE", inverseJoinColumns = { @JoinColumn(name = "RNAME") },
               joinColumns = { @JoinColumn(name = "UNAME") })
    @Label("角色")
    private Set<JRole> roles;

    @ManyToMany
    @JoinTable(catalog = "JEMTEST", name = "J_USER_AUTH", inverseJoinColumns = { @JoinColumn(name = "ANAME") },
               joinColumns = { @JoinColumn(name = "UNAME") })
    @Label("权限")
    private Set<JAuthority> auths;

    public void setAuths(Set<JAuthority> auths) {        
        this.auths = auths;
    }

    public Set<JAuthority> getAuths() {
        return auths;
    }

    public void setRoles(Set<JRole> roles) {
        this.roles = roles;
    }

    public Set<JRole> getRoles() {
        return roles;
    }

    public LoginUsers() {
    }

    public LoginUsers(Employees employees, String isvalid, BigDecimal managerTargetId, String name, String password) {
        this.employee = employees;
        this.isvalid = isvalid;
        this.managerTargetId = managerTargetId;
        this.name = name;
        this.password = password;
    }


    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public BigDecimal getManagerTargetId() {
        return managerTargetId;
    }

    public void setManagerTargetId(BigDecimal managerTargetId) {
        this.managerTargetId = managerTargetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    public String toString() {
        return " name=" + this.getName();
    }
}
