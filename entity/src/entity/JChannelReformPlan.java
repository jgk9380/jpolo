package entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "JChannelReformPlan.findAll", query = "select o from JChannelReformPlan o") })
@Table(name = "J_CHANNEL_REFORM_PLAN")
@IdClass(JChannelReformPlanPK.class)
public class JChannelReformPlan implements Serializable {
    private static final long serialVersionUID = -1292327269316112864L;
    @Id
    @Column(name = "CHANNEL_ID", nullable = false, length = 16)
    private String channelId;
    @Temporal(TemporalType.DATE)
    @Column(name = "COMPLETE_DATE")
    private Date completeDate;
    @Column(name = "CURRENT_VALUE", length = 30)
    private String currentValue;
    @Column(name = "GRID_OPINION", length = 200)
    private String gridOpinion;
    @Id
    @Column(nullable = false, length = 20)
    private String item;
    @Column(name = "MARKET_OPINION", length = 200)
    private String marketOpinion;
    @Id
    @Column(nullable = false, length = 6)
    private String month;
    @Column(name = "PLAN_VALUE", length = 20)
    private String planValue;
    @Column(name = "REQIRE_RESOURCE", length = 200)
    private String reqireResource;
    @Column(length = 30)
    private String step;

    @Column(length = 200, name = "MARKET_CHECKRESULT")
    private String marketCheckResult;


    public JChannelReformPlan() {
    }


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getGridOpinion() {
        return gridOpinion;
    }

    public void setGridOpinion(String gridOpinion) {
        this.gridOpinion = gridOpinion;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMarketOpinion() {
        return marketOpinion;
    }

    public void setMarketOpinion(String marketOpinion) {
        this.marketOpinion = marketOpinion;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPlanValue() {
        return planValue;
    }

    public void setPlanValue(String planValue) {
        this.planValue = planValue;
    }

    public String getReqireResource() {
        return reqireResource;
    }

    public void setReqireResource(String reqireResource) {
        this.reqireResource = reqireResource;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public void setMarketCheckResult(String marketCheckResult) {
        this.marketCheckResult = marketCheckResult;
    }

    public String getMarketCheckResult() {
        return marketCheckResult;
    }


    public int getStaus() {
        int res=0;
        if(this.getReqireResource()!=null||this.getPlanValue()!=null||this.getStep()!=null||this.completeDate!=null)
            res=1;
        if(this.gridOpinion!=null)
            res=2;
        if(this.marketOpinion!=null)
            res=3;
        if(marketCheckResult!=null)
            res=4;

        return res;
    }
}
