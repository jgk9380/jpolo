package entity.report;

import annotation.Enum;
import annotation.Label;

import entity.Employees;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * To create ID generator sequence ID_GEN_SEQ:
 * CREATE SEQUENCE ID_GEN_SEQ INCREMENT BY 50 START WITH 50;
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Report.findAll", query = "select o from Report o") })
@Table(name = "J_REPORT")
@SequenceGenerator(name = "Report_Id_Seq_Gen", sequenceName = "ID_GEN_SEQ", allocationSize = 50, initialValue = 50)
public class Report implements Serializable {

    @SuppressWarnings("compatibility:-4494774898895421430")
    private static final long serialVersionUID = -6940110669260850388L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Report_Id_Seq_Gen")
    private BigDecimal id;

    //申请人填写

    @Column(name = "REPORT_NAME", unique = true, length = 20)
    @Label("报表名称")
    private String reportName;

    @Column(name = "FIELDS_DESC", length = 1000)
    @Label("需求字段")
    private String fieldsDesc;
    
    @Column(name = "TASK_CONFIG", length = 1000)
    @Label("任务配置")
    private String taskConfig;

    @Column(name = "REMARK_DESC", length = 1000)
    @Label("申请说明")
    private String remarkDesc;

    @Column(name = "PARAMS_DESC", length = 1000)
    @Label("参数描叙")
    private String paramsDesc;

    @Column(name = "OUTPUT_DESC", length = 1000)
    @Label("输出字段")
    private String outputDesc;

    //实施人填写
    @Column(name = "DATA_SOURCE", length = 20)
    @Enumerated(EnumType.STRING)
    @Label("数据源")
    private Enum.DataSource dataSource;

    @Column(name = "SQL", length = 200)
    @Label("实现Sql")
    private String sql;

    @Column(name = "IMPLEMENT_REMARK", length = 400)
    @Label("实施说明")
    private String implementRemark;

    @Column(name = "IS_SINGLE")
    @Label("是否单记录")
    private boolean isSingle;

    @OneToOne
    @JoinColumn(name = "TYPE")
    @Label("报表类型")
    private CodeReportType type;

    @OneToOne
    @JoinColumn(name = "USE_TYPE")
    @Label("报表用途")
    private CodeReportUseType useType;

    @OneToOne
    @JoinColumn(name = "CHILD_REPORT_ID")
    private Report childReport;

    @OneToOne(mappedBy = "childReport")
    private Report parentReport;

    @Column(name = "CHILD_REPORT_LINK", length = 400)
    private String childReportLink;

    //程序填写
    @ManyToOne
    @JoinColumn(name = "IMPLEMENT_EMP_ID")
    private Employees implementEmp;

    @ManyToOne
    @JoinColumn(name = "REQUIRE_EMP_ID")
    private Employees requireEmp;

    @Column(name = "REPORT_STATUS", length = 20)
    @Enumerated(EnumType.STRING)
    private Enum.reportStatus reportStatus;

    @Column(name = "REPORT_CYCLE")
    @Enumerated(EnumType.STRING)
    @Label("报表周期")
    private Enum.retportCycle cycle;

    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<ReportParam> reportParams;

    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @OrderBy("seq")
    private List<ReportColumn> reportColumns;

    public Report() {
    }

    public String getChildReportLink() {
        return childReportLink;
    }

    public void setChildReportLink(String childReportLink) {
        this.childReportLink = childReportLink;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setParentReport(Report parentReport) {
        this.parentReport = parentReport;
    }

    public Report getParentReport() {
        return parentReport;
    }

    public Enum.DataSource getDataSource() {
        return dataSource;
    }

    public void setOutputDesc(String outputDesc) {
        this.outputDesc = outputDesc;
    }

    public String getOutputDesc() {
        return outputDesc;
    }

    public void setDataSource(Enum.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getFieldsDesc() {
        return fieldsDesc;
    }

    public void setFieldsDesc(String fieldsDesc) {
        this.fieldsDesc = fieldsDesc;
    }


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Employees getImplementEmp() {
        return implementEmp;
    }

    public void setImplementEmp(Employees implementEmpId) {
        this.implementEmp = implementEmpId;
    }

    public String getImplementRemark() {
        return implementRemark;
    }

    public void setImplementRemark(String implementRemark) {
        this.implementRemark = implementRemark;
    }

    public boolean getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(boolean isSingle) {
        this.isSingle = isSingle;
    }

    public String getParamsDesc() {
        return paramsDesc;
    }

    public void setParamsDesc(String paramsDesc) {
        this.paramsDesc = paramsDesc;
    }

    public String getRemarkDesc() {
        return remarkDesc;
    }

    public void setRemarkDesc(String remarkDesc) {
        this.remarkDesc = remarkDesc;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Enum.reportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Enum.reportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Employees getRequireEmp() {
        return requireEmp;
    }

    public void setRequireEmp(Employees requireEmpId) {
        this.requireEmp = requireEmpId;
    }


    public List<ReportParam> getReportParams() {
        return reportParams;
    }

    public void setReportParams(List<ReportParam> j_REPORT_PARAMSet) {
        this.reportParams = j_REPORT_PARAMSet;
    }

    public ReportParam addReportParam(ReportParam reportParam) {
        getReportParams().add(reportParam);
        reportParam.setReport(this);
        return reportParam;
    }

    public ReportParam removeReportParam(ReportParam reportParam) {
        getReportParams().remove(reportParam);
        reportParam.setReport(null);
        return reportParam;
    }

    public List<ReportColumn> getReportColumns() {
        return reportColumns;
    }

    public void setReportColumns(List<ReportColumn> JReportColumns) {
        this.reportColumns = JReportColumns;
    }

    public ReportColumn addReportColumn(ReportColumn reportColumn) {
        getReportColumns().add(reportColumn);
        reportColumn.setReport(this);
        return reportColumn;
    }

    public ReportColumn removeReportColumn(ReportColumn reportColumn) {
        getReportColumns().remove(reportColumn);
        reportColumn.setReport(null);
        return reportColumn;
    }

    public Report getChildReport() {
        return childReport;
    }

    public void setChildReport(Report childReport) {
        this.childReport = childReport;
    }

    public String toString() {
        return "<<" + this.reportName + ">>  id=" + this.getId();
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Report)) {
            return false;
        }
        final Report other = (Report) object;
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


    public void setType(CodeReportType type) {
        this.type = type;
    }

    public CodeReportType getType() {
        return type;
    }

    public void setUseType(CodeReportUseType useType) {
        this.useType = useType;
    }

    public CodeReportUseType getUseType() {
        return useType;
    }

    public void setCycle(Enum.retportCycle cycle) {
        this.cycle = cycle;
    }

    public Enum.retportCycle getCycle() {
        return cycle;
    }

    public void setTaskConfig(String takConfig) {
        this.taskConfig = takConfig;
    }

    public String getTaskConfig() {
        return taskConfig;
    }
}
