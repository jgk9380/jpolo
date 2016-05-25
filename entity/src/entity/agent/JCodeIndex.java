package entity.agent;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "JCodeIndex.findAll", query = "select o from JCodeIndex o") })
@Table(name = "J_CODE_INDEX")
public class JCodeIndex implements Serializable {
    private static final long serialVersionUID = 749456929482148198L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(name = "IS_CALC")
    private boolean isCalc;
    @Column(length = 200)
    private String remark;
    @ManyToOne
    @JoinColumn(name = "INDEX_TYPE_ID")
    private JCodeIndexType JCodeIndexType;

    public JCodeIndex() {
    }

   

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }


    public boolean getIsCalc() {
        return isCalc;
    }

    public void setIsCalc(boolean isCalc) {
        this.isCalc = isCalc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public JCodeIndexType getJCodeIndexType() {
        return JCodeIndexType;
    }

    public void setJCodeIndexType(JCodeIndexType JCodeIndexType) {
        this.JCodeIndexType = JCodeIndexType;
    }
}
