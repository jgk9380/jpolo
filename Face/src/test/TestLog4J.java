package test;


import org.apache.log4j.Logger;

/**�������log4j�����־��Ҫ��log4j���������ļ��Ĺ��������˽⡣
log4j����ʱ��Ĭ�ϻ�Ѱ��source folder�µ�log4j.xml�����ļ���
��û�У���Ѱ��log4j.properties�ļ���Ȼ��������á�
�����ļ�����λ����ȷ�������ڳ������ֶ�����log4j�����ļ���
����������ļ��ŵ���config�ļ����£���build Path�������¾ͺ��ˡ�
�ȼ��ɷ�ΪOFF��FATAL��ERROR��WARN��INFO��DEBUG��ALL��OFF�ǹرգ�
-->*/

public class TestLog4J {
    public static void main(String[] args) throws Exception {
        // Logger logger = Logger.getLogger(TestLog4J.class);
        Logger.getLogger(TestLog4J.class).error("error");      
        Logger.getLogger(TestLog4J.class).info("info");
        Logger.getLogger(TestLog4J.class).warn("warn");
        Logger.getLogger(TestLog4J.class).debug("debug");
        //throw new Exception("sss");
    }

}
