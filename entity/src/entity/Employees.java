package entity;


import annotation.Label;
import annotation.SelectScope;

import entity.agent.Channel;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "Employees.findAll", query = "select o from Employees o") })
@Label(value = "员工")
@Table(catalog = "comtest")

public class Employees implements Serializable {
    private static final long serialVersionUID = -2940221302290160215L;
    @Id
    @Column(nullable = false, length = 20)
    @Label(value = "员工ID")
    private String id;
    @Column(length = 20)
    @Label(value = "员工名称")
    private String name;
    @Column(name = "ACHIEVEMENT_RATION")
    @Label(value = "绩效系数")
    private BigDecimal achievementRation;
    @ManyToOne
    @JoinColumn(name = "DEPART_ID")
    @Label(value = "部门")
    private Depart depart;
    @Label(value = "岗位补贴")
    private BigDecimal dysubsidy;
    @Column(name = "FIXED_ACHIEVEMENT")
    @Label(value = "固定绩效")
    private BigDecimal fixedAchievement;
    @Column(name = "FIXED_SALARY")
    @Label(value = "固定工资")
    private BigDecimal fixedSalary;
    @Column(length = 40)
    @Label(value = "岗位系数")
    private String grade;
    private byte[] picture;
    @Column(name = "POSITION_TYPE_ID")
    @Label(value = "岗位ID")
    private BigDecimal positionTypeId;
    @Column(length = 20)
    private String shortdesc;
    @Column(name = "STAFF_ID", unique = true, length = 20)
    @Label(value = "发展代码")
    private String staffId;
    @Column(length = 20)
    @Label(value = "电话号码")
    private String tele;
    @OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @Label(value = "系统工号")
    //@SelectScope(sql = "select name from comtest.login_Users where emp_id is null or emp_id=[id]")
    @SelectScope(sql = "select o from LoginUsers o where o.employees is null ")
    private List<LoginUsers> loginUsersList;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    Channel channel;
    
    @Column(name = "posRemark_id")
    BigDecimal posRemarkId;

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public boolean isInvalid() {
        return invalid;
    }

    boolean invalid;

    public Employees() {
    }


    public BigDecimal getAchievementRation() {
        return achievementRation;
    }

    public void setAchievementRation(BigDecimal achievementRation) {
        this.achievementRation = achievementRation;
    }


    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Depart getDepart() {
        return depart;
    }

    public BigDecimal getDysubsidy() {
        return dysubsidy;
    }

    public void setDysubsidy(BigDecimal dysubsidy) {
        this.dysubsidy = dysubsidy;
    }

    public BigDecimal getFixedAchievement() {
        return fixedAchievement;
    }

    public void setFixedAchievement(BigDecimal fixedAchievement) {
        this.fixedAchievement = fixedAchievement;
    }

    public BigDecimal getFixedSalary() {
        return fixedSalary;
    }

    public void setFixedSalary(BigDecimal fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public BigDecimal getPositionTypeId() {
        return positionTypeId;
    }

    public void setPositionTypeId(BigDecimal positionTypeId) {
        this.positionTypeId = positionTypeId;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public List<LoginUsers> getLoginUsersList() {
        return loginUsersList;
    }

    public void setLoginUsersList(List<LoginUsers> loginUsersList) {
        this.loginUsersList = loginUsersList;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Employees)) {
            return false;
        }
        final Employees other = (Employees) object;
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

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setPosRemarkId(BigDecimal posRemarkId) {
        this.posRemarkId = posRemarkId;
    }

    public BigDecimal getPosRemarkId() {
        return posRemarkId;
    }
}
