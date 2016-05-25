package assist.utils;


public class DebugUtils1 {
    
    static LogLevel logLevel = LogLevel.error;
    public static void setLogLevel(LogLevel logLevel) {
        DebugUtils1.logLevel = logLevel;
    }


    public static void debug(String info) {
        log(info, LogLevel.testInfo);
        Exception e = new Exception("--------------debug info:-----------------");
        e.printStackTrace();
    }

    public static void error(String info, Exception e) {
        log(info, LogLevel.error);
        if (e == null)
            e = new Exception("--------------error info:-----------------");
        e.printStackTrace();
    }

    public static void waring(String info) {
        log(info, LogLevel.wraning);
    }

    public static void info(String info) {
        log(info, LogLevel.info);
    }

    public static void test(String info) {
        log(info, LogLevel.testInfo);
    }

    protected static void log(String info, LogLevel level) {
        if (level.getLevel() <= logLevel.getLevel())
            System.out.println(level.getLevelName() + ":" + info);
    }

    public static String getLengthString(String s, int len) {
        String res = s;
        for (int i = 0; i < (len - s.length()); i++)
            res = res + " ";
        return res;
    }

    enum LogLevel {
       error("错误", 1),
       wraning("警告", 2),
       log("日志", 3),
       info("信息", 4),
       testInfo("测试", 5);

       // 成员变量
       private String LevelName;
       private int level;

       private LogLevel(String LevelName, int level) {
           this.LevelName = LevelName;
           this.level = level;
       }

       private LogLevel(int level) {
           this.level = level;
           for (LogLevel c : assist.utils.DebugUtils1.LogLevel.values()) {
               if (c.level == level) {
                   LevelName = c.LevelName;
               }
           }
       }

       public String getLevelName() {
           return LevelName;
       }

       public int getLevel() {
           return level;
       }
    }
}
