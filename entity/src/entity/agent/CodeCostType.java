package entity.agent;

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
@NamedQueries({ @NamedQuery(name = "CodeCostType.findAll", query = "select o from CodeCostType o") })
@Table(name = "CODE_COST_TYPE")
public class CodeCostType implements Serializable {
    private static final long serialVersionUID = -3218589499615873078L;
    @Column(name = "AUTO_SQL", length = 1000)
    private String autoSql;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String type;
  

    public CodeCostType() {
    }



    public String getAutoSql() {
        return autoSql;
    }

    public void setAutoSql(String autoSql) {
        this.autoSql = autoSql;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        return name;
    }


}
