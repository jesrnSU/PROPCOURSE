package prop.assignment0;

public class TermNode implements INode{
    private FactorNode factorNode;
    private Lexeme operator;
    private TermNode termNode;

    public TermNode(FactorNode factorNode){
        this.factorNode = factorNode;
    }

    public TermNode(FactorNode factorNode, Lexeme operator, TermNode termNode){
        this.factorNode = factorNode;
        this.operator = operator;
        this.termNode = termNode;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        Object factor = factorNode.evaluate(args);
        if(operator != null || termNode != null){
            if(this.operator.token().equals(Token.MULT_OP)){
                return (double) factor * (double) termNode.evaluate(args);
            }
            else {
                return (double) factor / (double) termNode.evaluate(args);
            }
        }else{
            return factor;
        }
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        String stringTabs = BlockNode.tabBuilder(tabs);
        tabs += 1;

        builder.append(stringTabs + this.getClass().getSimpleName() + '\n');
        stringTabs += '\t';

        this.factorNode.buildString(builder, tabs);
        if(this.operator != null && this.termNode != null){
            builder.append(stringTabs + this.operator + '\n');
            this.termNode.buildString(builder, tabs);
        } 
    }
}
