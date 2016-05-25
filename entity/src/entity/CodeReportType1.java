package entity;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "CodeReportType1.findAll", query = "select o from CodeReportType1 o") })
@Table(name = "CODE_REPORT_TYPE")
public class CodeReportType1 implements Serializable {
    private static final long serialVersionUID = 8900076877656528783L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(length = 20)
    private String name;

    public CodeReportType1() {
    }

    public CodeReportType1(BigDecimal id, String name) {
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
}
