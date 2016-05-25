package annotation;


public interface Enum {
    public enum TransCycle {
        month,
        day,
        manual;
    }
    public enum TransType {
        truncate,
        drop,
        append;
    }
    public enum DataSource{
        ora11g,
        dss;
    }
    public enum paramType{
        Date,
        String,
        Number;
    }
    public enum footCalcType{
        averge,
        count,
        sum,
        calc,
        cons;
    } 
    public enum reportStatus{
        request,
        building,
        complete;
    }
    public enum retportCycle{
        day,
        week,
        month;
    }
}
