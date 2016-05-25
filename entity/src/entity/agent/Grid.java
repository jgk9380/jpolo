package entity.agent;

import entity.Employees;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Set;

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

@Entity(name = "Grid")
@NamedQueries({ @NamedQuery(name = "JCodeGrid.findAll", query = "select o from Grid o") })
@Table(name = "J_CODE_GRID")
public class Grid implements Serializable {
    private static final long serialVersionUID = 7048165064079280065L;
    @Column(name = "CGROUP_ID")
    private String cgroupId;

    @Column(length = 32)
    @Id
    private String id;
    @Column(length = 200)
    private String name;
    @Column(name = "STAFF_ID")
    private BigDecimal staffId;
    @Column(name = "STAFF_NAME", length = 200)
    private String staffName;
    @ManyToOne
    @JoinColumn(name = "MANAGER")
    Employees manager;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    City city;
    //    @OneToMany(mappedBy="grid",fetch=FetchType.EAGER)
    //    Set<Channel> channels;
    //
    //    @OneToMany(mappedBy="grid",fetch=FetchType.EAGER)
    //    Set<SixAddress> SixAddresses;

    public Grid() {
    }

    public String getCgroupId() {
        return cgroupId;
    }

    public void setCgroupId(String cgroupId) {
        this.cgroupId = cgroupId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getStaffId() {
        return staffId;
    }

    public void setStaffId(BigDecimal staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public Employees getManager() {
        return manager;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    //    public void setChannels(Set<Channel> channels) {
    //        this.channels = channels;
    //    }
    //
    //    public Set<Channel> getChannels() {
    //        return channels;
    //    }
}
