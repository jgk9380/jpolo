package entity;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import annotation.Label;

@Entity
@NamedQueries({ @NamedQuery(name = "JRole.findAll", query = "select o from JRole o") })
@Table(name = "J_ROLE", catalog = "jemtest" )
public class JRole implements Serializable {
    private static final long serialVersionUID = 8046088742332354916L;
    @Id
    @Column(nullable = false, length = 20)
    @Label(value="角色名称")
    private String name;
    @Column(length = 80)
    @Label(value="角色说明")
    private String remark;
    @ManyToMany(mappedBy="roles") 
    @Label(value="角色权限")
    Set<JAuthority> auths;

    public void setAuths(Set<JAuthority> auths) {
        this.auths = auths;
    }

    public Set<JAuthority> getAuths() {
        return auths;
    }

    public JRole() {
    }

    public JRole(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
