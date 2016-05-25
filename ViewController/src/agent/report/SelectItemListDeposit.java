package agent.report;

import assist.database.DbUtils;

import assist.utils.EntityManagerFactoryProxy;

import entity.agent.Channel;

import entity.agent.City;

import entity.agent.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import javax.persistence.EntityManager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class SelectItemListDeposit {
    EntityManager em;
    NamedParameterJdbcTemplate njt;
    JdbcTemplate jt;

    public SelectItemListDeposit() {
        super();
        em = EntityManagerFactoryProxy.getEntityManagerFor11g();
        try {
            njt = new NamedParameterJdbcTemplate(DbUtils.getDataSource("ora11g"));
            jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SelectItem> getCitySelectItemList() {
        List<SelectItem> result = new ArrayList<>();
        String ql = "select o from City o"; //增加自有标识
        List<City> cityList = em.createQuery(ql, City.class).getResultList();
        for (City city : cityList) {
            SelectItem si = new SelectItem();
            si.setLabel(city.getName());
            si.setValue(city);
            result.add(si);
        }
        return result;
    }

    public List<SelectItem> getChannelStatusSelectItemList() {
        List<SelectItem> result = new ArrayList<>();
        String[] labels={"正常","清算中","终止","建设中"};
        String[]  values={"0","1","2","9"};
        
        for (int i=0;i<4;i++) {
            SelectItem si = new SelectItem();
            si.setLabel(labels[i]);
            si.setValue(values[i]);
            result.add(si);
        }
        return result;
    }
   

    public List<SelectItem> getGridSelectItemList(City city) {
        if (city == null)
            return Collections.emptyList();
        List<SelectItem> result = new ArrayList<>();
        for (Grid grid : city.getGrids()) {
            SelectItem si = new SelectItem();
            si.setLabel(grid.getName());
            si.setValue(grid);
            result.add(si);
        }
        return result;
    }

    public List<SelectItem> getChannelSelectItemList(Grid grid) {
        if (grid == null)
            return Collections.emptyList();
        List<SelectItem> result = new ArrayList<>();
        String ql = "select o from Channel o where o.grid=:grid order by o.id "; //增加自有标识
        List<Channel> channelList = em.createQuery(ql, Channel.class).setParameter("grid", grid).getResultList();
        for (Channel channel : channelList) {
            SelectItem si = new SelectItem();
            si.setLabel(channel.getName() + ":" + channel.getId());
            si.setValue(channel);
            result.add(si);
        }
        return result;
    }

    public List<SelectItem> getChannelSelectItemList(Grid grid, String sw) {
        if (sw==null||sw.length()<1) {
            if (grid == null)
                return Collections.emptyList();
            List<SelectItem> result = new ArrayList<>();
            String ql = "select o from Channel o where o.grid=:grid order by o.id "; //增加自有标识
            List<Channel> channelList = em.createQuery(ql, Channel.class).setParameter("grid", grid).getResultList();
            for (Channel channel : channelList) {
                SelectItem si = new SelectItem();
                si.setLabel(channel.getName() + ":" + channel.getId());
                si.setValue(channel);
                result.add(si);
            }
            return result;
        } else {
            String param = "%" + sw + "%";            
            String ql = "select o from Channel o where o.name like :v  or o.id like :v"; //增加自有标识
            List<Channel> channelList = em.createQuery(ql, Channel.class).setParameter("v", param).getResultList();
            System.out.println("sugetst   size=" + channelList.size()+"param="+param);
            List<SelectItem> result = new ArrayList<>();
            for (Channel channel : channelList) {
                SelectItem si = new SelectItem();
                si.setLabel(channel.getName() + ":" + channel.getId());
                si.setValue(channel);
                result.add(si);
            }
            return result;
        }
    }

    public List<SelectItem> getMonthSelectItemList() {
        List<SelectItem> result = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            String sql = "select to_char(add_months(sysdate,-" + i + "),'YYYYMM') from dual";
            String date = jt.queryForObject(sql, String.class);
            SelectItem si = new SelectItem();
            si.setLabel(date);
            si.setValue(date);
            result.add(si);
        }
        return result;
    }

    public static void main(String[] args) {
        SelectItemListDeposit si = new SelectItemListDeposit();
        si.getMonthSelectItemList();
    }
}
