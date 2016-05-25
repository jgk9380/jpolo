package entity.report;

import annotation.Enum;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * To create ID generator sequence id_gen_seq:
 * CREATE SEQUENCE id_gen_seq INCREMENT BY 50 START WITH 50;
 */
@Entity
@NamedQueries({ @NamedQuery(name = "ReportParam.findAll", query = "select o from ReportParam o") })
@Table(name = "J_REPORT_PARAM")
@SequenceGenerator(name = "ReportParam_Id_Seq_Gen", sequenceName = "id_gen_seq", allocationSize = 50, initialValue = 50)
public class ReportParam implements Serializable {
    private static final long serialVersionUID = 5090893985214156382L;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReportParam_Id_Seq_Gen")
    private BigDecimal id;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 20)
    private String label;
    @Column(name = "DEFAULT_VALUE", length = 20)
    private String defaultValue;
    @Column(name = "IS_SINGLE")
    private boolean isSingle;
    
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Enum.paramType type;

  
    @Column(name = "SELECT_SCOPE_SQL", length=400)
    private String selectScopeSql;
    

    
    
    
    @ManyToOne
    @JoinColumn(name = "REPORT_ID")
    private Report report;

    public ReportParam() {
      

    }


    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public boolean getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(boolean isSingle) {
        this.isSingle = isSingle;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSelectScopeSql() {
        return selectScopeSql;
    }

    public void setSelectScopeSql(String selectScopeSql) {
        this.selectScopeSql = selectScopeSql;
    }

    public Enum.paramType getType() {
        return type;
    }

    public void setType(Enum.paramType type) {
        this.type = type;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ReportParam)) {
            return false;
        }
        final ReportParam other = (ReportParam) object;
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
