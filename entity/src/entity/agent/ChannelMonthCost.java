package entity.agent;

import annotation.Label;


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
@NamedQueries({ @NamedQuery(name = "ChannelMonthCost.findAll", query = "select o from ChannelMonthCost o") })
@Table(name = "CHANNEL_MONTH_COST")
@IdClass(ChannelMonthCostPK.class)
public class ChannelMonthCost implements Serializable {
    private static final long serialVersionUID = -966134188280925833L;


    @ManyToOne
    @Id
    @JoinColumn(name = "CHANNEL_ID")
    @Label("代理商")
    @SelectScope(hql = "select o from Channel o  order by o.id ")
    private Channel jcodeChannel;
    @Id
    @ManyToOne
    @Label("成本类型")
    @JoinColumn(name = "COST_TYPE")
    private CodeCostType codeCostType;  
    @Id
    @Column(nullable = false)
    @Label("月份")
    private String month;
    
    @Label("成本金额")
    private BigDecimal cost;

    public ChannelMonthCost() {
    }

 


    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Channel getJcodeChannel() {
        return jcodeChannel;
    }

    public void setJcodeChannel(Channel JCodeChannel) {
        this.jcodeChannel = JCodeChannel;
    }

    public CodeCostType getCodeCostType() {
        return codeCostType;
    }

    public void setCodeCostType(CodeCostType codeCostType) {
        this.codeCostType = codeCostType;
    }
    
   
}
