package agent;

import assist.database.DbUtils;

import assist.utils.EntityManagerFactoryProxy;

import entity.Depart;
import entity.Employees;

import entity.agent.Channel;
import entity.agent.City;

import entity.agent.Grid;

import entity.agent.SixAddress;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.persistence.EntityManager;

import javax.persistence.EntityTransaction;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;

import web.JSFUtils;

/**��Ա����
 *���ڲ��ּ����������֣�
 *1�� J_code_channel�б�����������������Ĺ�ϵ��
 *2�� J_code_grid����������������Ĺ�ϵ��
 *4�� ����������������ַ�Ĺ�ϵ��������ַ���� J_code_six_address��
 *5�� ӪҵԱ��Ӫҵ���Ķ�Ӧ�ñ�����employee����channel_id��
 *6�� ������Ա��֧����Ա��������employees�е�Depart_id��J_Code_city�б���Depart_id��


 *���Ų����ͻ����������̡���˾Ա�����û���
 *   1���û���ͻ����������̵ĵ�ά����ϵ��

 *У԰��
 *   1��У��������ѧУID�Ķ�Ӧ��ϵ��

 �������ܣ����ŵ�������λ������
     �������������������
 **/
public class EmployeesAdmin {
    EntityManager em;
    City city;
    Grid grid;
    Channel channel;
    SixAddress sixAddress;
    Depart depart;
    Employees employee;

  

    public EmployeesAdmin() {
        super();
        em = EntityManagerFactoryProxy.getEntityManagerFor11g();
       
    }

    public List<City> getCityList() {
        String ql = "select o from City o order by o.id";
        List<City> cityList = em.createQuery(ql, City.class).getResultList();
        return cityList;
    }

    public List<Employees> getEmployeeList() {
        if (depart == null) {
            return Collections.emptyList();
        }
        String ql = "select o from Employees o where o.depart=:depart and o.invalid=true  order by o.positionTypeId";
        List<Employees> cityList = em.createQuery(ql, Employees.class).setParameter("depart", depart).getResultList();
        return cityList;

    }


    public List<Grid> getGridList() {
        if (city == null)
            return Collections.emptyList();
        String ql = "select o from Grid o where o.city=:city order by o.id";
        List<Grid> gridList = em.createQuery(ql, Grid.class).setParameter("city", city).getResultList();
        return gridList;
    }

    public List<Channel> getChannelList() {
        if (grid == null)
            return Collections.emptyList();
        String ql = "select o from Channel  o where o.grid=:grid and o.state=0 order by o.id";
        List<Channel> channelList = em.createQuery(ql, Channel.class).setParameter("grid", grid).getResultList();
        return channelList;
    }

    public List<SixAddress> getSixAddressList() {
        if (grid == null)
            return Collections.emptyList();
        String ql = "select o from SixAddress  o where o.grid=:grid order by o.sixAddress";
        List<SixAddress> sixAddressList =
            em.createQuery(ql, SixAddress.class).setParameter("grid", grid).getResultList();
        return sixAddressList;
    }

    public void citySelectListener(SelectionEvent sc) {
        RichTable rt = (RichTable) sc.getComponent();
        city = (City) rt.getSelectedRowData();
        grid = null;
        channel = null;
        sixAddress = null;
    }

    public void employeeSelect(SelectionEvent sc) {
        RichTable rt = (RichTable) sc.getComponent();
        employee = (Employees) rt.getSelectedRowData();
        System.out.println("employeeSelected:" + employee);
        JSFUtils.refresh("empchan");
    }

    public void GridSelect(SelectionEvent sc) {
        RichTable rt = (RichTable) sc.getComponent();
        grid = (Grid) rt.getSelectedRowData();

    }

    public void channelSelect(SelectionEvent sc) {
        RichTable rt = (RichTable) sc.getComponent();
        channel = (Channel) rt.getSelectedRowData();
    }

    public void sixAddressSelect(SelectionEvent sc) {
        RichTable rt = (RichTable) sc.getComponent();
        sixAddress = (SixAddress) rt.getSelectedRowData();
    }

    public List<SelectItem> getCityManagerSelectItem() {
        List<SelectItem> result = new ArrayList<>();
        String ql = "select o from Employees o where o.positionTypeId=998226";
        List<Employees> empList = em.createQuery(ql, Employees.class).getResultList();
        for (Employees emp : empList) {
            SelectItem si = new SelectItem();
            si.setLabel(emp.getName());
            si.setValue(emp);
            result.add(si);
        }
        return result;
    }

    List<SelectItem> channelSelectItemForEmp;

    public void departChangeListen(ValueChangeEvent vc) {
        //��Ա����Ӫҵ������Ч������
        
        depart = (Depart) vc.getNewValue();
        channelSelectItemForEmp = new ArrayList<>();
        String ql = "select o from Channel o where o.grid.city.depart=:depart and o.state=0 and o.officeKindName like '%����%' order by o.grid.id,o.id"; //�������б�ʶ
        List<Channel> channelList = em.createQuery(ql, Channel.class).setParameter("depart", depart).getResultList();
        for (Channel channel : channelList) {
            SelectItem si = new SelectItem();
            si.setLabel(channel.getGrid().getName() + " : " + channel.getName() + ":" + channel.getId());
            si.setValue(channel);
            channelSelectItemForEmp.add(si);
        }
    }

    public List<SelectItem> getChannelSelectItemForEmp() {
        if (depart == null) {
            channelSelectItemForEmp = null;
            return Collections.emptyList();
        }
        return channelSelectItemForEmp;
    }

    List<SelectItem> positionTypeSelectItem;

    public List<SelectItem> getPositionTypeSelectItem() {
        if (positionTypeSelectItem != null)
            return positionTypeSelectItem;
        positionTypeSelectItem = new ArrayList<>();
        try {
            JdbcTemplate jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
            List<Map<String, Object>> codeList =
                jt.queryForList("select id,value from comtest.code_deposit where type_name='gwlx' and nvl(invalid,1)=1 order by id");
            for (Map<String, Object> code : codeList) {
                SelectItem si = new SelectItem();
                si.setLabel(code.get("value").toString());
                si.setValue(code.get("id"));
                positionTypeSelectItem.add(si);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage("error:" + e.getMessage());
        }

        return positionTypeSelectItem;
    }

    List<SelectItem> departSelectItem;

    public List<SelectItem> getDepartSelectItem() {
        if (departSelectItem != null)
            return departSelectItem;
        departSelectItem = new ArrayList<>();
        String ql = "select o from Depart o";
        List<Depart> departList = em.createQuery(ql, Depart.class).getResultList();
        for (Depart depart : departList) {
            SelectItem si = new SelectItem();
            si.setLabel(depart.getName());
            si.setValue(depart);
            departSelectItem.add(si);
        }
        return departSelectItem;
    }
    List<SelectItem> gridManagerSelectItem;

    public List<SelectItem> getGridManagerSelectItem() {
        if (gridManagerSelectItem != null)
            return gridManagerSelectItem;
        gridManagerSelectItem = new ArrayList<>();
        String ql = "select o from Employees o where o.positionTypeId=10027";
        List<Employees> empList = em.createQuery(ql, Employees.class).getResultList();
        for (Employees emp : empList) {
            SelectItem si = new SelectItem();
            si.setLabel(emp.getName());
            si.setValue(emp);
            gridManagerSelectItem.add(si);
        }
        return gridManagerSelectItem;
    }
    List<SelectItem> channelManagerSelectItem;

    public List<SelectItem> getChannelManagerSelectItem() {
        if (channelManagerSelectItem != null)
            return channelManagerSelectItem;
        channelManagerSelectItem = new ArrayList<>();
        String ql = "select o from Employees o where o.positionTypeId=10028";
        List<Employees> empList = em.createQuery(ql, Employees.class).getResultList();
        for (Employees emp : empList) {
            SelectItem si = new SelectItem();
            si.setLabel(emp.getName());
            si.setValue(emp);
            channelManagerSelectItem.add(si);
        }
        return channelManagerSelectItem;
    }
    List<SelectItem> sixAdddressManagerSelectItem;

    public List<SelectItem> getSixAdddressManagerSelectItem() {
        if (sixAdddressManagerSelectItem != null)
            return sixAdddressManagerSelectItem;
        sixAdddressManagerSelectItem = new ArrayList<>();
        String ql = "select o from Employees o where o.positionTypeId=10026";
        List<Employees> empList = em.createQuery(ql, Employees.class).getResultList();
        for (Employees emp : empList) {
            SelectItem si = new SelectItem();
            si.setLabel(emp.getName());
            si.setValue(emp);
            sixAdddressManagerSelectItem.add(si);
        }
        return sixAdddressManagerSelectItem;
    }

    List<SelectItem> posiRemarkSelectItem;

    public List<SelectItem> getPosiRemarkSelectItem() {
        if (posiRemarkSelectItem != null)
            return posiRemarkSelectItem;
        posiRemarkSelectItem = new ArrayList<>();
        String ql = "select id,remark from code_position_detail order by remark";
        try {
            JdbcTemplate jt;
            jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
            
            List<Map<String, Object>> posRemarkList = jt.queryForList(ql);
            for (Map<String, Object> m : posRemarkList) {
                SelectItem si = new SelectItem();
                si.setLabel((String) m.get("remark"));
                si.setValue(m.get("id"));
                posiRemarkSelectItem.add(si);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return posiRemarkSelectItem;
    }

    public void save(ActionEvent event) {
        try {
            EntityTransaction et= em.getTransaction();
            et.begin();
            em.flush();
            et.commit();
            JSFUtils.addFacesInformationMessage("����ɹ�");
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.addFacesErrorMessage("error" + e.getMessage());
        }
    }


    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Depart getDepart() {
        return depart;
    }


}
