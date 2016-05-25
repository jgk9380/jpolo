package assist.database.datatransfer;

import assist.database.DbUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.sql.Clob;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import assist.utils.DataUtils;

import assist.database.iface.IDataHelper;


/**
 *  * ����ִ�����ݸ�������
 * * java  -cp ./batchData.jar;./classes12.jar   utils.BatchData
 */

public class BatchData {
    private BatchData(int hours) {
        super();
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("ִ������ at" +new SimpleDateFormat("yy-MM-dd HH:mm").format(new Date()));
                try {
                    refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, time.getTime(), 1, TimeUnit.DAYS); //�����һ��

    }

    protected void refresh() throws Exception {
        System.out.println("*****��ʼ����***** at" + new Date());
        IDataHelper dataHelper = DbUtils.getDataHelper("dss");
        List<Object[]> ol = dataHelper.getSqlHelper().queryListArray("select id,object_name,object_desc,object_ddl from regionj.regionj_object");
        for (Object[] oa : ol) {
            BigDecimal id = (BigDecimal) oa[0];
            String name = (String) oa[1];
            String desc = (String) oa[2];
            Clob ddlClob = (Clob) oa[3];
            String ddlSql = ddlClob.getSubString(1, (int) ddlClob.length());
            String info = " id=" + id + "  name=" + name;
            info = info + DataUtils.repeatBlank(40 - info.length()) + "  desc=" + desc;
            System.out.print(info);
            //System.out.println(ddlSql);
            String[] sqls = ddlSql.split(";");
            System.out.println("  ��" + sqls.length + "����¼");
            System.out.print("        �����:");
            for (int i = 0; i < sqls.length; i++) {
                System.out.print(i + 1 + ",");
                if (!sqls[i].startsWith("--"))
                    dataHelper.getSqlHelper().execUpdate(sqls[i]);
            }
            System.out.println();
        }
        System.out.println("*****�������*****at" + new Date());
        return;
    }


    public static void main(String[] args) throws Exception {
        int hour = 16;
        if (args.length < 1) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str = null;
            System.out.print("����ִ��ʱ�䣨HH��:");
            str = br.readLine();
            hour = Integer.parseInt(str);
        } else
            hour = Integer.parseInt(args[0]);
        BatchData bd = new BatchData(hour);
        System.out.println("����ִ����ÿ��"+hour+"��");
        //bd.refresh();
    }
}
