package prop.assignment0;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
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

    // @Override
    // public String toString() {
    //     return '\n' + leftCurly.toString() + statementNode.toString() + rightCurly.toString();
    // }

    public static String tabBuilder(int tabs){
        String tabString = "";
        for(int i = 0; i < tabs; i++){
            tabString += '\t';
        }
        return tabString;
    }
}
