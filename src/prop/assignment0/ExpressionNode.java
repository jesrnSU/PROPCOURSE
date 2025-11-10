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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
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

    // @Override
    // public String toString(){
    //     if(operator != null || this.expressionNode != null){
    //         return '\n' + this.termNode.toString() + this.operator.toString() + this.expressionNode.toString();
    //     }else{
    //         return '\n' + this.termNode.toString();
    //     }
    // }
}
