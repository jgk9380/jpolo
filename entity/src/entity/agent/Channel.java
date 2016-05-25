package entity.agent;


import entity.Employees;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "JCodeChannel.findAll", query = "select o from Channel o") })
@Table(name = "J_CODE_CHANNEL")
public class Channel implements Serializable {
    private static final long serialVersionUID = 9008734342127681354L;
    @Column(name = "CHNL_PHONE", length = 40)
    private String chnlPhone;

    @Column(name = "CREATE_DATE", length = 40)
    private String createDate;

    @Id
    @Column(nullable = false, length = 16)
    private String id;
    @Column(name = "MANAGER_NAME", length = 100)
    private String managerName;
    @Column(name = "MANANGE_DEPART_ID", length = 40)
    private String manangeDepartId;
    @Column(length = 200)
    private String name;
    @Column(name = "OFFICE_KIND_NAME", length = 100)
    private String officeKindName;
    @Column(length = 6)
    private String state;
    @ManyToOne
    @JoinColumn(name = "MANAGER")
    Employees manager;

    @ManyToOne
    @JoinColumn(name = "GRID_ID")
    Grid grid;
    @OneToMany(mappedBy = "channel", fetch = FetchType.EAGER)
    Set<ChannelContact> channelContactSet;

    public Channel() {
    }


    public String getChnlPhone() {
        return chnlPhone;
    }

    public void setChnlPhone(String chnlPhone) {
        this.chnlPhone = chnlPhone;
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManangeDepartId() {
        return manangeDepartId;
    }

    public void setManangeDepartId(String manangeDepartId) {
        this.manangeDepartId = manangeDepartId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficeKindName() {
        return officeKindName;
    }

    public void setOfficeKindName(String officeKindName) {
        this.officeKindName = officeKindName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String toString() {
        String res = "";
        res = this.getId() + ":" + this.getName();
        return res;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public Employees getManager() {
        return manager;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setChannelContactSet(Set<ChannelContact> channelContactSet) {
        this.channelContactSet = channelContactSet;
    }

    public Set<ChannelContact> getChannelContactSet() {
        if (channelContactSet == null)
            channelContactSet = new HashSet<>();
        return channelContactSet;
    }

    public int getContactSize() {
        if (channelContactSet == null)
            return 0;
        return channelContactSet.size();
    }
}
