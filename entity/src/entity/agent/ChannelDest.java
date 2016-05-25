package entity.agent;


import annotation.SelectScope;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "ChannelDest.findAll", query = "select o from ChannelDest o") })
@Table(name = "CHANNEL_DEST")
@IdClass(ChannelDestPK.class)
public class ChannelDest implements Serializable {
    private static final long serialVersionUID = 2023297766713527596L;
    private BigDecimal dest;
    @Id
    @Column(name="YEAR_OR_MONTH",nullable = false)
    private String yearOrMonth;
    @Column(length = 400)
    private String remark;
    @ManyToOne
    @Id
    @JoinColumn(name = "CHANNEL_ID")
    @SelectScope(hql = "select o from Channel o  order by o.id ")
    private Channel JCodeChannel;
    @ManyToOne
    @Id
    @JoinColumn(name = "DEST_TYPE")

    private CodeDestType codeDestType;

    public ChannelDest() {
    }



    public BigDecimal getDest() {
        return dest;
    }

    public void setDest(BigDecimal dest) {
        this.dest = dest;
    }


    public String getYearOrMonth() {
        return yearOrMonth;
    }

    public void setYearOrMonth(String month) {
        this.yearOrMonth = month;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Channel getJCodeChannel() {
        return JCodeChannel;
    }

    public void setJCodeChannel(Channel JCodeChannel1) {
        this.JCodeChannel = JCodeChannel1;
    }

    public CodeDestType getCodeDestType() {
        return codeDestType;
    }

    public void setCodeDestType(CodeDestType codeDestType) {
        this.codeDestType = codeDestType;
    }
}
