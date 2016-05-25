package entity.agent;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@NamedQueries({ @NamedQuery(name = "ChannelContact.findAll", query = "select o from ChannelContact o") })
@Table(name = "J_CHANNEL_CONTACT")
public class ChannelContact implements Serializable {
    private static final long serialVersionUID = -8493363803377450719L;
    @ManyToOne
    @JoinColumn(name = "CHANNEL_ID")
    Channel channel;

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE", nullable = false)
    private Date endDate;
    @Column(name = "ENTER_FEE")
    private BigDecimal enterFee;
    @Id
    @Column(nullable = false, length = 80)
    private String id;
    @Column(name = "RENT_CONTACT_ID", length = 80)
    private String rentContactId;
    @Column(name = "RENT_FEE")
    private BigDecimal rentFee;
    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;
    @Column(name = "SUBSIDY_FEE")
    private BigDecimal subsidyFee;
    @Column(name = "SUBSIDY_PERIOD_RENT_FEE")
    private BigDecimal subsidyPeriodRentFee;
    @OneToMany(mappedBy = "channelContact", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<ChannelContactDest> channelContactDestList;
    

    public ChannelContact() {
    }

    public ChannelContact(Date endDate, BigDecimal enterFee, String id, String rentContactId, BigDecimal rentFee,
                          Date startDate, BigDecimal subsidyFee, BigDecimal subsidyPeriodRentFee) {
        this.endDate = endDate;
        this.enterFee = enterFee;
        this.id = id;
        this.rentContactId = rentContactId;
        this.rentFee = rentFee;
        this.startDate = startDate;
        this.subsidyFee = subsidyFee;
        this.subsidyPeriodRentFee = subsidyPeriodRentFee;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getEnterFee() {
        return enterFee;
    }

    public void setEnterFee(BigDecimal enterFee) {
        this.enterFee = enterFee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRentContactId() {
        return rentContactId;
    }

    public void setRentContactId(String rentContactId) {
        this.rentContactId = rentContactId;
    }

    public BigDecimal getRentFee() {
        return rentFee;
    }

    public void setRentFee(BigDecimal rentFee) {
        this.rentFee = rentFee;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getSubsidyFee() {
        return subsidyFee;
    }

    public void setSubsidyFee(BigDecimal subsidyFee) {
        this.subsidyFee = subsidyFee;
    }

    public BigDecimal getSubsidyPeriodRentFee() {
        return subsidyPeriodRentFee;
    }

    public void setSubsidyPeriodRentFee(BigDecimal subsidyPeriodRentFee) {
        this.subsidyPeriodRentFee = subsidyPeriodRentFee;
    }

    public List<ChannelContactDest> getChannelContactDestList() {
        return channelContactDestList;
    }

    public void setChannelContactDestList(List<ChannelContactDest> CHANNEL_CONTACT_DESTSet) {
        this.channelContactDestList = CHANNEL_CONTACT_DESTSet;
    }

    public ChannelContactDest addChannelContactDest(ChannelContactDest channelContactDest) {
        getChannelContactDestList().add(channelContactDest);
        channelContactDest.setChannelContact(this);
        return channelContactDest;
    }

    public ChannelContactDest removeChannelContactDest(ChannelContactDest channelContactDest) {
        getChannelContactDestList().remove(channelContactDest);
        channelContactDest.setChannelContact(null);
        return channelContactDest;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ChannelContact)) {
            return false;
        }
        final ChannelContact other = (ChannelContact) object;
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
