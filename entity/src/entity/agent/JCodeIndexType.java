package entity.agent;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "JCodeIndexType.findAll", query = "select o from JCodeIndexType o") })
@Table(name = "J_CODE_INDEX_TYPE")
public class JCodeIndexType implements Serializable {
    private static final long serialVersionUID = 530993652049576664L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(name = "IS_CALC")
    private Integer isCalc;
    @Column(length = 60)
    private String remark;
    @Column(length = 20)
    private String type;
    @OneToMany(mappedBy = "JCodeIndexType", cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.EAGER)
    @OrderBy(value = "id ASC") 
    private List<JCodeIndex> JCodeIndexList;

    public JCodeIndexType() {
    
    }

    public JCodeIndexType(BigDecimal id, Integer isCalc, String remark, String type) {
        this.id = id;
        this.isCalc = isCalc;
        this.remark = remark;
        this.type = type;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getIsCalc() {
        return isCalc;
    }

    public void setIsCalc(Integer isCalc) {
        this.isCalc = isCalc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<JCodeIndex> getJCodeIndexList() {
        return JCodeIndexList;
    }

    public void setJCodeIndexList(List<JCodeIndex> JCodeIndexList) {
        this.JCodeIndexList = JCodeIndexList;
    }

    public JCodeIndex addJCodeIndex(JCodeIndex JCodeIndex) {
        getJCodeIndexList().add(JCodeIndex);
        JCodeIndex.setJCodeIndexType(this);
        return JCodeIndex;
    }

    public JCodeIndex removeJCodeIndex(JCodeIndex JCodeIndex) {
        getJCodeIndexList().remove(JCodeIndex);
        JCodeIndex.setJCodeIndexType(null);
        return JCodeIndex;
    }
}
