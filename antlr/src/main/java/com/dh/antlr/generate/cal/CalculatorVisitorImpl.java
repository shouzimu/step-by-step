package com.dh.antlr.generate.cal;

import java.util.HashMap;
import java.util.Map;

public class CalculatorVisitorImpl extends CalculatorBaseVisitor<Integer> {

    private Map<String, Integer> variable;

    public CalculatorVisitorImpl() {
        variable = new HashMap<>();
    }

    @Override
    public Integer visitProg(CalculatorParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }

    @Override
    public Integer visitPrint(CalculatorParser.PrintContext ctx) {
        Integer result = ctx.expr().accept(this);
        System.out.println(result);
        return null;

    }

    @Override
    public Integer visitAssign(CalculatorParser.AssignContext ctx) {
        String name = ctx.ID().getText();
        Integer value = ctx.expr().accept(this);
        variable.put(name, value);
        return null;
    }

    @Override
    public Integer visitBlank(CalculatorParser.BlankContext ctx) {
        return super.visitBlank(ctx);
    }

    @Override
    public Integer visitMulDiv(CalculatorParser.MulDivContext ctx) {
        Integer param1 = ctx.expr(0).accept(this);
        Integer param2 = ctx.expr(1).accept(this);
        if (ctx.op.getType() == CalculatorParser.MUL) {
            return param1 * param2;
        } else {
            return param1 / param2;
        }
    }

    @Override
    public Integer visitAddSub(CalculatorParser.AddSubContext ctx) {
        Integer param1 = ctx.expr(0).accept(this);
        Integer param2 = ctx.expr(1).accept(this);
        if (ctx.op.getType() == CalculatorParser.ADD) {
            return param1 + param2;
        } else {
            return param1 - param2;
        }
    }

    @Override
    public Integer visitParenthese(CalculatorParser.ParentheseContext ctx) {
        return super.visitParenthese(ctx);
    }

    @Override
    public Integer visitId(CalculatorParser.IdContext ctx) {
        return variable.get(ctx.getText());
    }

    @Override
    public Integer visitInt(CalculatorParser.IntContext ctx) {
        return Integer.parseInt(ctx.getText());
    }
}
