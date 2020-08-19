package com.dh.antlr.generate.mysql;

import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class MySqlParserListenerImpl extends MySqlParserBaseListener {

    TokenStreamRewriter rewriter;

    public MySqlParserListenerImpl(TokenStream tokenStream) {
        this.rewriter = new TokenStreamRewriter(tokenStream);
    }

    @Override
    public void enterTableName(MySqlParser.TableNameContext ctx) {
   //     System.out.println("visitTableName");
      //  rewriter.insertAfter(ctx.start, "\thello_world");
        super.enterTableName(ctx);
    }



    public TokenStreamRewriter getRewriter() {
        return rewriter;
    }

}
