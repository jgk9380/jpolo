package agent.report.tableModel;

import assist.database.DbUtils;

import assist.utils.EntityManagerFactoryProxy;

import entity.agent.Channel;
import entity.agent.City;
import entity.agent.Grid;
import entity.agent.JCodeIndexType;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import javax.persistence.EntityManager;

import oracle.adf.view.rich.model.AutoSuggestUIHints;

import oracle.adf.view.rich.model.ListOfValuesModel;

import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import web.JSFUtils;
import web.LoginUser;

public class BeneficialTable {

    private  JdbcTemplate jt;
    NamedParameterJdbcTemplate npjp;
    EntityManager em;
    Map<String, Map<String, Object>> benfitTableModel;
    List<Map<String, Object>> benfitTable2;
    String month;
    City city;
    entity.agent.Grid grid;
    entity.agent.Channel channel;
    String appendParam;
    String searchCondition;
    List<String> channelStatusList;

    City queryCity;
    Grid queryGrid;


    public BeneficialTable() {
        super();
        em = EntityManagerFactoryProxy.getEntityManagerFor11g();
        try {
            npjp = new NamedParameterJdbcTemplate(DbUtils.getDataSource("ora11g"));
             jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int getRowCount() {
        return 0;
    }

    int getColCount() {
        return 0;
    }

    public List<JCodeIndexType> getFieldIndexType() {
        String ql = "select o from JCodeIndexType o where o.type='field' order by o.id";
        List<JCodeIndexType> res = em.createQuery(ql, JCodeIndexType.class).getResultList();
        return res;
    }

    public List<JCodeIndexType> getRowIndexType() {
        String ql = "select o from JCodeIndexType o where o.type='row' order by o.id";
        List<JCodeIndexType> res = em.createQuery(ql, JCodeIndexType.class).getResultList();
        return res;
    }

    public String getTableTdValue(BigDecimal rowId, BigDecimal fieldId) {
        Map<String, Object> row = getBenfitTableModel().get(rowId.toString());
        if (row == null)
            return null;
        Object res = row.get(fieldId.toString());
        if (res == null)
            return null;
        return res.toString();
    }

    public void initBenfitTableModel(String month, Channel channel) {
        benfitTableModel = new HashMap<>();
        if (month == null || channel == null)
            return;
        Map<String, String> pram = new HashMap<>();
        pram.put("month", month);
        pram.put("channel", channel.getId());
        //pram.put("channel", "02ADS");
        String fetchSqlString = "select sql from j_code_sql where valid=1 and flag='b1'";
        List<String> sqlList = npjp.queryForList(fetchSqlString, new HashMap<String, Object>(), String.class);
       // System.out.println("sql.size=" + sqlList.size());
        String tempSql = null;
        try {
            for (String sql : sqlList) {
                tempSql = sql;
               // System.out.println("pram month= " + pram.get("month").toString() + "  channel=" + pram.get("channel"));
                List<Map<String, Object>> list = npjp.queryForList(sql, pram);

                System.out.println("mlist.size=" + list.size() + "  " + list);
                for (Map<String, Object> m : list) {
                    System.out.println("mxxxx=" + m);
                    //benfitTableModel.put((String) m.get("ROW_ID"), m);
                    
                    System.out.println("---------"+m.get("ROW_ID"));
                    if(m.get("ROW_ID")==null){
                        System.out.println("null sql="+sql);
                    }
                    Map<String, Object> rowTemp = benfitTableModel.get(m.get("ROW_ID").toString());
                    if (rowTemp == null) {
                        benfitTableModel.put(m.get("ROW_ID").toString(), m);
                    } else {
                        for (String key : rowTemp.keySet()) {
                            m.put(key, rowTemp.get(key));
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error sql mesage=" + e.getMessage() + " \n cause=" + e.getCause());
        }

    }


    public Map<String, Map<String, Object>> getBenfitTableModel() {
        if (benfitTableModel == null) {
            this.initBenfitTableModel(month, channel);
        }

        return benfitTableModel;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void monthChangeLisener(ValueChangeEvent valueChangeEvent) {
        benfitTableModel = null;
        benfitTable2 = null;
    }

    public void cityChangeLisener(ValueChangeEvent vce) {
        queryCity = (City) vce.getNewValue();
        benfitTableModel = null;
        benfitTable2=null;
        grid = null;
        channel = null;
    }

    public void gridChangeLisener(ValueChangeEvent vce) {
        queryGrid = (Grid) vce.getNewValue();
        benfitTable2=null;
        benfitTableModel = null;
        channel = null;
    }

    public void channelChangeLisener(ValueChangeEvent valueChangeEvent) {
        benfitTableModel = null;
    }


    public List<Map<String, Object>> getBenfitTable2() {
        LoginUser lu = (LoginUser) JSFUtils.resolveExpression("#{sessionManager.loginUser}");
        if (lu == null) {
           // System.out.println("没有登陆用户");
            return Collections.emptyList();
        }
        if (this.month == null)
            return Collections.emptyList();
        if (benfitTable2 != null)
            return benfitTable2;
        String selectsql = "select sql from j_code_sql where row_id='99'";
        String sql = npjp.queryForObject(selectsql, new HashMap<String, Object>(), String.class);
        String queryEmpIdSql = "select emp_id from comtest.login_users where name='" + lu.getLoginID() + "'";

        String empId = jt.queryForObject(queryEmpIdSql,String.class);
        Map<String, Object> param = new HashMap<>();
        param.put("month", month);
        param.put("empId", empId);
        param.put("state", this.getChannelStatusList());
        if (appendParam != null) {
            String appendNameIdSql =
                " and (chnl_id like '%'||:name||'%' or chnl_name like  '%'||:name||'%' or agent_name  like  '%'||:name||'%') " +
                " and chnl_id in ( select a.id from j_code_channel a,j_code_grid b ,j_code_city c " +
                "where a.grid_id=b.id and b.city_id=c.id and  a.state in (:state) and  (c.id=:cityId or :cityId='0') and (b.id=:gridId or :gridId='0') )";
            sql = sql + appendNameIdSql;
            param.put("name", appendParam);
        } else {
            sql =
                sql +
                " and chnl_id in ( select a.id from j_code_channel a,j_code_grid b ,j_code_city c " +
                " where a.grid_id=b.id and b.city_id=c.id and  a.state in (:state) and  (c.id=:cityId or :cityId='0') and (b.id=:gridId or :gridId='0') )";
        }

        String cityId = "0";
        if (queryCity != null)
            cityId = queryCity.getId();

        String gridId = "0";
        if (queryGrid != null)
            gridId = queryGrid.getId();
        
        param.put("cityId", cityId);
        param.put("gridId", gridId);


        benfitTable2 = npjp.queryForList(sql, param);
        return benfitTable2;
    }

    public void setAppendParam(String appendParam) {
        this.appendParam = appendParam;
    }

    public String getAppendParam() {
        return appendParam;
    }

    public void appendParamChangeLisener(ValueChangeEvent valueChangeEvent) {
        this.benfitTable2 = null;
    }


    public static void main(String[] args) {
        BeneficialTable b = new BeneficialTable();
        Channel c = new Channel();
        c.setId("02ADS");
        b.initBenfitTableModel("201512", c);
        System.out.println("res size" + b.getBenfitTableModel().keySet().size());
        for (Map m : b.getBenfitTableModel().values()) {
            System.out.println(m);
        }
    }


    public List<SelectItem> sugestedChannelItems(String v) {
        //System.out.println("sugetst。。。。");
        String param = "%" + v + "%";
        String ql = "select o from Channel o where o.name like :v  or o.id like :v"; //增加自有标识
        List<Channel> channelList = em.createQuery(ql, Channel.class).setParameter("v", param).getResultList();
       // System.out.println("sugetst   size=" + channelList.size());
        List<SelectItem> result = new ArrayList<>();
        for (Channel channel : channelList) {
            SelectItem si = new SelectItem();
            si.setLabel(channel.getName() + ":" + channel.getId());
            si.setValue(channel);
            result.add(si);
        }
        return result;
    }


    public void swChangeLisener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

    }


    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void statusChangeLisener(ValueChangeEvent valueChangeEvent) {
        benfitTable2 = null;
    }

    public void setChannelStatusList(List<String> channelStatusList) {
        this.channelStatusList = channelStatusList;
    }

    public List<String> getChannelStatusList() {
        return channelStatusList;
    }

    public void setQueryCity(City queryCity) {
        this.queryCity = queryCity;
    }

    public City getQueryCity() {
        return queryCity;
    }

    public void setQueryGrid(Grid queryGrid) {
        this.queryGrid = queryGrid;
    }

    public Grid getQueryGrid() {
        return queryGrid;
    }
}
