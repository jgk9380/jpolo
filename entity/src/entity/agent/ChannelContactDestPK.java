package entity.agent;

import java.io.Serializable;

import java.math.BigDecimal;

public class ChannelContactDestPK implements Serializable {
    private String channelContact;
    private BigDecimal codeContactDestType;

    public ChannelContactDestPK() {
    }

    public ChannelContactDestPK(String channelContact, BigDecimal codeContactDestType) {
        this.channelContact = channelContact;
        this.codeContactDestType = codeContactDestType;
    }

    public boolean equals(Object other) {
        if (other instanceof ChannelContactDestPK) {
            final ChannelContactDestPK otherChannelContactDestPK = (ChannelContactDestPK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getChannelContact() {
        return channelContact;
    }

    public void setChannelContact(String channelContact) {
        this.channelContact = channelContact;
    }

    public BigDecimal getCodeContactDestType() {
        return codeContactDestType;
    }

    public void setCodeContactDestType(BigDecimal codeContactDestType) {
        this.codeContactDestType = codeContactDestType;
    }
}
