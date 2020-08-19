package com.dh.antlr.generate.cal;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class TestCalculator {
    public static void main(String[] args) {
        String expression = "a = 12\n" +
                "b = a * 2\n" +
                "a + b\n";
        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalculatorParser parser = new CalculatorParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree root = parser.prog();
        CalculatorVisitor<Integer> visitor = new CalculatorVisitorImpl();
        root.accept(visitor);
        System.out.println(root.getText());
    }
}
