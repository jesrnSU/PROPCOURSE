package prop.assignment0;

public class AssignmentNode implements INode{
    private Lexeme id;
    private Lexeme assignSymbol;
    private ExpressionNode expressionNode;
    private Lexeme semiSymbol;

    public AssignmentNode(Lexeme id, Lexeme assign, ExpressionNode eNode, Lexeme semi){
        this.id = id;
        this.assignSymbol = assign;
        this.expressionNode = eNode;
        this.semiSymbol = semi;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildString'");
    }

}
