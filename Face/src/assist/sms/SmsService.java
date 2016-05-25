package assist.sms;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import assist.database.DbUtils;
import assist.database.iface.IDataHelper;

/**
 *  * 发送短信
 * java  -cp ./batchData.jar;./commons-dbcp2-2.1.1.jar;./commons-logging-1.1.3.jar;./commons-pool2-2.4.2.jar;./dms.jar;./ojdbc6dms.jar;./ojdl.jar;./orai18n.jar   utils.sms.SmsService
 */

public class SmsService {
    int i = 0;

    public SmsService() {
        super();
        Runnable runnable = new Runnable() {
            public void run() {
                i++;
                if (i % 120 == 0)
                    System.out.println("执行任务 at " + new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date()));
                try {
                    refreshSMS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS); //天更新一次
    }

    private void refreshSMS() {
        //System.out.println("f");
        try {
            IDataHelper dataHelper = DbUtils.getDataHelper(null);
            List<Object[]> oal = dataHelper.getSqlHelper().queryListArray("select id,tele,title,content from j_sms");
            //System.out.println("l.size=" + oal.size());
            for (Object[] oa : oal) {
                BigDecimal id = (BigDecimal) oa[0];
                String tele = (String) oa[1];
                String title = (String) oa[2];
                String content = (String) oa[3];
                smsThread st = new smsThread(tele, content, title);
                st.start();

                //MMSToolFor802.sendSMS(tele, content, title);
                String insertsql =
                    "insert into j_sms_history(id,tele,title,content) values(id_gen_seq.nextval,'" + tele + "','" + title + "','" + content + "')";
                //System.out.println("insertsql=" + insertsql);
                dataHelper.getSqlHelper().execUpdate(insertsql);
                String deleteSql = "delete from j_sms where id=" + id;
                dataHelper.getSqlHelper().execUpdate(deleteSql);
                //System.out.println("deleteSql=" + deleteSql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public static void sendSms(String tele, String title, String smsContent) {
        String sql = "insert into j_sms(id,tele,title,content) values(id_gen_seq.nextval,'" + tele + "','" + title + "','" + smsContent + "')";
        IDataHelper dataHelper;
        try {
            dataHelper = DbUtils.getDataHelper("ora11g");
            dataHelper.getSqlHelper().execUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        SmsService ss = new SmsService();
        // SmsService.sendSms("15651554341", "testTitle", "TestContent");
        System.out.println("Ok");
    }

    class smsThread extends Thread implements Runnable {
        String tele;
        String title;
        String content;

        smsThread(String tele, String title, String content) {
            this.title = title;
            this.tele = tele;
            this.content = content;
        }

        public void run() {
            try {
                MMSToolFor802.sendSMS(tele, title ,content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
