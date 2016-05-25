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
@NamedQueries({ @NamedQuery(name = "CodeReportType.findAll", query = "select o from CodeReportType o") })
@Table(name = "CODE_REPORT_TYPE")
public class CodeReportType implements Serializable {
    private static final long serialVersionUID = 4690498785624507713L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(length = 20)
    private String name;

    public CodeReportType() {
    }

    public CodeReportType(BigDecimal id, String name) {
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
        if (!(object instanceof CodeReportType)) {
            return false;
        }
        final CodeReportType other = (CodeReportType) object;
        if (!(id == null ? other.id == null : id.equals(other.id))) {
            return false;
        }
        if (!(name == null ? other.name == null : name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

}
