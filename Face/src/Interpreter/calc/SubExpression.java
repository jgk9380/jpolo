package Interpreter.calc;

import java.util.HashMap;

public class SubExpression extends SymbolExpression {

    public SubExpression(Expression _left, Expression _right) {
        super(_left, _right);
    }

    //�����������ʽ���

    public int interpreter(HashMap<String, Integer> var) {
        return super.left.interpreter(var) - super.right.interpreter(var);
    }

}

//�����嵥27-5 ����������
