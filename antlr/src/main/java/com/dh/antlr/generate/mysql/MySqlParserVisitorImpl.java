package com.dh.antlr.generate.mysql;

import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class MySqlParserVisitorImpl<T> extends MySqlParserBaseVisitor<T> {

    TokenStreamRewriter rewriter;

    public MySqlParserVisitorImpl(TokenStream tokenStream) {
        this.rewriter = new TokenStreamRewriter(tokenStream);
    }

    @Override
    public T visitTableName(MySqlParser.TableNameContext ctx) {
      //  System.out.println("visitTableName");
       // rewriter.insertAfter(ctx.start,"\thello_world");
        return super.visitTableName(ctx);
    }

    @Override
    public T visitPredicateExpression(MySqlParser.PredicateExpressionContext ctx) {
        rewriter.insertAfter(ctx.stop," and q=5 ");
        return super.visitPredicateExpression(ctx);
    }

    public TokenStreamRewriter getRewriter() {
        return rewriter;
    }
}
