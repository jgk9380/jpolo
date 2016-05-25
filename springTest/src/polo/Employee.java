package polo;




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

@Table(catalog = "comtest")

public class Employee implements Serializable {
    private static final long serialVersionUID = -2940221302290160215L;
    @Id
    @Column(nullable = false, length = 20)
  
    private String id;
    @Column(length = 20)

    private String name;
    @Column(name = "ACHIEVEMENT_RATION")
   
    private BigDecimal achievementRation;
   
  

  
    private BigDecimal dysubsidy;
    
    @Column(name = "FIXED_ACHIEVEMENT")
   
    private BigDecimal fixedAchievement;
    @Column(name = "FIXED_SALARY")
  
    private BigDecimal fixedSalary;
    @Column(length = 40)
  
    private String grade;
    private byte[] picture;
    @Column(name = "POSITION_TYPE_ID")
    
    private BigDecimal positionTypeId;
    @Column(length = 20)
    private String shortdesc;
    @Column(name = "STAFF_ID", unique = true, length = 20)
    
    private String staffId;
    @Column(length = 20)

    private String tele;
    @OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  

    
    
 

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public boolean isInvalid() {
        return invalid;
    }

    boolean invalid;

    public Employee() {
    }


    public BigDecimal getAchievementRation() {
        return achievementRation;
    }

    public void setAchievementRation(BigDecimal achievementRation) {
        this.achievementRation = achievementRation;
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

 

    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Employee)) {
            return false;
        }
        final Employee other = (Employee) object;
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

