package report;

import annotation.Enum;

import assist.database.DbUtils;

import assist.utils.EntityManagerFactoryProxy;

import entity.report.Report;

import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import javax.persistence.EntityManager;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import web.JSFUtils;

public class AdminCareReport {
  
    EntityManager em;
    List<Report> selectReportList;
    //String loginID = JSFUtils.resolveExpressionAsString("#{sessionManager.loginUser.loginID}");

    public AdminCareReport() {
        super();
    }

    public List<SelectItem> getAllCompleteReport() {
        em = EntityManagerFactoryProxy.getEntityManagerFor11g();
        String ql = "select  o from Report o  where o.reportStatus=:status";
        List<Report> reList =
            em.createQuery(ql, Report.class).setParameter("status", Enum.reportStatus.complete).getResultList();
        List<SelectItem> res = new ArrayList<>();
        for (Report re : reList) {
            SelectItem si = new SelectItem();
            si.setLabel(re.toString());
            si.setValue(re);
            res.add(si);
        }

        return res;
    }

    public void setSelectReport(List<Report> selectReportList) {
        this.selectReportList = selectReportList;
    }

    public List<Report> getSelectReport() {
        String loginID = JSFUtils.resolveExpressionAsString("#{sessionManager.loginUser.loginID}");
        if (selectReportList != null)
            return selectReportList;
        String sql =
            "select report_id from emp_care_report " +
            " where emp_id =(select emp_id from comtest.login_users where name=:loginID)";
        NamedParameterJdbcTemplate jdbcTemplate;
        try {
            jdbcTemplate = new NamedParameterJdbcTemplate(DbUtils.getDataSource(Enum.DataSource.ora11g.toString()));
            Map<String, Object> param = new HashMap<>();
            param.put("loginID", loginID);
            List<BigDecimal> temp = jdbcTemplate.queryForList(sql, param, BigDecimal.class);
            selectReportList = new ArrayList<>();
            for (BigDecimal b : temp) {
                Report re = em.find(Report.class, b);
                selectReportList.add(re);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return selectReportList;
    }

    public void save(ActionEvent actionEvent) {
        try {
            String loginID = JSFUtils.resolveExpressionAsString("#{sessionManager.loginUser.loginID}");
            NamedParameterJdbcTemplate jdbcTemplate =
                new NamedParameterJdbcTemplate(DbUtils.getDataSource(Enum.DataSource.ora11g.toString()));
            String sql = "select emp_id from comtest.login_users where name=:loginID";
            Map<String, Object> param = new HashMap<>();
            param.put("loginID", loginID);
            String empID = jdbcTemplate.query(sql, param, new ResultSetExtractor<String>() {
                @Override
                public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                    rs.next();
                    return rs.getString("emp_id");
                }
            });
            sql = "delete from emp_care_report " + "where  emp_id=:empID";
            param = new HashMap<>();
            param.put("empID", empID);
            jdbcTemplate.update(sql, param);

            sql = "insert into emp_care_report(emp_id,report_id) values(:empID,:reportID)";
            if (selectReportList != null)
                for (Report re : selectReportList) {
                    param.put("reportID", re.getId());
                    jdbcTemplate.update(sql, param);
                }
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage(e.getMessage());
        }
        JSFUtils.addFacesInformationMessage("±£´æ³É¹¦");
        return;
    }

    public static void main(String[] args) {
        AdminCareReport acr = new AdminCareReport();
        acr.getAllCompleteReport();
        acr.getSelectReport();
        System.out.println("selectre=" + acr.selectReportList);
    }
}
