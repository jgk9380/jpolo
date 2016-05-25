package agent.report;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import java.util.List;
import java.util.Map;

public class ReportUtils {
    
    public double  divNumber(Number b1, Number b2) {
        double l1;
        double l2;
        if (b1 == null)
            l1 = 0d;
        else
            l1 = b1.doubleValue();
        if (b2 == null)
            l2 = 0d;
        else
            l2 = b2.doubleValue();
        if (l2 == 0)
            return 0d;
        //System.out.println("b1=" + b1 + "b2=" + b2 + " " + l1/l2);
        DecimalFormat df = new DecimalFormat("######0.00");
        String res = df.format(l1 * 100 / l2);
        return Double.parseDouble(res);
    }
    public double  sumListMap(List<Map<String,Object>> lm,String fieldName) {
       double dres=0;
        DecimalFormat df = new DecimalFormat("######0.00");
        for(Map<String,Object> m:lm){
            Double d=0d;
            if(m.get(fieldName)==null)
                d=0d;
            else 
                d = ((Number) m.get(fieldName)).doubleValue();                
            dres=dres+d;
        }
        String res = df.format(dres);
        return Double.parseDouble(res);
    }
}
