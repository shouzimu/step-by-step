package com.dh.antlr.sqlbuff;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import com.dh.antlr.sqlbuff.misc.CodeBuffTokenStream;
import com.dh.antlr.sqlbuff.misc.LangDescriptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InputDocument {
	public LangDescriptor language;
	public String content;
	public List<String> lines; // used for debugging; a cache of lines in this.content
	public int index;
	public ParserRuleContext tree;
	public Map<Token, TerminalNode> tokenToNodeMap = null;

	public Parser parser;
	public CodeBuffTokenStream tokens;

	public InputDocument(String content, LangDescriptor language) {
		this.content = content;
		this.language = language;
	}

	public String getLine(int line) {
		if ( lines==null ) {
			lines = Arrays.asList(content.split("\n"));
		}
		if ( line>0 ) {
			return lines.get(line-1);
		}
		return null;
	}

	public void setTree(ParserRuleContext root) {
		this.tree = root;
		if ( root!=null ) {
			tokenToNodeMap = com.dh.antlr.sqlbuff.Trainer.indexTree(root);
		}
	}

}

