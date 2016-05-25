package entity;

import annotation.Label;
import annotation.SelectScope;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "Depart.findAll", query = "select o from Depart o") })
@Table(name = "CODE_DEPART", catalog = "comtest")
@Label("部门")
public class Depart implements Serializable {
    private static final long serialVersionUID = -8853925053643468609L;
    @Id
    @Column(nullable = false)
    @Label("部门ID")
    private BigDecimal id;
    @Column(unique = true, length = 80)
    @Label("名称")
    private String name;

    @OneToMany(mappedBy = "depart", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @Label("部门员工")
    private List<Employees> empList;


    @Column(name = "DEPART_TYPE")
    private BigDecimal departType;


    @Column(name = "DUTIER_ID", length = 20)
    @SelectScope(sql = "select  name,id from comtest.employees order by name")
    @Label("部门负责人")
    private String dutierId;

    public void setEmpList(List<Employees> empList) {
        this.empList = empList;
    }

    public List<Employees> getEmpList() {
        return empList;
    }

    public Depart() {
    }

    public Depart(BigDecimal departLevel, BigDecimal departType, String dutierId, Depart codeDepart, BigDecimal id,
                  String name) {
        this.departType = departType;
        this.dutierId = dutierId;
        this.id = id;
        this.name = name;
    }


    public BigDecimal getDepartType() {
        return departType;
    }

    public void setDepartType(BigDecimal departType) {
        this.departType = departType;
    }

    public String getDutierId() {
        return dutierId;
    }

    public void setDutierId(String dutierId) {
        this.dutierId = dutierId;
    }


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Depart)) {
            return false;
        }
        final Depart other = (Depart) object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

}
