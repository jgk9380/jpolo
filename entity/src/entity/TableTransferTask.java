package entity;


import annotation.Enum;

import annotation.Enum.TransCycle;
import annotation.Enum.TransType;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * To create ID generator sequence id_gen_seq:
 * CREATE SEQUENCE id_gen_seq INCREMENT BY 50 START WITH 50;
 */
@Entity
@NamedQueries({ @NamedQuery(name = "JTableTransferTask.findAll", query = "select o from TableTransferTask o") })
@Table(name = "J_TABLE_TRANSFER_TASK",schema="JEMTEST")
@SequenceGenerator(name = "TableTransferTask_Id_Seq_Gen", sequenceName = "id_gen_seq", allocationSize = 50)
public class TableTransferTask implements Serializable {
    private static final long serialVersionUID = -2185502896280814038L;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Enum.TransCycle cycle;
    private Integer day;
    private Integer hour;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TableTransferTask_Id_Seq_Gen")
    private BigDecimal id;
    @Column(length = 200)
    private String remark;
    @Column(name = "SOURCE_DS", length = 20)
    private String sourceDs;
    @Column(name = "SOURCE_SCHEMA", length = 20)
    private String sourceSchema;
    @Column(name = "TABLE_NAME", length = 40)
    private String tableName;
    @Column(name = "TARGET_DS", length = 20)
    private String targetDs;
    @Column(name = "TRANS_TYPE", length = 10)
    @Enumerated(EnumType.STRING)
    private Enum.TransType transType;

    public TableTransferTask() {
    }

    public TableTransferTask(Enum.TransCycle cycle, Integer day, Integer hour, BigDecimal id, String remark, String sourceDs,
                             String sourceSchema, String tableName, String targetDs, Enum.TransType transType) {
        this.cycle = cycle;
        this.day = day;
        this.hour = hour;
        this.id = id;
        this.remark = remark;
        this.sourceDs = sourceDs;
        this.sourceSchema = sourceSchema;
        this.tableName = tableName;
        this.targetDs = targetDs;
        this.transType = transType;
    }

    public Enum.TransCycle getCycle() {
        return cycle;
    }

    public void setCycle(Enum.TransCycle cycle) {
        this.cycle = cycle;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSourceDs() {
        return sourceDs;
    }

    public void setSourceDs(String sourceDs) {
        this.sourceDs = sourceDs;
    }

    public String getSourceSchema() {
        return sourceSchema;
    }

    public void setSourceSchema(String sourceSchema) {
        this.sourceSchema = sourceSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTargetDs() {
        return targetDs;
    }

    public void setTargetDs(String targetDs) {
        this.targetDs = targetDs;
    }

    public Enum.TransType getTransType() {
        return transType;
    }

    public void setTransType(Enum.TransType transType) {
        this.transType = transType;
    }
}
