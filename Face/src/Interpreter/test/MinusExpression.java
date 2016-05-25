package Interpreter.test;


public class MinusExpression implements IExpression {
    public void interpret(Context context) {
        System.out.println("PlusExpression --");
        String input = context.getInput();
        int parsedResult = Integer.parseInt(input);
        parsedResult --;
        context.setInput(String.valueOf(parsedResult));
        context.setOutput(parsedResult);
    }
}


