package agent.report;

import agent.report.tableModel.BeneficialTable;

import assist.database.DbUtils;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;

import web.JSFUtils;

public class AgentReport {
    JdbcTemplate jt;
    BeneficialTable beneficialTable;
    PhotoRetainReport photoRetainReport;
    NewRetainReport newRetainReport;
    ReportUtils reportUtils = new ReportUtils();
    SelectItemListDeposit selectItemListDeposit = new SelectItemListDeposit();
    String agentQueryName = "";
    Map<String, String> columnMap;
    List<String> selectColumns;
    String[] fiexedColArray = {
        "chnl_id", "chnl_name", "agent_name", "pzyh", "pzsr", "zbyh", "zbyh_old", "zbsr", "zbsr_old", "syby", "sysr",
        "syxzyh", "syxzyh_fee", "syxzsr", "syljyh", "syljsr", "dyby", "dysr", "dyxzyh", "dyxzyh_fee", "dyxzsr",
        "dyljyh", "dyljsr", "byyh_rate", "bysr_rate", "xzyh_rate", "xzyh_fee_rate", "xzsr_rate", "ljyh_rate",
        "ljxz_rate", "pzyh_rate", "pzsr_rate", "photo_charge", "new_charge", "cost_value", "maoli_value", "maoli_rate",
        "cost_rate", "cost_comm_rate", "zbyh_rate", "zbsr_rate"
    };

    void initColumnMap() {
        columnMap = new HashMap<>();
        columnMap.put("chnl_id", "渠道ID");
        columnMap.put("chnl_name", "渠道名称");
        columnMap.put("pzyh", "拍照用户");
        columnMap.put("agent_name", "渠道经理");
        columnMap.put("pzsr", "拍照收入");
        columnMap.put("zbyh", "指标用户");
        columnMap.put("zbyh_old", "其中存量用户数");
        columnMap.put("zbsr", "指标收入");
        columnMap.put("zbsr_old", "其中存量用户收入");
        columnMap.put("syby", "上月保有数");
        columnMap.put("sysr", "上月保有收入");
        columnMap.put("syxzyh", "上月新增用户数");
        columnMap.put("syxzyh_fee", "上月新增出帐用户数");
        columnMap.put("syxzsr", "上月新增用户收入");
        columnMap.put("syljyh", "上月累计新增用户");
        columnMap.put("syljsr", "上月累计新增收入");
        columnMap.put("dyby", "本月保有数");
        columnMap.put("dysr", "本月保有收入");
        columnMap.put("dyxzyh", "本月新增用户数");
        columnMap.put("dyxzyh_fee", "本月新增出帐用户数");
        columnMap.put("dyxzsr", "本月新增用户收入");
        columnMap.put("dyljyh", "累计新增出帐用户数");
        columnMap.put("dyljsr", "累计新增收入");
        columnMap.put("byyh_rate", "保有用户环比");
        columnMap.put("bysr_rate", "保有收入环比");
        columnMap.put("xzyh_rate", "月新增用户环比");
        columnMap.put("xzyh_fee_rate", "月新增出帐用户环比");
        columnMap.put("xzsr_rate", "月新增收入环比");
        columnMap.put("ljyh_rate", "累计新增出帐数环比");
        columnMap.put("ljxz_rate", "累计新增收入环比");
        columnMap.put("pzyh_rate", "拍照用户保有率");
        columnMap.put("pzsr_rate", "拍照收入保有率");
        columnMap.put("photo_charge", "存量收入");
        columnMap.put("new_charge", "新增收入");
        columnMap.put("cost_value", "费用小计，含渠道费用及其他费用");
        columnMap.put("maoli_value", "毛利额");
        columnMap.put("maoli_rate", "毛利率");
        columnMap.put("cost_rate", "费用占收比");
        columnMap.put("cost_comm_rate", "其中佣金占收比");
        columnMap.put("zbyh_rate", "指标用户完成率");
        columnMap.put("zbsr_rate", "指标收入完成率");
        selectColumns = new ArrayList<>();
        for (String m : fiexedColArray) {
            selectColumns.add(m);
        }
    }

    public List<String> getSelectColumns() {
        List<String> res = new ArrayList<>();
        for (String m : fiexedColArray) {
            if (selectColumns.contains(m))
                res.add(m);
        }
        selectColumns = res;
        return selectColumns;
    }

    public List<SelectItem> getColSelectItems() {
        List<SelectItem> res = new ArrayList<>();
        for (String m : fiexedColArray) {
            SelectItem si = new SelectItem();
            si.setValue(m);
            si.setLabel(columnMap.get(m));
            res.add(si);
        }
        return res;
    }


    public AgentReport() {
        super();
        initColumnMap();
        try {
            jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        photoRetainReport = new PhotoRetainReport(jt);
        newRetainReport = new NewRetainReport(jt);
        beneficialTable = new BeneficialTable();
    }

    public PhotoRetainReport getPhotoRetain() {
        return photoRetainReport;
    }


    public ReportUtils getReportUtils() {
        return reportUtils;
    }

    public SelectItemListDeposit getSelectItemListDeposit() {
        return selectItemListDeposit;
    }


    public NewRetainReport getNewRetainReport() {
        return newRetainReport;
    }


    public BeneficialTable getBeneficialTable() {
        return beneficialTable;
    }


    public List<Map<String, Object>> getChannelSubs() {
        String sql =
            "select c.name,--\n" + "a.channel_id, --代理商ID,\n" + "a.id ,--合同号, \n" +
            "to_char(a.start_date,'YYYYMMDD') start_date, --开始日期,\n" +
            "to_char(a.end_date,'YYYYMMDD') end_date, --结束日期,\n" + "a.enter_fee, --进场费,\n" +
            "a.subsidy_fee, --补贴金额,\n" + "a.rent_contact_id, --房租合同号,\n" + "a.rent_fee, --房租合同年租金,\n" +
            "a.subsidy_period_rent_fee, --补贴期房租金额,\n" + "b.a1, --用户1003本地产品,\n" + "b.a2, --用户1008移网小计,\n" +
            "b.a3, --用户1009其中高端,\n" + "b.a4, --用户1010其中高端合约,\n" + "b.a5, --积分1017折算积分,\n" + "b.a6, --收入2003本地产品,\n" +
            "b.a7 --收入2008移网小计 \n" + "from j_channel_contact a  ,\n" +
            "(select contact_id,sum(case when dest_type_id=1003 then dest else 0 end) a1,\n" +
            " sum(case when dest_type_id=1008 then dest  else 0 end) a2,\n" +
            " sum(case when dest_type_id=1009 then dest else 0 end) a3,\n" +
            " sum(case when dest_type_id=1010 then dest else 0 end) a4,\n" +
            " sum(case when dest_type_id=1017 then dest else 0 end) a5,\n" +
            " sum(case when dest_type_id=2003 then dest else 0 end) a6,\n" +
            " sum( case when dest_type_id=2008 then dest else 0 end) a7\n" +
            " from j_channel_contact_dest  group by contact_id)  b, j_code_channel c \n" +
            " where a.id=contact_id（+）and a.channel_id=c.id  and c.name like :agentQueryName \n";
        //System.out.println("sql="+sql);
        Map<String, Object> params = new HashMap<>();
        params.put("agentQueryName", "%" + agentQueryName + "%");
        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(jt);
        return npjt.queryForList(sql, params);
    }

    public void setAgentQueryName(String agentQueryName) {
        this.agentQueryName = agentQueryName;
    }

    public String getAgentQueryName() {
        return agentQueryName;
    }

    public void setSelectColumns(List<String> selectColumns) {
        this.selectColumns = selectColumns;
        //System.out.println("col size="+selectColumns.size());
    }


    public Map<String, String> getColumnMap() {
        return columnMap;
    }

    public void selectSubmitActionListener(ActionEvent actionEvent) {
        JSFUtils.hidePopup("p1");
    }
}
