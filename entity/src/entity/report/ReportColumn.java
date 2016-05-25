package entity.report;

import annotation.Enum;
import annotation.Label;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * To create ID generator sequence ID_GEN_SEQ:
 * CREATE SEQUENCE ID_GEN_SEQ INCREMENT BY 50 START WITH 50;
 */
@Entity
@NamedQueries({ @NamedQuery(name = "ReportColumn.findAll", query = "select o from ReportColumn o") })
@Table(name = "J_REPORT_COLUMN")
@SequenceGenerator(name = "ReportColumn_Id_Seq_Gen", sequenceName = "id_gen_seq", allocationSize = 50,
                   initialValue = 50)
public class ReportColumn implements Serializable {
    @SuppressWarnings("compatibility:-983121663697506826")
    private static final long serialVersionUID = -3744374432776206150L;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReportColumn_Id_Seq_Gen")
    private BigDecimal id;
    @Column(name = "NAME")
    @Label("名称")
    private String name;
    
    @Column(name = "ISHIDE")
    @Label("是否隐藏")
    private boolean isHide;
    
    @Column()
    @Label("序号")
    private Integer seq;
    @Column(name = "HEADER_TEXT", length = 20)
    @Label("列标题")        
    private String headerText;
    
    @Column(name = "FOOTER_CALC_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @Label("尾注类型")
    private Enum.footCalcType footerCalcType;
    @Column(name = "FOOT_TEXT", length = 20)
    @Label("尾注")
    private String footText;
      
    @ManyToOne
    @JoinColumn(name = "REPORT_ID")
    private Report report;
    
    @OneToOne
    @JoinColumn(name="CHILD_REPORT_ID")
    @Label("链接子表")
    private Report linkReport;

   
    @Column(name = "CHILD_REPORT_LINK", length = 400)
    @Label("子表链接串")
    private String childReportLinkParams;

    public ReportColumn() {
    }

    public String getChildReportLinkParams() {
        return childReportLinkParams;
    }

    public void setChildReportLinkParams(String childReportLink) {
        this.childReportLinkParams = childReportLink;
    }

    public String getFootText() {
        return footText;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setLinkReport(Report linkReport) {
        this.linkReport = linkReport;
    }

    public Report getLinkReport() {
        return linkReport;
    }

    public String getName() {
        return name;
    }

    public void setFootText(String footText) {
        this.footText = footText;
    }

    public Enum.footCalcType getFooterCalcType() {
        return footerCalcType;
    }

    public void setFooterCalcType(Enum.footCalcType footerCalcType) {
        this.footerCalcType = footerCalcType;
    }


    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }


    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report1) {
        this.report = report1;
    }


    public void setIsHide(boolean isHide) {
        this.isHide = isHide;
    }

    public boolean getIsHide() {
        return isHide;
    }
}
