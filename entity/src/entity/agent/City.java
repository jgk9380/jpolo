package entity.agent;

import entity.Depart;
import entity.Employees;

import java.io.Serializable;

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

@Entity(name = "City")
@NamedQueries({ @NamedQuery(name = "City.findAll", query = "select o from City o") })
@Table(name = "J_CODE_CITY")
public class City implements Serializable {
    private static final long serialVersionUID = -7398445542759111713L;
    @Id
    @Column(length = 8)
    private String id;
    @Column(length = 200)
    private String name;
    @ManyToOne  
    @JoinColumn(name = "MANAGER")
    Employees manager;
    @OneToMany(mappedBy="city",fetch=FetchType.EAGER)
    Set<Grid> grids; 
    
    @ManyToOne  
    @JoinColumn(name = "DEPART_ID")
    Depart depart;
    

    public City() {
    }

    public City(String id, String name) {
        this.id = id;
        this.name = name;
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

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public Employees getManager() {
        return manager;
    }

    public void setGrids(Set<Grid> grids) {
        this.grids = grids;
    }

    public Set<Grid> getGrids() {
        return grids;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Depart getDepart() {
        return depart;
    }
}
