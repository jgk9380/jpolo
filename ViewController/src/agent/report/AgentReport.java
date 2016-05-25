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
        columnMap.put("chnl_id", "����ID");
        columnMap.put("chnl_name", "��������");
        columnMap.put("pzyh", "�����û�");
        columnMap.put("agent_name", "��������");
        columnMap.put("pzsr", "��������");
        columnMap.put("zbyh", "ָ���û�");
        columnMap.put("zbyh_old", "���д����û���");
        columnMap.put("zbsr", "ָ������");
        columnMap.put("zbsr_old", "���д����û�����");
        columnMap.put("syby", "���±�����");
        columnMap.put("sysr", "���±�������");
        columnMap.put("syxzyh", "���������û���");
        columnMap.put("syxzyh_fee", "�������������û���");
        columnMap.put("syxzsr", "���������û�����");
        columnMap.put("syljyh", "�����ۼ������û�");
        columnMap.put("syljsr", "�����ۼ���������");
        columnMap.put("dyby", "���±�����");
        columnMap.put("dysr", "���±�������");
        columnMap.put("dyxzyh", "���������û���");
        columnMap.put("dyxzyh_fee", "�������������û���");
        columnMap.put("dyxzsr", "���������û�����");
        columnMap.put("dyljyh", "�ۼ����������û���");
        columnMap.put("dyljsr", "�ۼ���������");
        columnMap.put("byyh_rate", "�����û�����");
        columnMap.put("bysr_rate", "�������뻷��");
        columnMap.put("xzyh_rate", "�������û�����");
        columnMap.put("xzyh_fee_rate", "�����������û�����");
        columnMap.put("xzsr_rate", "���������뻷��");
        columnMap.put("ljyh_rate", "�ۼ���������������");
        columnMap.put("ljxz_rate", "�ۼ��������뻷��");
        columnMap.put("pzyh_rate", "�����û�������");
        columnMap.put("pzsr_rate", "�������뱣����");
        columnMap.put("photo_charge", "��������");
        columnMap.put("new_charge", "��������");
        columnMap.put("cost_value", "����С�ƣ����������ü���������");
        columnMap.put("maoli_value", "ë����");
        columnMap.put("maoli_rate", "ë����");
        columnMap.put("cost_rate", "����ռ�ձ�");
        columnMap.put("cost_comm_rate", "����Ӷ��ռ�ձ�");
        columnMap.put("zbyh_rate", "ָ���û������");
        columnMap.put("zbsr_rate", "ָ�����������");
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
            "select c.name,--\n" + "a.channel_id, --������ID,\n" + "a.id ,--��ͬ��, \n" +
            "to_char(a.start_date,'YYYYMMDD') start_date, --��ʼ����,\n" +
            "to_char(a.end_date,'YYYYMMDD') end_date, --��������,\n" + "a.enter_fee, --������,\n" +
            "a.subsidy_fee, --�������,\n" + "a.rent_contact_id, --�����ͬ��,\n" + "a.rent_fee, --�����ͬ�����,\n" +
            "a.subsidy_period_rent_fee, --�����ڷ�����,\n" + "b.a1, --�û�1003���ز�Ʒ,\n" + "b.a2, --�û�1008����С��,\n" +
            "b.a3, --�û�1009���и߶�,\n" + "b.a4, --�û�1010���и߶˺�Լ,\n" + "b.a5, --����1017�������,\n" + "b.a6, --����2003���ز�Ʒ,\n" +
            "b.a7 --����2008����С�� \n" + "from j_channel_contact a  ,\n" +
            "(select contact_id,sum(case when dest_type_id=1003 then dest else 0 end) a1,\n" +
            " sum(case when dest_type_id=1008 then dest  else 0 end) a2,\n" +
            " sum(case when dest_type_id=1009 then dest else 0 end) a3,\n" +
            " sum(case when dest_type_id=1010 then dest else 0 end) a4,\n" +
            " sum(case when dest_type_id=1017 then dest else 0 end) a5,\n" +
            " sum(case when dest_type_id=2003 then dest else 0 end) a6,\n" +
            " sum( case when dest_type_id=2008 then dest else 0 end) a7\n" +
            " from j_channel_contact_dest  group by contact_id)  b, j_code_channel c \n" +
            " where a.id=contact_id��+��and a.channel_id=c.id  and c.name like :agentQueryName \n";
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
