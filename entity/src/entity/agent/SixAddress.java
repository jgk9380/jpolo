package entity.agent;

import entity.Employees;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "SixAddress.findAll", query = "select o from SixAddress o") })
@Table(name = "J_CODE_SIX_ADDRESS")
public class SixAddress implements Serializable {
    private static final long serialVersionUID = 943810491571749865L; 
    @Id
    @Column(name = "SIX_ADDRESS", nullable = false, length = 400)
    private String sixAddress;    
    @ManyToOne
    @JoinColumn(name = "GRID_ID")
    private Grid grid;
    
    @ManyToOne
    @JoinColumn(name = "MANAGER")
    private Employees manager;

    public SixAddress() {
    
    }
    
    
    public String getSixAddress() {
        return sixAddress;
    }

    public void setSixAddress(String sixAddress) {
        this.sixAddress = sixAddress;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public Employees getManager() {
        return manager;
    }
}
