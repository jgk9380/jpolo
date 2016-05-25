package agent.report;

import assist.database.DbUtils;

import entity.agent.Channel;
import entity.agent.City;

import entity.agent.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 新增保有
 */
public class NewRetainReport {
    JdbcTemplate jt;
    boolean singleUnit = false;
    String beginMonth;
    String endMonth;
    City city;
    entity.agent.Grid grid;
    entity.agent.Channel channel;
    NewRetainTool newRetainTool;

    public NewRetainReport(JdbcTemplate jt) {
        super();
        this.jt = jt;
        newRetainTool = new NewRetainTool(jt);
        String sql = "select to_char(add_months(sysdate,-11),'YYYYMM') from dual";
        beginMonth = jt.queryForObject(sql, String.class);
    }
    List<Map<String, Object>> reatianReport;

    public List<Map<String, Object>> getReatianReport() {
        if (reatianReport != null)
            return reatianReport;

        if (beginMonth == null)
            return Collections.emptyList();
        if (singleUnit == false) {
            if (grid != null) {
                reatianReport = newRetainTool.getGridLm(grid, beginMonth);
                return reatianReport;
            }
            if (city != null) {
                reatianReport = newRetainTool.getCityLm(city, beginMonth);
                return reatianReport;
            }
            reatianReport = newRetainTool.getAllSql(beginMonth);
            return reatianReport;
        } else {
            if (channel != null) {
                reatianReport = newRetainTool.getSingleChannelNewRetain(beginMonth,channel);
                return reatianReport;
            }
            if (grid != null) {
                reatianReport = newRetainTool.getSingleGridNewRetain(beginMonth,grid);
                return reatianReport;
            }
            if (city != null) {
                reatianReport = newRetainTool.getSingleCityNewRetain( beginMonth,city);
                return reatianReport;
            }
            reatianReport = newRetainTool.getSingleNewRetain(beginMonth);
            return reatianReport;
        }
    }


    public void setBeginMonth(String beginMonth) {
        this.beginMonth = beginMonth;
    }

    public String getBeginMonth() {
        return beginMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getEndMonth() {
        return endMonth;
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


    public void beginMonthChangeLisener(ValueChangeEvent valueChangeEvent) {
        beginMonth = (String) valueChangeEvent.getNewValue();
        reatianReport = null;

    }

    public void endMonthChangeLisener(ValueChangeEvent valueChangeEvent) {
        endMonth = (String) valueChangeEvent.getNewValue();
        reatianReport = null;
    }

    public void cityChangeLisener(ValueChangeEvent valueChangeEvent) {
        city = (City) valueChangeEvent.getNewValue();
        grid = null;
        channel = null;
        reatianReport = null;
    }

    public void gridChangeLisener(ValueChangeEvent valueChangeEvent) {
        grid = (Grid) valueChangeEvent.getNewValue();
        channel = null;
        reatianReport = null;
    }


    public void channelChangeLisener(ValueChangeEvent valueChangeEvent) {
        channel = (Channel) valueChangeEvent.getNewValue();
        reatianReport = null;
    }

    public String getMaxMonth(String head) {
        List<String> l = getMonthList(head);
        String max = head + "01";
        for (String s : l) {
            if (max.compareTo(s) < 0)
                max = s;
        }
        return max;
    }

    public List<String> getMonthList(String head) {

        if (beginMonth == null) {
            String sql = "select to_char(add_months(sysdate,-11),'YYYYMM') from dual";
            beginMonth = jt.queryForObject(sql, String.class);
        }
        if (endMonth == null) {
            String sql = "select to_char(add_months(sysdate,-1),'YYYYMM') from dual";
            endMonth = jt.queryForObject(sql, String.class);
        }
        if (this.beginMonth.compareTo(this.endMonth) >= 0) {
            String sql = "select to_char(add_months(sysdate,-1),'YYYYMM') from dual";
            endMonth = jt.queryForObject(sql, String.class);
        }
        String intervalSql =
            "select months_between(to_date('" + endMonth + "','YYYYMM'),to_date('" + beginMonth +
            "','YYYYMM')) xxx from dual ";

        int interval = jt.queryForObject(intervalSql, Integer.class);
        //System.out.println("intvalSql="+intervalSql+" "+ interval);
        List<String> res = new ArrayList<>();
        for (int i = 1; i <= interval + 1; i++) {
            String stemp = "" + i;
            if (stemp.length() == 1)
                stemp = "0" + stemp;
            stemp = head + stemp;
            res.add(stemp);
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        JdbcTemplate jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
        NewRetainReport nrr = new NewRetainReport(jt);
        System.out.println(nrr.getMonthList("u"));
    }


    public void setSingleUnit(boolean singleUnit) {
        this.singleUnit = singleUnit;
    }

    public boolean getSingleUnit() {
        return singleUnit;
    }

    public void singleUnitChangeListener(ValueChangeEvent valueChangeEvent) {
        singleUnit=(Boolean)valueChangeEvent.getNewValue();
        reatianReport=null;
    }
}
