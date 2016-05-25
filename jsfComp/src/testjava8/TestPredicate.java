package testjava8;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class TestPredicate {
    public TestPredicate() {
        super();
    }
    public static void main(String[] args) {
       Predicate<String> predicate = (s) -> s.length() > 0;
       predicate.test("foo");              // true
       predicate.negate().test("foo");     // false
       Predicate<Boolean> nonNull = Objects::nonNull;
       Predicate<Boolean> isNull = Objects::isNull;
       Predicate<String> isEmpty = String::isEmpty;
       Predicate<String> isNotEmpty = isEmpty.negate();
       
       tstParall();
   }
    public static  void tstParall(){
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        // cuanÐÐÅÅÐò£º
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
        
        
       // ²¢ÐÐÅÅÐò£º
        

         t0 = System.nanoTime();
         count = values.parallelStream().sorted().count();
        System.out.println(count);
         t1 = System.nanoTime();
         millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }
}
