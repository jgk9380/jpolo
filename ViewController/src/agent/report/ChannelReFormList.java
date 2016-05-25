package agent.report;

import assist.database.DbUtils;

import entity.agent.City;

import entity.agent.Grid;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import web.JSFUtils;
import web.LoginUser;

public class ChannelReFormList {
    String month;
    JdbcTemplate jt;
    City queryCity;
    Grid queryGrid;
    ReformPlan reformPlan;

    public ChannelReFormList() {
        super();
        try {
            jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    List<Map<String, Object>> reFormList;

    public List<Map<String, Object>> getReFromList() {
        LoginUser lu = (LoginUser) JSFUtils.resolveExpression("#{sessionManager.loginUser}");
        if (lu == null) {
            // System.out.println("没有登陆用户");
            return Collections.emptyList();
        }
        String queryEmpIdSql = "select emp_id from comtest.login_users where name='" + lu.getLoginID() + "'";
        String empId = jt.queryForObject(queryEmpIdSql, String.class);
        if (reFormList != null)
            return reFormList;
        if (month == null)
            return Collections.emptyList();
        String qsqlsql = "select sql from j_code_sql where row_id=98";

        String sql = jt.queryForObject(qsqlsql, String.class);
        //System.out.println("sql=" + sql);
        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(jt);
        Map<String, Object> param = new HashMap<>();
        String cityId = "0";

        if (queryCity != null)
            cityId = queryCity.getId();

        String gridId = "0";

        if (queryGrid != null)
            gridId = queryGrid.getId();

        System.out.println("grid=" + gridId);
        param.put("month", month);
        param.put("empId", empId);
        param.put("cityId", cityId);
        param.put("gridId", gridId);
        //System.out.println("month="+month+" empId="+empId+" cityId="+cityId+"  gridId="+gridId);
        reFormList = npjt.queryForList(sql, param);

        return reFormList;
    }

    public void setMonth(String month) {
        this.month = month;
        reFormList = null;
    }

    public String getMonth() {
        return month;
    }

    public void monthChangerListen(ValueChangeEvent vce) {
        month = (String) vce.getNewValue();
        reFormList = null;
    }

    public void setQueryGrid(Grid queryGrid) {
        this.queryGrid = queryGrid;
    }

    public Grid getQueryGrid() {
        return queryGrid;
    }

    public void setQueryCity(City queryCity) {
        this.queryCity = queryCity;
    }

    public City getQueryCity() {
        return queryCity;
    }

    public void cityChangeLisener(ValueChangeEvent valueChangeEvent) {
        queryCity = (City) valueChangeEvent.getNewValue();
        reFormList = null;
    }

    public void gridChangeLisener(ValueChangeEvent valueChangeEvent) {
        queryGrid = (Grid) valueChangeEvent.getNewValue();
        reFormList = null;
    }


    public String reformPlanAction() {
        String month = JSFUtils.resolveExpressionAsString("#{sessionScope.month}");
        String chnnelId = JSFUtils.resolveExpressionAsString("#{sessionScope.channelId}");
        System.out.println("month=" + month + " chnnelId=" + chnnelId);
        reformPlan = new ReformPlan(month, chnnelId);
        for (Map<String, Object> m : reFormList) {
            if (m.get("CHNL_ID").equals(chnnelId))
                reformPlan.init(m);
        }
        JSFUtils.showPopup("p1");
        return null;
    }

    public String getTitle(String month, String chnnelId) {
        System.out.println("month=" + month + " chnnelId=" + chnnelId);
        ReformPlan perReformPlan = new ReformPlan(month, chnnelId);
        for (Map<String, Object> m : reFormList) {
            if (m.get("CHNL_ID").equals(chnnelId))
                perReformPlan.init(m);
        }
        //渠道经理
        System.out.println("渠道经理" +
                           JSFUtils.resolveExpressionAsBoolean("#{sessionManager.loginUser.positionTypeId==10028}") +
                           "  " + perReformPlan.getStatus());
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionManager.loginUser.positionTypeId==10028}")) {
            if (perReformPlan.getStatus() == 0)
                return "待填写";
            if (perReformPlan.getStatus() == 1)
                return "网格审核";
            if (perReformPlan.getStatus() == 2)
                return "待市场部审核";
            if (perReformPlan.getStatus() == 3)
                return "待市场部核查";
            if (perReformPlan.getStatus() == 4)
                return "整改完成";
        }
        //网格经理
        System.out.println("网格经理" +
                           JSFUtils.resolveExpressionAsBoolean("#{sessionManager.loginUser.positionTypeId==10027}"));
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionManager.loginUser.positionTypeId==10027}")) {
            if (perReformPlan.getStatus() == 0)
                return "待填写";
            if (perReformPlan.getStatus() == 1)
                return "待审核";
            if (perReformPlan.getStatus() == 2)
                return "待市场部审核";
            if (perReformPlan.getStatus() == 3)
                return "待市场部核查";
            if (perReformPlan.getStatus() == 4)
                return "整改完成";
        }

        //市场部审核
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionManager.loginUser.isHasAuth('marketcheck')}")) {
            if (perReformPlan.getStatus() == 0)
                return "待填写";
            if (perReformPlan.getStatus() == 1)
                return "待网格审核";
            if (perReformPlan.getStatus() == 2)
                return "待市场部审核";
            if (perReformPlan.getStatus() == 3)
                return "待市场部核查";
            if (perReformPlan.getStatus() == 4)
                return "整改完成";
        }
        return "查看";
    }

    public ReformPlan getReformPlan() {
        return reformPlan;
    }

    public void setJt(JdbcTemplate jt) {
        this.jt = jt;
    }

    public JdbcTemplate getJt() {
        return jt;
    }

    public void setReformPlan(ReformPlan reformPlan) {
        this.reformPlan = reformPlan;
    }
}
