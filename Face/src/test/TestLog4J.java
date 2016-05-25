package test;


import org.apache.log4j.Logger;

/**如果采用log4j输出日志，要对log4j加载配置文件的过程有所了解。
log4j启动时，默认会寻找source folder下的log4j.xml配置文件，
若没有，会寻找log4j.properties文件。然后加载配置。
配置文件放置位置正确，不用在程序中手动加载log4j配置文件。
如果将配置文件放到了config文件夹下，在build Path中设置下就好了。
等级可分为OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL，OFF是关闭，
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
