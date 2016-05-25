package agent.report;

import assist.database.DbUtils;

import assist.utils.EntityManagerFactoryProxy;

import entity.JChannelReformPlan;

import entity.JChannelReformPlanPK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import javax.persistence.EntityManager;

import javax.persistence.EntityTransaction;

import org.springframework.jdbc.core.JdbcTemplate;

import web.JSFUtils;

public class ReformPlan {
    String month, channelCode;
    JdbcTemplate jt;
    EntityManager em;

    List<JChannelReformPlan> crpList;

    public ReformPlan(String month, String channelCode) {
        this.month = month;
        this.channelCode = channelCode;
        try {
            jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
            em = EntityManagerFactoryProxy.getEntityManagerFor11g();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JChannelReformPlan load(String item, String value) {
        JChannelReformPlanPK jrpp = new JChannelReformPlanPK(channelCode, item, month);
        JChannelReformPlan re = em.find(JChannelReformPlan.class, jrpp);
        if (re == null) {
            re = new JChannelReformPlan();
            re.setMonth(month);
            re.setChannelId(channelCode);
            re.setItem(item);
            re.setCurrentValue(value);
        }
        return re;
    }

    public void init(Map<String, Object> m) {
        crpList = new ArrayList<>();
        crpList.add(load("人均月产能低<100", m.get("arg_charge").toString()));
        crpList.add(load("用户存量保有低<70%", m.get("curr_user_by").toString()));
        crpList.add(load("用户新增保有低<70%", m.get("new_fee_by").toString()));
        crpList.add(load("收入存量保有低<70%", m.get("curr_fee_by").toString()));
        crpList.add(load("收入新增保有低<70%", m.get("new_fee_by").toString()));
        crpList.add(load("毛利为负", (String) m.get("maoli_fee").toString()));
        crpList.add(load("费用占收比高>60%", m.get("cost_rate").toString()));
        EntityTransaction et = em.getTransaction();
        et.begin();
        for (JChannelReformPlan crp : crpList) {
            em.merge(crp);
        }
        et.commit();
    }


    public List<JChannelReformPlan> getCrpList() {
        return crpList;
    }

    public void saveCrpList(ActionEvent actionEvent) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        for (JChannelReformPlan jrp : crpList) {
            em.merge(jrp);
        }
        et.commit();
        JSFUtils.addFacesInformationMessage("保存成功");
        JSFUtils.hidePopup("p1");
    }

    public int getStatus() {
        int res = 4;
        for (JChannelReformPlan rp : crpList) {
            int stauts = rp.getStaus();
            if (stauts <= res)
                res = stauts;
        }
        return res;
    }
   
    
}
