package entity;

import java.io.Serializable;

public class JChannelReformPlanPK implements Serializable {
    private String channelId;
    private String item;
    private String month;

    public JChannelReformPlanPK() {
    }

    public JChannelReformPlanPK(String channelId, String item, String month) {
        this.channelId = channelId;
        this.item = item;
        this.month = month;
    }

    public boolean equals(Object other) {
        if (other instanceof JChannelReformPlanPK) {
            final JChannelReformPlanPK otherJChannelReformPlanPK = (JChannelReformPlanPK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
