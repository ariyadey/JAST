package jast.utilities;


import jast.antlr.JavaLexer;
import jast.antlr.JavaParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class AntlrUtil {

    public static String output = "";
    private static List<String> LineNum = new ArrayList<>();
    private static List<String> Type = new ArrayList<>();
    private static List<String> Content = new ArrayList<>();

    public static ParserRuleContext getRuleContext(String inputStr) {
        ANTLRInputStream input = new ANTLRInputStream(inputStr);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        var parserRuleContext = parser.compilationUnit();
//        parserRuleContext.
        return parserRuleContext;
    }

    public static void generateAST(RuleContext ruleContext, boolean verbose, int indentation) {
        boolean toBeIgnored = !verbose && ruleContext.getChildCount() == 1 && ruleContext.getChild(0) instanceof ParserRuleContext;

        if (!toBeIgnored) {
            String ruleName = JavaParser.ruleNames[ruleContext.getRuleIndex()];
            LineNum.add(Integer.toString(indentation));
            Type.add(ruleName);
            Content.add(ruleContext.getText());
        }

        for (int i = 0; i < ruleContext.getChildCount(); i++) {
            ParseTree element = ruleContext.getChild(i);
            if (element instanceof RuleContext) {
                generateAST((RuleContext) element, verbose, indentation + (toBeIgnored ? 0 : 1));
            }
        }
    }

    public static void writeDOT() {
        var astGraph = GraphUtil.getGraphInstance("AST");
        writeLabel(astGraph);
        int pos = 0;
        for (int i = 1; i < LineNum.size(); i++) {
            pos = getPos(Integer.parseInt(LineNum.get(i)) - 1, i);
            astGraph.addEdge(String.valueOf(i), (Integer.parseInt(LineNum.get(i)) - 1) + "" + Integer.toString(pos), LineNum.get(i) + i);
            output = output.concat((Integer.parseInt(LineNum.get(i)) - 1) + Integer.toString(pos) + "->" + LineNum.get(i) + i);
            output = output.concat("\n");
//            System.out.println((Integer.parseInt(LineNum.get(i))-1)+Integer.toString(pos)+"->"+LineNum.get(i)+i);
        }
        astGraph.display();
    }

    private static void writeLabel(Graph astGraph) {
        for (int i = 0; i < LineNum.size(); i++) {
            astGraph.addNode(LineNum.get(i) + i).setAttribute("ui.label", Type.get(i)+"\\n "+Content.get(i)+" \"]");
            output = output.concat(LineNum.get(i)+i+"[label=\""+Type.get(i)+"\\n "+Content.get(i)+" \"]");
            output = output.concat("\n");
//            System.out.println(LineNum.get(i)+i+"[label=\""+Type.get(i)+"\\n "+Content.get(i)+" \"]");
        }
    }

    private static int getPos(int n, int limit) {
        int pos = 0;
        for (int i = 0; i < limit; i++) {
            if (Integer.parseInt(LineNum.get(i)) == n) {
                pos = i;
            }
        }
        return pos;
    }
}
