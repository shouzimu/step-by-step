package com.dh.antlr.generate.mysql;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class TestMysql {
    public static void main(String[] args) {
        String sql = "select * from a left join b on a.id = b.id where a.id = '5'";

        MySqlLexer lexer = new MySqlLexer(CharStreams.fromString(sql));
        TokenStream tokenStream = new CommonTokenStream(lexer);
        MySqlParser parser = new MySqlParser(tokenStream);
        ParseTree root = parser.selectStatement();

        System.out.println(root.getText());

        MySqlParserVisitorImpl visitor = new MySqlParserVisitorImpl(tokenStream);
        root.accept(visitor);
        System.out.println(visitor.getRewriter().getText());

        ParseTreeWalker work = new ParseTreeWalker();
        MySqlParserListenerImpl listener = new MySqlParserListenerImpl(tokenStream);
        work.walk(listener, root);
//        show((ParserRuleContext) root);
        System.out.println(listener.getRewriter().getText());

    }

    private static void show(ParserRuleContext node) {
        if (null != node.children && !node.children.isEmpty()) {
            for (ParseTree child : node.children) {
                try {
                    show((ParserRuleContext) child);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(node.getText());
    }
}
