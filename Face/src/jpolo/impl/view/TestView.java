package jpolo.impl.view;

import entity.JRole;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestView {
    PoloView<JRole> view;

    public TestView() {
        super();
    }

    public Class getGenericType(int index) throws NoSuchFieldException {
        Type genType;
        genType = getClass().getDeclaredField("view").getGenericType();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        System.out.println("params.len=" + params.length);

        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }

        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
    public static void main(String[] args) throws NoSuchFieldException {
        TestView tv=new TestView();
       // System.out.println(tv.getGenericType(0));
   }
    
}
