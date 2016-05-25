package assist.utils;


import java.text.SimpleDateFormat;

import java.util.Date;

import assist.database.iface.ISqlHelper;

public class DateUtils {
    static ISqlHelper iSql = null;

    public static void setISql(ISqlHelper iSql) {
        DateUtils.iSql = iSql;
    }

    public static Number getYear(Date date) throws Exception {
        String datas = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date);
        String sql = "select to_number(to_char(to_date('" + datas + "','YYYYMMDD HH24:MI:SS'),'YYYY')) from dual";
        Number ol = (Number) iSql.querySingle(sql);
        return ol;
    }

    public static Date parse(String date) throws Exception {
        String sql = "select to_date('" + date + "','YYYYMMDD') from dual";
        Date ol = (Date) iSql.querySingle(sql);
        return ol;
    }
    /*计算某年1月1日的偏移*/
    private static Number getWeekAtYearPos(Date date) throws Exception {
        /*计算1月1日的偏移*/
        String posYear = new SimpleDateFormat("yyyy").format(date);
        String posSql = "select to_number(to_char(to_date('" + posYear + "0101','YYYYMMDD')" + ",'d'))-1 from dual";
        Number pos = (Number) iSql.querySingle(posSql);
        System.out.println("pos=" + pos);
        /*计算1月1日的偏移*/
        return pos;
    }
    
    /*计算某年某周的开始日期*/
    public static Date getDateAtWeek(Date date,Number week) throws Exception {         
        Number pos = getWeekAtYearPos(date);
        String year = new SimpleDateFormat("yyyy").format(date);
        String dateSql = "select to_date('" + year + "0101','YYYYMMDD')+("+week+"-1)*7-"+pos+" from dual";
        Date dateRes = (Date) iSql.querySingle(dateSql);
        System.out.println("pos=" + pos);       
        return dateRes;
    }

    public static Number getWeekAtYear(Date date) throws Exception {      
        Number pos = getWeekAtYearPos(date);
        /*计算具体日期是某年多少周时，向右偏移pos*/
        String datas = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date); //2015年+4
        String sql = "select to_number(to_char(to_date('" + datas + "','YYYYMMDD HH24:MI:SS')+" + pos + ",'ww')) from dual";
        Number ol = (Number) iSql.querySingle(sql);
        return ol;
    }

    public static Date getLastWeekDate(Date date) throws Exception {
        String datas = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date);
        String sql = "select to_date('" + datas + "','YYYYMMDD HH24:MI:SS')-7 from dual";
        Date ol = (Date) iSql.querySingle(sql);
        return ol;
    }

    public static Date getNextWeekDate(Date date) throws Exception {
        String datas = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(date);
        String sql = "select to_date('" + datas + "','YYYYMMDD HH24:MI:SS')+7 from dual";
        Date ol = (Date) iSql.querySingle(sql);
        return ol;
    }

    public static Date getSysDate() throws Exception {
        String sql = "select sysdate from dual";
        Date ol = (Date) iSql.querySingle(sql);
        return ol;
    }

    public static void main(String[] args) throws Exception {


    }
    
    
     enum WeekDay {
        Sunday(0), //星期日
        Monday(1), // 星期一
        Tuesday(2), //星期二
        Wednesday(3), //星期三
        Thursday(4), //星期四
        Friday(5), //星期五
        Saturday(6); // 星期六

        int day;

        private WeekDay(int i) {
            this.day = i;
        }

        public int getWeekDay() {
            return day;
        }

        public static WeekDay parse(String s) {
            WeekDay res;
            switch (s) {
            case "sun":
                res = assist.utils.DateUtils.WeekDay.Sunday;
                break;
            case "mon":
                res = assist.utils.DateUtils.WeekDay.Monday;
                break;
            case "tue":
                res = assist.utils.DateUtils.WeekDay.Tuesday;
                break;
            case "wed":
                res = assist.utils.DateUtils.WeekDay.Wednesday;
                break;
            case "thu":
                res = assist.utils.DateUtils.WeekDay.Thursday;
                break;
            case "fri":
                res = assist.utils.DateUtils.WeekDay.Friday;
                break;
            case "sat":
                res = assist.utils.DateUtils.WeekDay.Saturday;
                break;
            default:
                return null;
            }
            return null;
        }

    }

}
