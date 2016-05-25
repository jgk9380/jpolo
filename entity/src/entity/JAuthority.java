package entity;

import java.io.Serializable;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "JAuthority.findAll", query = "select o from JAuthority o") })
@Table(name = "J_AUTHORITY",catalog = "jemtest")
public class JAuthority implements Serializable {
    @Id
    @Column(nullable = false, length = 20)
    private String name;
    @Column(length = 80)
    private String remark;
    //@ManyToMany(mappedBy = "auths")
    @ManyToMany()
    @JoinTable(catalog = "jemtest",name = "J_ROLE_AUTH", inverseJoinColumns = { @JoinColumn(name = "ANAME")},joinColumns = {@JoinColumn(name = "RNAME")})
    Set<JRole> roles;

    public void setRoles(Set<JRole> roles) {
        this.roles = roles;
    }

    public Set<JRole> getRoles() {
        return roles;
    }


    public JAuthority() {
    }

    public JAuthority(String name, String remark) {
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
