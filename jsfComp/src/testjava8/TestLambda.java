package testjava8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestLambda {
    public TestLambda() {
        super();
    }

    public static void main(String[] args) {
                List<String> s = new ArrayList<>();
                s.add("a");
                s.add("c");
                s.add("b");
                Collections.sort(s, (String a, String b) -> { return a.compareTo(b); });
                System.out.println("s=" + s);


        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted); // 123
        
        
        Arrays.asList( "a", "b", "d" ).forEach( e -> System.out.print( e+" " ) );
       // 请注意参数e的类型是由编译器推测出来的。同时，你也可以通过把参数类型与参数包括在括号中的形式直接给出参数的类型：
        System.out.println();
        Arrays.asList( "a", "b", "c" ).forEach( ( String e ) -> System.out.print( e +" ") );
        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );

    }


    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

}
