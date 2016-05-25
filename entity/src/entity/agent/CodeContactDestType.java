package entity.agent;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "CodeContactDestType.findAll", query = "select o from CodeContactDestType o") })
@Table(name = "J_CODE_CONTACT_DEST_TYPE")
public class CodeContactDestType implements Serializable {
    private static final long serialVersionUID = 2950628846500127799L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(length = 40)
    private String name;
   
    public CodeContactDestType() {
    }

 
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CodeContactDestType)) {
            return false;
        }
        final CodeContactDestType other = (CodeContactDestType) object;
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
    
    public String toString(){
        return name;
    }
}
