package agent;

import assist.utils.EntityManagerFactoryProxy;

import entity.agent.Channel;
import entity.agent.ChannelContact;
import entity.agent.ChannelContactDest;
import entity.agent.City;
import entity.agent.CodeContactDestType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import web.JSFUtils;

public class ChannelContactAdmin {
    Channel channel;
    EntityManager em;
    ChannelContact cc;
    City city;
    String agentQueryName="";


    public ChannelContactAdmin() {
        super();
        em = EntityManagerFactoryProxy.getEntityManagerFor11g();

    }

    public List<SelectItem> getChannelSelectItemList() {
        List<SelectItem> result = new ArrayList<>();
        String ql =
            "select o from Channel o where o.grid is not null and o.grid.city is not null order by o.grid.city.id,o.grid.id,o.id"; //增加自有标识
        List<Channel> channelList = em.createQuery(ql, Channel.class).getResultList();
        for (Channel channel : channelList) {
            SelectItem si = new SelectItem();
            si.setLabel(channel.getGrid().getCity().getName() + ":" + channel.getGrid().getName() + ":" +
                        channel.getName() + ":" + channel.getId());
            si.setValue(channel);
            result.add(si);
        }
        return result;
    }

    public ChannelContact getCc() {
        return cc;
    }

    public List<City> getCityList() {
        String ql = "select o from City o order by o.id";
        List<City> cityList = em.createQuery(ql, City.class).getResultList();
        return cityList;
    }
    List<Channel> channelList;

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void citySelect(SelectionEvent sc) {
        RichTable rt = (RichTable) sc.getComponent();
        city = (City) rt.getSelectedRowData();
        channel = null;
        cc = null;
        String ql = "select o from Channel o where o.grid is not null and o.grid.city=:city order by o.id";
        channelList = em.createQuery(ql, Channel.class).setParameter("city", city).getResultList();
    }

    public void channelSelect(SelectionEvent sc) {
        RichTable rt = (RichTable) sc.getComponent();
        channel = (Channel) rt.getSelectedRowData();
        cc = null;
    }

    public void save(ActionEvent actionEvent) {
        if (cc == null) {
            JSFUtils.addFacesErrorMessage("cc is null;");
            return;
        }
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            cc.getChannel().getChannelContactSet().add(cc);
            em.merge(cc);
            em.getEntityManagerFactory().getCache().evict(cc.getClass());
            et.commit();
            JSFUtils.addFacesInformationMessage("保存成功");
            cc = null;
        } catch (Exception e) {
            JSFUtils.addFacesErrorMessage("error:" + e.getMessage());
            if (et.isActive())
                et.rollback();
            return;
        }
    }

    public void addDest(ActionEvent ac) {
        ChannelContactDest ccd = new ChannelContactDest();
        ccd.setChannelContact(cc);
        List<ChannelContactDest> scc = cc.getChannelContactDestList();
        if (scc == null)
            scc = new ArrayList<>();
        scc.add(ccd);
        cc.setChannelContactDestList(scc);
    }

    public List<SelectItem> getCodeContactDestTypeSelectItem() {
        List<SelectItem> result = new ArrayList<>();
        String ql = "select o from CodeContactDestType o ";
        List<CodeContactDestType> ccdtl = em.createQuery(ql, CodeContactDestType.class).getResultList();
        for (CodeContactDestType ccdt : ccdtl) {
            SelectItem si = new SelectItem();
            si.setLabel(ccdt.toString());
            si.setValue(ccdt);
            result.add(si);
        }
        return result;
    }


    public void addContact(ActionEvent actionEvent) {
        if (channel == null) {
            JSFUtils.addFacesInformationMessage("请选择代理商");
            return;
        }
        cc = new ChannelContact();
        cc.setChannel(channel);
    }

    public City getCity() {
        return city;
    }

    public Channel getChannel() {
        return channel;
    }

    public void contactSelect(ActionEvent ae) {
        String contactId = (String) ae.getComponent().getAttributes().get("contactId");
        //System.out.println("contactId" + contactId);
        cc = em.find(ChannelContact.class, contactId);
        JSFUtils.refresh("contpgl");
        JSFUtils.refresh("contForm");
    }

    public void setCc(ChannelContact cc) {
        this.cc = cc;
    }

    public void setAgentQueryName(String agentQueryName) {
        this.agentQueryName = agentQueryName;
    }

    public String getAgentQueryName() {
        return agentQueryName;
    }

    public void queryAgent(ActionEvent actionEvent) {
        String ql = "select o from Channel o where o.grid is not null and o.grid.city=:city and o.name like :agentQueryName order by o.id";
        channelList = em.createQuery(ql, Channel.class).setParameter("city", city).setParameter("agentQueryName", "%"+agentQueryName+"%").getResultList();
    }
}
