package agent.report;

import assist.database.DbUtils;

import entity.agent.Channel;
import entity.agent.City;

import entity.agent.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import utils.system;

public class PhotoRetainReport {
    JdbcTemplate jt;
    String photoQueryMonth ;   
    City city;
    entity.agent.Grid grid;
    
    String sqlBody=" round(sum(a.photo_u)/10000,2) photo_u£¬round(sum(a.photo_f)/10000 ,2) photo_f£¬\n" +
            "     round(sum(u01)/10000,2) u01,round(sum(u02)/10000,2) u02,round(sum(u03)/10000,2) u03,\n" +
            "     round(sum(u04)/10000,2) u04,round(sum(u05)/10000,2) u05,round(sum(u06)/10000,2) u06,\n" +
            "     round(sum(u07)/10000,2) u07,round(sum(u08)/10000,2) u08,round(sum(u09)/10000,2) u09,\n" +
            "     round(sum(u10)/10000,2) u10,round(sum(u11)/10000,2) u11,round(sum(u12)/10000,2) u12,\n" +  
            "     round(sum(f01)/10000 ,2) f01,round(sum(f02)/10000 ,2) f02,round(sum(f03)/10000 ,2) f03, \n" + 
            "     round(sum(f04)/10000 ,2) f04,round(sum(f05)/10000 ,2) f05,round(sum(f06)/10000 ,2) f06, \n" + 
            "     round(sum(f07)/10000 ,2) f07,round(sum(f08)/10000 ,2) f08,round(sum(f09)/10000 ,2) f09, \n" + 
            "     round(sum(f10)/10000 ,2) f10,round(sum(f11)/10000 ,2) f11,round(sum(f12)/10000 ,2) f12 "+
            "     from j_photo_retain_mv a,j_code_channel b,j_code_grid c ,j_code_city d\n" +
            "     where a.develop_channel=b.id(+) and b.grid_id=c.id(+) and c.city_id=d.id(+) and ";
   
  


    public PhotoRetainReport(JdbcTemplate jt) {
        super();
        this.jt = jt;
        String sql="select to_char(add_months(sysdate,-1),'YYYYMM') from dual";
        photoQueryMonth=jt.queryForObject(sql, String.class);
    }

    public List<String> getMonthList(String head) {
        //System.out.println("month=" + photoQueryMonth);
        if (photoQueryMonth == null)
            return Collections.emptyList();
        List<String> res = new ArrayList<>();
        String tm = photoQueryMonth.substring(4, 6);
        int tmi = Integer.parseInt(tm);
        //System.out.println("tmi=" + tmi + " tm=" + tm);
        for (int i = 1; i <= tmi; i++) {
            String stemp = "" + i;
            if (stemp.length() == 1)
                stemp = "0" + stemp;
            stemp = head + stemp;
            res.add(stemp);
        }
        return res;
    }

    public String getMaxMonth(String head) {
        return head + photoQueryMonth.substring(4, 6);
    }

    List<Map<String, Object>> userReatianReport;

    public List<Map<String, Object>> getUserReatianReport() {
        if (userReatianReport != null)
            return userReatianReport;

        if (grid != null) {
            userReatianReport = this.getGridUserReatianReport(grid);
            return userReatianReport;
        }
        if (city != null) {
            userReatianReport = this.getCityUserReatianReport(city);
            return userReatianReport;
        }

        userReatianReport = this.getAllUserReatianReport();
        return userReatianReport;
    }

    public List<Map<String, Object>> getAllUserReatianReport() {
        String allUserReainSql =
            "     select nvl(d.name,'±¾²¿') cname ,"+sqlBody+" acct_year=:year \n" +
            "     group by d.name order by d.name ";
        System.out.println("allUserReainSql=" + allUserReainSql);
        String year =
            jt.queryForObject("select to_char(to_date('" + photoQueryMonth + "','YYYYMM')-365,'YYYY') from dual ", String.class);
        System.out.println("year=" + year);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("year", year);
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        System.out.println("sql="+allUserReainSql);
        List<Map<String, Object>> res = njt.queryForList(allUserReainSql, p2);
        return res;
    }

    public List<Map<String, Object>> getCityUserReatianReport(City city) {
        String cityUserReainSql =
            "    select nvl(c.name,'±¾²¿') cname ,"+sqlBody+"  acct_year=:year and d.id=:cityID\n" +
            "     group by  nvl(c.name,'±¾²¿') order by  nvl(c.name,'±¾²¿') ";
        System.out.println("cityUserReainSql=" + cityUserReainSql);
        String year =
            jt.queryForObject("select to_char(to_date('" + photoQueryMonth + "','YYYYMM')-365,'YYYY') from dual ", String.class);
        System.out.println("year=" + year);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("year", year);
        p2.put("cityID", city.getId());
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(cityUserReainSql, p2);
        return res;
    }

    public List<Map<String, Object>> getGridUserReatianReport(Grid grid) {
        String gridUserReainSql =
            "   select nvl(b.name,'±¾²¿') cname ,"+sqlBody+" acct_year=:year and c.id=:gridID\n" +
            "     group by  nvl(b.name,'±¾²¿') order by  round(sum(a.photo_u) ,2) desc\n ";
        System.out.println("gridUserReainSql=" + gridUserReainSql);
        String year =
            jt.queryForObject("select to_char(to_date('" + photoQueryMonth + "','YYYYMM')-365,'YYYY') from dual ", String.class);
        System.out.println("year=" + year);
        Map<String, Object> p2 = new HashMap<>();
        p2.put("year", year);
        p2.put("gridID", grid.getId());
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(jt);
        List<Map<String, Object>> res = njt.queryForList(gridUserReainSql, p2);
        return res;
    }

    public static void main(String[] args) {
        try {
            JdbcTemplate jt = new JdbcTemplate(DbUtils.getDataSource("ora11g"));
            PhotoRetainReport pr = new PhotoRetainReport(jt);
            System.out.println("ss=" + pr.getMonthList("u"));
            List<Map<String, Object>> lm = pr.getUserReatianReport();
            System.out.println(lm);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPhotoQueryMonth(String month) {

        this.photoQueryMonth = month;
    }

    public String getPhotoQueryMonth() {
        return photoQueryMonth;
    }

    public City getCity() {
        return city;
    }

    public Grid getGrid() {
        return grid;
    }

  

    public void setCity(City city) {
        this.city = city;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

  

    public void cityChangeLisener(ValueChangeEvent vc) {
        city = (City) vc.getNewValue();
        grid=null;
        this.userReatianReport = null;

    }

    public void photoQueryMonthChangeLisener(ValueChangeEvent vc) {
        String oldMnth = photoQueryMonth;
        photoQueryMonth = (String) vc.getNewValue();
        if (!oldMnth.substring(0, 4).equals(photoQueryMonth.substring(0, 4)))
            this.userReatianReport = null;
    }

    public void gridChangeLisener(ValueChangeEvent vc) {
        grid = (Grid) vc.getNewValue();
        this.userReatianReport = null;
    }
}
