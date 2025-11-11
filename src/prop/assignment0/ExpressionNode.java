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
        StringBuilder result = new StringBuilder(); 
        result.append(this.termNode.evaluate(args));
        if(this.operator != null && this.expressionNode != null){
            result.append(this.operator.value());
            result.append(this.expressionNode.evaluate(args));
        }
        return result.toString();
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
