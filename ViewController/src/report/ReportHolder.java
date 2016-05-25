package report;

import assist.database.DbUtils;

import entity.report.Report;
import entity.report.ReportColumn;
import entity.report.ReportParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import javax.persistence.EntityManager;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import web.JSFUtils;

public class ReportHolder {
    EntityManager em;
    Report report; //保存当前显示的报表
    Map<String, Object> paramMap; //参数value保存
    List<Map<String, Object>> valuesHolder; //保存表格数据

    ReportHolder parent;

    public ReportHolder getParentReport() {
        return parent;
    }

    public ReportHolder() {
        super();

    }

    public ReportHolder(Report report) {
        super();
        parent = null;
        this.report = report;
        paramMap = new HashMap<>();
        if (report.getReportParams() != null)
            for (ReportParam rp : report.getReportParams()) {
                //System.out.println("rp.name=" + rp.getName());
                paramMap.put(rp.getName(), null);
            }
    }


    public ReportParam getParam(String paramName) {
        ReportParam rp =
            em.createQuery("select o from ReportParam o where o.report=:report and o.name=:name ",
                           ReportParam.class).setParameter("report", report).setParameter("name",
                                                                                          paramName).getSingleResult();
        if (rp == null)
            JSFUtils.addFacesInformationMessage("获取参数为空");
        return rp;
    }

    public List<SelectItem> getSelectItemList(ReportParam parm) {
        String sql = parm.getSelectScopeSql();
        JdbcTemplate npjdbct;
        try {
            npjdbct = new JdbcTemplate(DbUtils.getDataSource(report.getDataSource().toString()));
            List<Map<String, Object>> l = npjdbct.queryForList(sql);
            List<SelectItem> res = new ArrayList<>();
            for (Map<String, Object> m : l) {
                SelectItem si = new SelectItem();
                si.setLabel((String) m.get("label"));
                si.setValue(m.get("value"));
                res.add(si);
            }
            return res;
        } catch (Exception e) {
            JSFUtils.addFacesInformationMessage("获取选择数据有误：" + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean isSelectAble(ReportParam param) {
        if (param.getSelectScopeSql() == null || param.getSelectScopeSql().length() < 5)
            return false;
        return true;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public List<Map<String, Object>> getReportValue() {
        return valuesHolder;
    }

    public String refresh() {
        System.out.println("begin Query....");
        for (String key : paramMap.keySet()) {
            System.out.println("key=" + key + "  value=" + paramMap.get(key));
            if (paramMap.get(key) == null) {
                JSFUtils.addFacesInformationMessage("参数：" + key + "为空");
                return null;
            }
        }
        String sql = report.getSql();

        System.out.println("sql=" + sql);

        try {
            if (paramMap.keySet().size() == 0) {
                JdbcTemplate npjdbct = new JdbcTemplate(DbUtils.getDataSource(report.getDataSource().toString()));
                valuesHolder = npjdbct.queryForList(sql);
            } else {
                NamedParameterJdbcTemplate npjdbct =
                    new NamedParameterJdbcTemplate(DbUtils.getDataSource(report.getDataSource().toString()));
                valuesHolder = npjdbct.queryForList(sql, paramMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage("错误：" + e.getMessage());
        }

        System.out.println("valuesHolder.size=" + valuesHolder.size());
        JSFUtils.refresh("reportForm");

        System.out.println("end Query....");
        return null;
    }

    public Report getReport() {
        return report;
    }

    public Object getFooterValue(ReportColumn rc) {
        if (rc.getFooterCalcType() == null)
            return null;
        switch (rc.getFooterCalcType()) {
        case cons:
            return rc.getFootText();
        case count:
            {
                int i = 0;
                for (Map<String, Object> m : valuesHolder) {
                    if (m.get(rc.getName()) != null)
                        i++;
                }
                return i;
            }
        case averge:
            {
                Double total = 0d;
                for (Map<String, Object> m : valuesHolder) {
                    Number temp = (Number) m.get(rc.getName());
                    total = total + temp.doubleValue();
                }
                return total / valuesHolder.size();
            }
        case sum:
            {
                Double total = 0d;
                for (Map<String, Object> m : valuesHolder) {
                    Number temp = (Number) m.get(rc.getName());
                    total = total + temp.doubleValue();
                }
                return total;
            }
        default:
            System.out.println("error");
            return null;
        }
    }


    public ReportHolder getChildernReportHolder() {
        ReportHolder childernReportHolder = null;
        try {
            RichTable rt = (RichTable) JSFUtils.findComponentInRoot("reportT");
            if (rt == null) {
                JSFUtils.addFacesInformationMessage("取行数据失败！");
                return null;
            }
            Map<String, Object> rowData = (Map<String, Object>) rt.getSelectedRowData();
            System.out.println("current selet row=" + rowData);
            System.out.println("get Child");
            if (report.getChildReport() == null)
                return null;
            childernReportHolder = new ReportHolder(report.getChildReport());
            childernReportHolder.parent = this;
            System.out.println("childrl=" + report.getChildReportLink());
            /**设置参数*/
            String[] sa = report.getChildReportLink().split(";");
            for (String s : sa) {
                System.out.println("str=\"" + s + "\"");
                String pName = s.substring(0, s.indexOf("="));
                String v = s.substring(s.indexOf("="), s.length());
                String vType = v.substring(1, 2);
                String vName = v.substring(3, v.length());
                System.out.println("pname=" + pName + " vType=" + vType + " vName=" + vName);
                Object pValue = null;
                if (vType.equalsIgnoreCase("v")) {
                    pValue = rowData.get(vName.toUpperCase());
                    System.out.println("getV name=" + vName + " value=" + pValue);
                } else if (vType.equalsIgnoreCase("p")) {
                    pValue = this.getParamMap().get(vName);
                    System.out.println("getP name=" + vName + " value=" + pValue);
                } else {
                    Logger logger = Logger.getLogger(ReportHolder.class);
                    logger.info("错误的参数类型：" + vType);
                    JSFUtils.addFacesInformationMessage("错误的参数类型：" + vType);
                }
                System.out.println("set pname=" + pName + "  value=" + pValue);
                if (pValue == null)
                    JSFUtils.addFacesInformationMessage("参数：" + pName + "值为空");
                childernReportHolder.getParamMap().put(pName, pValue);
            }
            /**设置参数*/
            childernReportHolder.refresh();
            JSFUtils.refresh("reportForm");
        } catch (Exception e) {
            JSFUtils.addFacesInformationMessage("取子表数据失败！" + e.getMessage());
            e.printStackTrace();
        }

        return childernReportHolder;

    }

    public void childQuery(ActionEvent ae) {
        System.out.println("query");
        RichTable rt = (RichTable) ae.getComponent();
        Map<String, Object> m = (Map<String, Object>) rt.getSelectedRowData();
        System.out.println(m);
    }

    public void parasLinkStr(String linkStr) {
        String[] sa = linkStr.split(";");
        for (String s : sa) {
            System.out.println("str=\"" + s + "\"");
            String pName = s.substring(0, s.indexOf("="));
            String v = s.substring(s.indexOf("="), s.length());
            String vType = v.substring(1, 2);
            String vName = v.substring(3, v.length());
            System.out.println("pname=" + pName + "|vType=" + vType + "|vName=" + vName);
        }
    }


    public void refreshPage(ActionEvent ac) {
        System.out.println("refreshPage ");
        JSFUtils.refresh("reportForm");
    }

    public static void main(String[] args) {
        ReportHolder re = new ReportHolder();
        re.parasLinkStr("photo=v.photo;date=p.date");
        //re.parasLinkStr("photo=v.photo");
    }
}
