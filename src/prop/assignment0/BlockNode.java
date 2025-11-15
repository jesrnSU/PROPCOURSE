package prop.assignment0;

import java.util.HashMap;

public class BlockNode implements INode{
    private Lexeme leftCurly;
    private StatementsNode statementNode;
    private Lexeme rightCurly;

    public BlockNode(Lexeme lCurly, StatementsNode sNode, Lexeme rCurly){
        leftCurly = lCurly;
        statementNode = sNode;
        rightCurly = rCurly;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        HashMap<Object, Lexeme> namespace = new HashMap<>();
        args = new Object[3];
        args[0] = namespace;
        return statementNode.evaluate(args);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        String tab = tabBuilder(tabs);

        builder.append(tab + this.getClass().getSimpleName() + '\n');
        builder.append(tab + leftCurly + '\n');
        if(statementNode != null)
            statementNode.buildString(builder, tabs + 1);
        builder.append(tab + rightCurly + '\n');
    }

    public static String tabBuilder(int tabs){
        String tabString = "";
        for(int i = 0; i < tabs; i++){
            tabString += '\t';
        }
        return tabString;
    }
}
