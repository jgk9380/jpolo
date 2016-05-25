package entity.task;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "TaskList.findAll", query = "select o from Task o") })
@Table(name = "J_TASK",schema="JEMTEST")
public class Task implements Serializable {
    private static final long serialVersionUID = -4313398865724244532L;
    @Temporal(TemporalType.DATE)
    @Column(name = "CHECK_DATE")
    private Date checkDate;
    
    @Column(name = "REQUIRE_CHECK", length = 20)      
    private boolean requireCheck;
    
    @Column(name = "LAST_B_RESULT", length = 20)      
    private boolean lastBResult;
    
    @Column(name = "CHECK_RESULT", length = 20)
    private String checkResult;
    @Column(length = 200)
    private String content;
    @Column(length = 20)
    private String dutier;
    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    private Date endDate;
    @Id
    @Column(nullable = false)
    private BigDecimal id;
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_DATE")
    private Date lastDate;
    @Column(name = "LAST_RESULT", length = 20)
    private String lastResult;
    @Column(name = "PROCESS_LIST")
    private String processList;
    @Temporal(TemporalType.DATE)
    @Column(name = "PRODUCT_DATE")
    private Date productDate;
    @Column(length = 20)
    private String productor;
    @Column(length = 200)
    private String remark;
    private Integer score;
    private Integer status;
    @Column(length = 50)
    private String tag;
    @Column(name = "TASK_OBJECT", length = 200)
    private String taskObject;
    @Column(name = "COUNT")
    private int count;
    
    @ManyToOne
    @JoinColumn(name = "TYPE")
    private TaskType taskType;
    @ManyToOne
    @JoinColumn(name = "SUBJECT")
    private TaskSubject taskSubject;

    public Task() {
    }



    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDutier() {
        return dutier;
    }

    public void setDutier(String dutier) {
        this.dutier = dutier;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public String getProcessList() {
        return processList;
    }

    public void setProcessList(String processList) {
        this.processList = processList;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTaskObject() {
        return taskObject;
    }

    public void setTaskObject(String taskObject) {
        this.taskObject = taskObject;
    }


    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskSubject getTaskSubject() {
        return taskSubject;
    }

    public void setTaskSubject(TaskSubject taskSubject) {
        this.taskSubject = taskSubject;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Task)) {
            return false;
        }
        final Task other = (Task) object;
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

    public void setRequireCheck(boolean requireCheck) {
        this.requireCheck = requireCheck;
    }

    public boolean isRequireCheck() {
        return requireCheck;
    }

    public void setLastBResult(boolean lastBResult) {
        this.lastBResult = lastBResult;
    }

    public boolean getIsLastBResult() {
        return lastBResult;
    }
}
