package entity.agent;

import java.io.Serializable;

import java.math.BigDecimal;

public class ChannelMonthCostPK implements Serializable {
    @SuppressWarnings("compatibility:375719053262003161")
    private static final long serialVersionUID = 1L;
    private String month;
    private String jcodeChannel;
    private BigDecimal codeCostType;   

    public ChannelMonthCostPK() {
    }

    public ChannelMonthCostPK(String month, String JCodeChannel) {
        this.month = month;
        this.jcodeChannel = JCodeChannel;
    }

    public boolean equals(Object other) {
        if (other instanceof ChannelMonthCostPK) {
            final ChannelMonthCostPK otherAgentMonthCostPK = (ChannelMonthCostPK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getJcodeChannel() {
        return jcodeChannel;
    }

    public void setJcodeChannel(String JCodeChannel) {
        this.jcodeChannel = JCodeChannel;
    }

    public void setCodeCostType(BigDecimal codeCostType) {
        this.codeCostType = codeCostType;
    }

    public BigDecimal getCodeCostType() {
        return codeCostType;
    }
}
