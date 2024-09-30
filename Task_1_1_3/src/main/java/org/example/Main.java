package org.example;

import java.util.HashMap;
import java.util.Map;

abstract class Expression {
    public abstract void print(); // Печать выражения
    public abstract Expression derivative(String variable); // Символьное дифференцирование
    public abstract int eval(String variables); // Вычисление значения выражения

    protected int getValueFromMap(Map<String, Integer> variables, String varName) {
        return variables.getOrDefault(varName, 0);
    }
}

class Number extends Expression {
    private int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0); // Производная константы
    }

    @Override
    public int eval(String variables) {
        return value;// Константа не зависит от переменных
    }
}

class Variable extends Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0); // Производная переменной
    }

    @Override
    public int eval(String variables) {
        Map<String, Integer> varMap = parseVariables(variables);
        return getValueFromMap(varMap, name); // Получаем значение переменной
    }

    private Map<String, Integer> parseVariables(String variables) {
        Map<String, Integer> varMap = new HashMap<>();
        String[] assignments = variables.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                varMap.put(varName, value);
            }
        }
        return varMap;
    }
}

abstract class BinaryOperation extends Expression {
    protected Expression left;
    protected Expression right;

    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print(getOperator());
        right.print();
        System.out.print(")");
    }

    protected abstract String getOperator();
}

class Add extends BinaryOperation {
    public Add(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "+";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) + right.eval(variables);
    }
}

class Sub extends BinaryOperation {
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "-";
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) - right.eval(variables);
    }
}

class Mul extends BinaryOperation {
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "*";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable)));
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) * right.eval(variables);
    }
}

class Div extends BinaryOperation {
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "/";
    }

    @Override
    public Expression derivative(String variable) {
        return new Div(new Sub(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))),
                new Mul(right, right));
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) / right.eval(variables);
    }
}

public class Main {
    public static void main(String[] args) {
        // Пример использования
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x"))); // (3+(2*x))

        // Печать
        e.print(); // Вывод: (3+(2*x))
        System.out.println();

        // Дифференцирование
        Expression de = e.derivative("x");
        de.print(); // Вывод: (0+((0*x)+(2*1)))
        System.out.println();

        // Вычисление значения
        String variables = "x = 10; y = 13";
        int result = e.eval(variables);
        System.out.println(result); // Вывод: 23
    }
}