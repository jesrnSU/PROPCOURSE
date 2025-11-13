package prop.assignment0;

public class ExpressionNode implements INode{
    private TermNode termNode;
    private Lexeme operator;
    private ExpressionNode expressionNode;

    public ExpressionNode(TermNode termNode){
        this.termNode = termNode;
        this.operator = null;
        this.expressionNode = null;
    }

    public ExpressionNode(TermNode termNode, Lexeme operator, ExpressionNode expressionNode){
        this.termNode = termNode;
        this.operator = operator;
        this.expressionNode = expressionNode;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        Object term = termNode.evaluate(args);
        if(this.operator != null && this.expressionNode != null){
            if(this.operator.token().equals(Token.ADD_OP)){
                return (double) term + (double) this.expressionNode.evaluate(args);
            }else{
                return (double) term - (double) this.expressionNode.evaluate(args);
            } 
        }else{
            return term;
        }
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        String stringTabs = BlockNode.tabBuilder(tabs);
        tabs += 1;

        builder.append(stringTabs + this.getClass().getSimpleName() + '\n');
        stringTabs += '\t';

        termNode.buildString(builder, tabs);
        if(this.expressionNode != null){
            builder.append(stringTabs + operator + '\n');
            this.expressionNode.buildString(builder, tabs);
        }
    }
}
