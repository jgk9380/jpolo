package entity.agent;

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
@NamedQueries({ @NamedQuery(name = "ChannelContactDest.findAll", query = "select o from ChannelContactDest o") })
@Table(name = "J_CHANNEL_CONTACT_DEST")
@IdClass(ChannelContactDestPK.class)
public class ChannelContactDest implements Serializable {
    private static final long serialVersionUID = 896886313053148535L;
   
    @ManyToOne
    @Id
    @JoinColumn(name = "CONTACT_ID")
    private ChannelContact channelContact;
    @ManyToOne
    @Id
    @JoinColumn(name = "DEST_TYPE_ID")
    private CodeContactDestType codeContactDestType;
    
    private BigDecimal dest;

    public ChannelContactDest() {
    }

    public ChannelContactDest(ChannelContact channelContact, BigDecimal dest, CodeContactDestType codeContactDestType) {
        this.channelContact = channelContact;
        this.dest = dest;
        this.codeContactDestType = codeContactDestType;
    }


    public BigDecimal getDest() {
        return dest;
    }

    public void setDest(BigDecimal dest) {
        this.dest = dest;
    }


    public ChannelContact getChannelContact() {
        return channelContact;
    }

    public void setChannelContact(ChannelContact channelContact) {
        this.channelContact = channelContact;
    }

    public CodeContactDestType getCodeContactDestType() {
        return codeContactDestType;
    }

    public void setCodeContactDestType(CodeContactDestType codeContactDestType) {
        this.codeContactDestType = codeContactDestType;
    }
}
