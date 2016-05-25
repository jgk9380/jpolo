package entity.task;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "TaskSubject.findAll", query = "select o from TaskSubject o") })
@Table(name = "TASK_SUBJECT")
public class TaskSubject implements Serializable {
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Column(length = 20)
    private String name;


    public TaskSubject() {
    }

    public TaskSubject(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
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

}
