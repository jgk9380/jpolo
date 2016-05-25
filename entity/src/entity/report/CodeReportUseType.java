package entity.report;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "CodeReportUseType.findAll", query = "select o from CodeReportUseType o") })
@Table(name = "CODE_REPORT_USE_TYPE")
public class CodeReportUseType implements Serializable {
    private static final long serialVersionUID = -1652431503727840838L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(length = 80)
    private String name;

    public CodeReportUseType() {
    }

    public CodeReportUseType(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
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
    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CodeReportUseType)) {
            return false;
        }
        final CodeReportUseType other = (CodeReportUseType) object;
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
