package testjava8;

interface Class1 {
    double calculate(int a);
    
    default double sqrt(int a) {
        return Math.sqrt(a);
    }
    default double sqrt1(int a) {
        return Math.sqrt(a);
    }
}