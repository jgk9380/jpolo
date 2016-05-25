package entity.agent;

import java.io.Serializable;

import java.math.BigDecimal;

public class ChannelDestPK implements Serializable {
    @SuppressWarnings("compatibility:-6238466995844442354")
    private static final long serialVersionUID = 1L;
    private String yearOrMonth;
    private String JCodeChannel;
    private BigDecimal codeDestType;

    public ChannelDestPK() {
    }

    public ChannelDestPK(String yearOrMonth, String JCodeChannel) {
        this.yearOrMonth = yearOrMonth;
        this.JCodeChannel = JCodeChannel;
    }

    public boolean equals(Object other) {
        if (other instanceof ChannelDestPK) {
            final ChannelDestPK otherAgentMonthDestPK = (ChannelDestPK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

  
    public String getJCodeChannel() {
        return JCodeChannel;
    }

    public void setJCodeChannel(String JCodeChannel1) {
        this.JCodeChannel = JCodeChannel1;
    }

    public void setCodeDestType(BigDecimal codeDestType) {
        this.codeDestType = codeDestType;
    }

    public BigDecimal getCodeDestType() {
        return codeDestType;
    }

    public void setYearOrMonth(String yearOrMonth) {
        this.yearOrMonth = yearOrMonth;
    }

    public String getYearOrMonth() {
        return yearOrMonth;
    }
}
