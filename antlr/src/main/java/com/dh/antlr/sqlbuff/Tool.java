package com.dh.antlr.sqlbuff;

import com.dh.antlr.generate.mysql.MySqlLexer;
import com.dh.antlr.generate.mysql.MySqlParser;
import com.dh.antlr.sqlbuff.misc.CodeBuffTokenStream;
import com.dh.antlr.sqlbuff.misc.LangDescriptor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static com.dh.antlr.sqlbuff.Trainer.FEATURES_HPOS;
import static com.dh.antlr.sqlbuff.Trainer.FEATURES_INJECT_WS;
import static org.antlr.v4.runtime.atn.PredictionMode.SLL;

/**
 * The main CodeBuff tool used to format files. Examples:
 * <p>
 * $ java -jar target/codebuff-1.4.19.jar  \
 * -g com.dh.antlr.sqlbuff.ANTLRv4 -rule grammarSpec -corpus corpus/antlr4/training \
 * -files g4 -indent 4 -comment LINE_COMMENT T.g4
 * <p>
 * $ java -jar codebuff-1.4.19 \
 * -g com.dh.antlr.sqlbuff.Java -rule compilationUnit \
 * -corpus corpus/java/training/stringtemplate4  -files java \
 * -comment LINE_COMMENT T.java
 * <p>
 * You have to have some libs in your CLASSPATH. See pom.xml, but it's
 * ANTLR 4, Apache commons-lang3, Google guava, and StringTemplate 4.
 * <p>
 * The grammar must be run through ANTLR and be compiled (and in the CLASSPATH).
 * For Java8.g4, use "-g Java8", not the filename. For separated
 * grammar files, like ANTLRv4Parser.g4 and ANTLRv4Lexer.g4, use "-g ANTLRv4".
 * If the grammar is in a package, use fully-qualified like
 * "-g com.dh.antlr.sqlbuff.ANTLRv4"
 * <p>
 * Output goes to stdout if no -o option used.
 */
public class Tool {
    public static boolean showFileNames = false;
    public static boolean showTokens = false;


    public static String version;

    public static CodeBuffTokenStream tokenize(String doc, Class<? extends Lexer> lexerClass)
            throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(doc);
        Lexer lexer = getLexer(lexerClass, input);

        CodeBuffTokenStream tokens = new CodeBuffTokenStream(lexer);
        tokens.fill();
        return tokens;
    }

    public static Parser getParser(Class<? extends Parser> parserClass, CommonTokenStream tokens) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends Parser> parserCtor =
                parserClass.getConstructor(TokenStream.class);
        return parserCtor.newInstance(tokens);
    }

    public static Lexer getLexer(Class<? extends Lexer> lexerClass, ANTLRInputStream input) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends Lexer> lexerCtor =
                lexerClass.getConstructor(CharStream.class);
        return lexerCtor.newInstance(input);
    }

    /**
     * Get all file contents into input doc list
     */
    public static List<InputDocument> load(List<String> fileNames, LangDescriptor language)
            throws Exception {
        List<InputDocument> documents = new ArrayList<>();
        for (String fileName : fileNames) {
            documents.add(parse(fileName, language));
        }
        if (documents.size() > 0) {
            documents.get(0).parser.getInterpreter().clearDFA(); // free up memory
        }

        return documents;
    }

    public static String load(String fileName, int tabSize)
            throws Exception {
        Path path = FileSystems.getDefault().getPath(fileName);
        byte[] filearray = Files.readAllBytes(path);
        String content = new String(filearray);
        String notabs = expandTabs(content, tabSize);
        return notabs;
    }


    public static InputDocument parse(String inputSql, LangDescriptor language)
            throws Exception {
        ANTLRInputStream input = new ANTLRInputStream(inputSql);
        Lexer lexer = getLexer(language.lexerClass, input);

        InputDocument doc = new InputDocument(inputSql, language);

        doc.tokens = new CodeBuffTokenStream(lexer);

        doc.parser = getParser(language.parserClass, doc.tokens);
        doc.parser.setBuildParseTree(true);

        // two-stage parsing. Try with SLL first
        doc.parser.getInterpreter().setPredictionMode(SLL);
        doc.parser.setErrorHandler(new BailErrorStrategy());
        doc.parser.removeErrorListeners();

        Method startRule = language.parserClass.getMethod(language.startRuleName);
        try {
            doc.setTree((ParserRuleContext) startRule.invoke(doc.parser, (Object[]) null));
        } catch (InvocationTargetException ex) {
            if (ex.getCause() instanceof ParseCancellationException) {
                doc.parser.reset();
                doc.tokens.reset(); // rewind input stream
                // back to standard listeners/handlers
                doc.parser.addErrorListener(
                        new ANTLRErrorListener() {
                            @Override
                            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                                System.err.println(recognizer.getInputStream().getSourceName() + " line " + line + ":" + charPositionInLine + " " + msg);
                            }

                            @Override
                            public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
                            }

                            @Override
                            public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
                            }

                            @Override
                            public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
                            }
                        });
                doc.parser.setErrorHandler(new DefaultErrorStrategy());
                doc.parser.getInterpreter().setPredictionMode(PredictionMode.LL);
                doc.setTree((ParserRuleContext) startRule.invoke(doc.parser, (Object[]) null));
                if (doc.parser.getNumberOfSyntaxErrors() > 0) {
                    doc.setTree(null);
                }
            }
        }

        return doc;
    }

    public static List<String> getFilenames(File f, String inputFilePattern) throws Exception {
        List<String> files = new ArrayList<>();
        getFilenames_(f, inputFilePattern, files);
        return files;
    }

    public static void getFilenames_(File f, String inputFilePattern, List<String> files) {
        // If this is a directory, walk each file/dir in that directory
        if (f.isDirectory()) {
            String flist[] = f.list();
            for (String aFlist : flist) {
                getFilenames_(new File(f, aFlist), inputFilePattern, files);
            }
        }

        // otherwise, if this is an input file, load it!
        else if (inputFilePattern == null || f.getName().matches(inputFilePattern)) {
            files.add(f.getAbsolutePath());
        }
    }

    public static String join(int[] array, String separator) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i < array.length - 1) {
                builder.append(separator);
            }
        }

        return builder.toString();
    }

    public static String join(String[] array, String separator) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i < array.length - 1) {
                builder.append(separator);
            }
        }

        return builder.toString();
    }

    public static List<CommonToken> copy(CommonTokenStream tokens) {
        List<CommonToken> copy = new ArrayList<>();
        tokens.fill();
        for (Token t : tokens.getTokens()) {
            copy.add(new CommonToken(t));
        }
        return copy;
    }

    public static int L0_Distance(boolean[] categorical, int[] A, int[] B) {
        int count = 0; // count how many mismatched categories there are
        for (int i = 0; i < A.length; i++) {
            if (categorical[i]) {
                if (A[i] != B[i]) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * A distance of 0 should count much more than non-0. Also, penalize
     * mismatches closer to current token than those farther away.
     */
    public static double weightedL0_Distance(FeatureMetaData[] featureTypes, int[] A, int[] B) {
        double count = 0; // count how many mismatched categories there are
        for (int i = 0; i < A.length; i++) {
            com.dh.antlr.sqlbuff.FeatureType type = featureTypes[i].type;
            if (type == com.dh.antlr.sqlbuff.FeatureType.TOKEN ||
                    type == com.dh.antlr.sqlbuff.FeatureType.RULE ||
                    type == com.dh.antlr.sqlbuff.FeatureType.INT ||
                    type == com.dh.antlr.sqlbuff.FeatureType.BOOL) {
                if (A[i] != B[i]) {
                    count += featureTypes[i].mismatchCost;
                }
            }
        }
        return count;
    }

    public static double sigmoid(int x, float center) {
        return 1.0 / (1.0 + Math.exp(-0.9 * (x - center)));
    }

    public static int max(List<Integer> Y) {
        int max = 0;
        for (int y : Y) max = Math.max(max, y);
        return max;
    }

    public static int sum(int[] a) {
        int s = 0;
        for (int x : a) s += x;
        return s;
    }

    public static String spaces(int n) {
        return sequence(n, " ");
//		StringBuilder buf = new StringBuilder();
//		for (int sp=1; sp<=n; sp++) buf.append(" ");
//		return buf.toString();
    }

    public static String newlines(int n) {
        return sequence(n, "\n");
//		StringBuilder buf = new StringBuilder();
//		for (int sp=1; sp<=n; sp++) buf.append("\n");
//		return buf.toString();
    }

    public static String sequence(int n, String s) {
        StringBuilder buf = new StringBuilder();
        for (int sp = 1; sp <= n; sp++) buf.append(s);
        return buf.toString();
    }

    public static int count(String s, char x) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == x) {
                n++;
            }
        }
        return n;
    }

    public static String expandTabs(String s, int tabSize) {
        if (s == null) return null;
        StringBuilder buf = new StringBuilder();
        int col = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\n':
                    col = 0;
                    buf.append(c);
                    break;
                case '\t':
                    int n = tabSize - col % tabSize;
                    col += n;
                    buf.append(spaces(n));
                    break;
                default:
                    col++;
                    buf.append(c);
                    break;
            }
        }
        return buf.toString();
    }

    public static String dumpWhiteSpace(String s) {
        String[] whiteSpaces = new String[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\n':
                    whiteSpaces[i] = "\\n";
                    break;
                case '\t':
                    whiteSpaces[i] = "\\t";
                    break;
                case '\r':
                    whiteSpaces[i] = "\\r";
                    break;
                case '\u000C':
                    whiteSpaces[i] = "\\u000C";
                    break;
                case ' ':
                    whiteSpaces[i] = "ws";
                    break;
                default:
                    whiteSpaces[i] = String.valueOf(c);
                    break;
            }
        }
        return join(whiteSpaces, " | ");
    }

    public static void main(String[] args) throws Exception{
        Class lexerClass = MySqlLexer.class;
        Class parserClass = MySqlParser.class;
        Lexer lexer = getLexer(lexerClass, null);

        int singleLineCommentType = -1;


        LangDescriptor language = new LangDescriptor("fileRegex",
                lexerClass, parserClass, "sqlStatements",
                4, singleLineCommentType);

        String sources = "select\n" +
                "id,\n" +
                "rule_id, group_name,\n" +
                "data_source, data_base, table_name, " +
                "expression,\n" +
                "assigned, assigned_date from core_query_rule_details a left join core_query_rule_details b on  a.id = b.id where 1>0";


        System.out.println("----------------------\n\n\n" + sources + "\n\n\n-----------------------");


        InputDocument testDoc = parse(sources, language);
        List<InputDocument> others = new ArrayList<>();
        Corpus corpus = new Corpus(others, language);
        corpus.train();

        Formatter formatter = new Formatter(corpus, language.indentSize, Formatter.DEFAULT_K,
                FEATURES_INJECT_WS, FEATURES_HPOS);
        String output = formatter.format(testDoc, false);

        System.out.print(output);

    }
}
