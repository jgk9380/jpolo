package entity.agent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "CGroup.findAll", query = "select o from CGroup o") })
@Table(name = "J_CODE_CGROUP")
public class CGroup implements Serializable {
    private static final long serialVersionUID = 1712025982757448679L;
    @Id
    @Column(length = 16)
    private String id;
    @Column(length = 240)
    private String name;

    public CGroup() {
    }

    public CGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
