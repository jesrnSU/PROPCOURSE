package prop.assignment0;

public class BlockNode implements INode{
    Lexeme leftCurly;
    StatementNode statementNode;
    Lexeme rightCurly;

    public BlockNode(Lexeme lCurly, StatementNode sNode, Lexeme rCurly){
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
        builder.append("");
    }

}
