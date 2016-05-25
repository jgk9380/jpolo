package report;

import annotation.Enum;

import assist.database.DbUtils;

import assist.utils.EntityManagerFactoryProxy;

import entity.LoginUsers;

import entity.report.Report;
import entity.report.ReportColumn;
import entity.report.ReportParam;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jpolo.iface.polo.IPolo;

import jpolo.impl.polo.Polo;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import web.JSFUtils;


public class ReportAdmin {
    EntityManager em;
    IPolo<Report> requestReportPolo;
    List<Report> reportList;
    Report currentBuildingReport;
    Report currentShowReport;
    AdminCareReport adminCareReport;

    ReportColumn currentColumn;
    ReportParam currentParam;
    ReportHolder reportHoler; //当前查询的报表

    public ReportAdmin() throws NoSuchFieldException, Exception {
        super();
        em = EntityManagerFactoryProxy.getEntityManagerFor11g();
        adminCareReport=new AdminCareReport();
        requestReportPolo = new Polo<>(em, new Report());
        String ql = "select o from Report o  where o.reportStatus=:status order by o.id ";
        reportList = em.createQuery(ql, Report.class).setParameter("status", Enum.reportStatus.request).getResultList();
        //reportHoler=new ReportHolder(em.find(Report.class, new BigDecimal(16483)));
    }


    public ReportHolder getReportHolder() {
        return reportHoler;
    }

    public void setCurrentShowReport(Report currentShowReport) {
        this.currentShowReport = currentShowReport;
        reportHoler = new ReportHolder(currentShowReport);
    }

    public Report getCurrentShowReport() {
        return currentShowReport;
    }

    public IPolo<Report> getRequestReportPolo() {
        return requestReportPolo;
    }

    public Report getCurrentBuildingReport() {
        return currentBuildingReport;
    }

    public ReportColumn getCurrentColumn() {
        return currentColumn;
    }


    public void setCurrentColumn(ReportColumn currentColumn) {
        this.currentColumn = currentColumn;
    }


    public void setCurrentParam(ReportParam currentParam) {
        this.currentParam = currentParam;
    }

    public ReportParam getCurrentParam() {
        return currentParam;
    }


    public List<Report> getReportList() {
        return reportList;
    }

    public List<Report> getCompleteReportList() {
        String ql = "select o from Report o  where o.reportStatus=:status order by o.id ";
        List<Report> res =
            em.createQuery(ql, Report.class).setParameter("status", Enum.reportStatus.complete).getResultList();
        return res;
    }

    public List<Report> getBuildingReportList() {
        String ql = "select o from Report o  where o.reportStatus=:status order by o.id ";
        List<Report> res =
            em.createQuery(ql, Report.class).setParameter("status", Enum.reportStatus.building).getResultList();
        return res;
    }

    public String reportRequestSubmit() {
        try {
            Report report = requestReportPolo.getPolobject();
            String loingID = JSFUtils.resolveExpressionAsString("#{sessionManager.loginUser.loginID}");
            if (loingID != null) {
                LoginUsers lu = em.find(LoginUsers.class, loingID);
                report.setRequireEmp(lu.getEmployee());
            }
            report.setReportStatus(Enum.reportStatus.request);
            if (report.getReportName() == null || report.getParamsDesc() == null || report.getFieldsDesc() == null)
                throw new Exception("填写不完整");
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(report);
            et.commit();
            requestReportPolo = new Polo<>(em, new Report());
            JSFUtils.addFacesInformationMessage("报表需求已提交");
            JSFUtils.refresh("f1");
        } catch (Exception e) {
            JSFUtils.addFacesErrorMessage(e.getMessage());
        }
        return null;
    }


    public void toBuildingReportSelectAction(SelectionEvent se) {
        //currentBuildingReport
        RichTable rt = (RichTable) se.getComponent();
        currentBuildingReport = (Report) rt.getSelectedRowData();
        this.currentColumn = null;
        this.currentParam = null;

        JSFUtils.refresh("panelTab");
        JSFUtils.refresh("pgReport");
        JSFUtils.refresh("pgButton");

    }

    public String submit() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(currentBuildingReport);
        et.commit();
        JSFUtils.addFacesInformationMessage("报表SQl已提交");
        JSFUtils.refresh("paramGroup");
        JSFUtils.refresh("form1");
        return null;
    }

    public Set<String> analyseParams(String sql) {
        List<String> res = new ArrayList<>();
        // String sql = " select " + fieldSql + " from " + fromSql + " where " + whereSql;
        int i = sql.indexOf(":");
        while (i > 0) {
            String regex = ",| |s+" ;                   
            String s = sql.substring(i + 1).split(regex)[0];
            res.add(s);
            System.out.println("s=" + sql.substring(i + 1).split(" ")[0]);
            sql = sql.substring(i + 1);
            i = sql.indexOf(":");
        }
        return new HashSet<String>(res);
    }

    public List<String> analyseColumns(Report rep) {
        List<String> res = new ArrayList<>();
        Enum.DataSource ds = rep.getDataSource();
        String sql = rep.getSql();
        try {
            Map<String, Object> paramMap;
            paramMap = new HashMap<>();
            for (ReportParam rp : rep.getReportParams()) {
                paramMap.put(rp.getName(), null);
            }
            NamedParameterJdbcTemplate jdbcTemplate =
                new NamedParameterJdbcTemplate(DbUtils.getDataSource(ds.toString()));
            List<Map<String, Object>> ml = jdbcTemplate.queryForList(sql, paramMap);
            Map<String, Object> m = null;
            if (ml != null && ml.size() > 0)
                m = ml.get(0);
            else
                JSFUtils.addFacesErrorMessage("没有取得测试数据");
            for (String s : m.keySet()) {
                res.add(s);
            }
        } catch (Exception e) {
            JSFUtils.addFacesErrorMessage("error ana col:" + e.getMessage());
        }
        return res;
    }

    public String initParams() {
        Set<String> pSet = analyseParams(currentBuildingReport.getSql());
       
        //em.merge(currentBuildingReport);
        EntityTransaction et = em.getTransaction();
        et.begin();

        Iterator<ReportParam> it = currentBuildingReport.getReportParams().iterator();
        while (it.hasNext()) {
            ReportParam rp = it.next();
            em.remove(em.merge(rp));
        }
        currentBuildingReport.setReportParams(new ArrayList<ReportParam>());
        et.commit();
        et.begin();

        for (String s : pSet) {
            ReportParam rp = new ReportParam();
            rp.setName(s);
            rp.setLabel(s);
            rp.setType(Enum.paramType.String);
            rp.setIsSingle(true);
            System.out.println("add param:" + s);
            currentBuildingReport.addReportParam(rp);
            em.persist(rp);
            em.merge(currentBuildingReport);
        }
        et.commit();

        JSFUtils.refresh("form1");
        return null;
    }

    public String initColumns() {
        List<String> cList = analyseColumns(currentBuildingReport);
        System.out.println("c.size=" + currentBuildingReport.getReportColumns().size());
        System.out.println("cl.size=" + cList.size());
        EntityTransaction et = em.getTransaction();
        et.begin();
        Iterator<ReportColumn> it = currentBuildingReport.getReportColumns().iterator();
        int i = 0;
        while (it.hasNext()) {
            ReportColumn rc = it.next();
            em.remove(em.merge(rc));
            System.out.println("i=" + i);
            i++;
        }
        currentBuildingReport.setReportColumns(new ArrayList<ReportColumn>());
        et.commit();

        et.begin();
        for (String s : cList) {
            ReportColumn rc1 = new ReportColumn();
            rc1.setName(s);
            rc1.setHeaderText(s);
            System.out.println("add column:" + s + "  rc.id=" + rc1.getId());
            currentBuildingReport.addReportColumn(rc1);
            em.persist(rc1);
        }
        et.commit();
        JSFUtils.refresh("form1");
        return null;
    }


    public static void main(String[] args) throws NoSuchFieldException, Exception {
        ReportAdmin ra = new ReportAdmin();
        String sql = "select * from zdm_real_user where open_mode=:open_mode " +
            "and fee1501>to_char(:fee,'YYYYMM') and xx=:fee ";
      
       Set<String >  s =ra.analyseParams(sql);
       System.out.println(s);
    }


    public void columnSelect(ActionEvent ae) {
        System.out.println("select col");
        //        this.currentColumn = (ReportColumn) ae.getComponent().getAttributes().get("currentCol");
        //        System.out.println("select rc:" + currentColumn.getName());
        JSFUtils.refresh("pgColumn");
        JSFUtils.refresh("form1");
    }


    public void paramSelect(ActionEvent ac) {
        System.out.println("select param");
        JSFUtils.refresh("paramGroup");
        JSFUtils.refresh("pgParam");
    }

    //    public void ReportSelect(ActionEvent ac) {
    //        //强制刷新页面
    //        System.out.println("selectReport="+currentShowReport.getReportName());
    //        reportHoler=new ReportHolder(this.currentShowReport);
    //        JSFUtils.refresh("reportForm");
    //    }


    //    public String ReportSelectAction() {
    //        System.out.println(currentShowReport.getReportName());
    //        reportHoler=new ReportHolder(this.currentShowReport);
    //        JSFUtils.refresh("reportForm");
    //        return null;
    //    }

    public String submitComplete() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        currentBuildingReport.setReportStatus(Enum.reportStatus.complete);
        et.commit();
        JSFUtils.addFacesInformationMessage("已提交");
        return null;
    }

    public void testlong(ActionEvent actionEvent) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String testlong() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String query() {
        String ql = "select o from Report o  order by o.reportStatus,o.id  ";
        reportList = em.createQuery(ql, Report.class).getResultList();
        JSFUtils.refresh("t1");
        return null;
    }

    public String ReportSelect() {
        // Add event code here...
        return null;
    }


    public void initTest(ActionEvent actionEvent) {
        reportHoler = new ReportHolder(this.getCurrentBuildingReport());
        JSFUtils.refresh("dc_g1");
    }


    public void initReport(ActionEvent ae) {
        String status = (String) ae.getComponent().getAttributes().get("status");
        System.out.println("status=" + status);
        String ql = "select o from Report o  where o.reportStatus=:status order by o.id ";
        if (status.equals("request"))
            reportList =
                em.createQuery(ql, Report.class).setParameter("status", Enum.reportStatus.request).getResultList();
        if (status.equals("complete"))
            reportList =
                em.createQuery(ql, Report.class).setParameter("status", Enum.reportStatus.complete).getResultList();
    }

    public void submitEdit(ActionEvent actionEvent) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        currentBuildingReport.setReportStatus(Enum.reportStatus.request);
        et.commit();
        JSFUtils.addFacesInformationMessage("已提交");
    }


    public List<Report> getMyRequestReports() {
        List<Report> res = null;
        String loginID = JSFUtils.resolveExpressionAsString("#{sessionManager.loginUser.loginID}");
        //String loginID="jgk974";
        try {
            NamedParameterJdbcTemplate jdbcTemplate =
                new NamedParameterJdbcTemplate(DbUtils.getDataSource(Enum.DataSource.ora11g.toString()));
            String sql =
                "select id from j_report a,comtest.login_Users c\n" +
                "where c.name=:loginID and a.report_status='complete' and c.emp_id=a.require_emp_id";
            Map<String, Object> param = new HashMap<>();
            param.put("loginID", loginID);
            List<BigDecimal> temp = jdbcTemplate.queryForList(sql, param, BigDecimal.class);

            res = new ArrayList<>();
            for (BigDecimal bd : temp) {
                Report re = em.find(Report.class, bd);
                res.add(re);
            }
            System.out.println("req=" + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Report> getMyCareReports() {

        List<Report> res = null;
        String loginID = JSFUtils.resolveExpressionAsString("#{sessionManager.loginUser.loginID}");
        //String loginID="jgk974";
        try {
            NamedParameterJdbcTemplate jdbcTemplate =
                new NamedParameterJdbcTemplate(DbUtils.getDataSource(Enum.DataSource.ora11g.toString()));
            String sql =
                "select report_id from emp_care_report " +
                "where emp_id =(select emp_id from comtest.login_users where name=:loginID )";
            Map<String, Object> param = new HashMap<>();
            param.put("loginID", loginID);
            List<BigDecimal> temp = jdbcTemplate.queryForList(sql, param, BigDecimal.class);

            res = new ArrayList<>();
            for (BigDecimal bd : temp) {
                Report re = em.find(Report.class, bd);
                res.add(re);
            }
            System.out.println("care=" + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    public List<Report> getOtherReports() {

        List<Report> res = null;
        String loginID = JSFUtils.resolveExpressionAsString("#{sessionManager.loginUser.loginID}");
        //String loginID="jgk974";
        try {
            NamedParameterJdbcTemplate jdbcTemplate =
                new NamedParameterJdbcTemplate(DbUtils.getDataSource(Enum.DataSource.ora11g.toString()));
            String sql =
                "   select id from j_report a,comtest.login_Users c\n" +
                "  where c.name=:loginID and a.require_emp_id<>c.emp_id and a.report_status='complete' \n" +
                "  and a.id not in(select report_id from emp_care_report where emp_id=:loginID  )";
            Map<String, Object> param = new HashMap<>();
            param.put("loginID", loginID);
            List<BigDecimal> temp = jdbcTemplate.queryForList(sql, param, BigDecimal.class);

            res = new ArrayList<>();
            for (BigDecimal bd : temp) {
                Report re = em.find(Report.class, bd);
                res.add(re);
            }
            System.out.println("other=" + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public void reportItemSelect(ValueChangeEvent vc) {
        currentShowReport = (Report) vc.getNewValue();
        //System.out.println("selectReport="+currentShowReport.getReportName());
        reportHoler = new ReportHolder(this.currentShowReport);
        JSFUtils.setExpressionValue("#{sessionScope.currentReport}", null);
        JSFUtils.refresh("reportForm");
    }
    
    public void reportLinkSelect(ActionEvent actionEvent) {
        currentShowReport = (Report) JSFUtils.resolveExpression("#{sessionScope.selectReport}");
        if (currentShowReport == null) {
            System.out.println("select repoert null");
            return;
        }
        reportHoler = new ReportHolder(this.currentShowReport);
        JSFUtils.setExpressionValue("#{sessionScope.currentReport}", null);
        JSFUtils.refresh("reportForm");
    }


    public AdminCareReport getAdminCareReport() {
        return adminCareReport;
    }
}
