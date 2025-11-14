package prop.assignment0;

import java.util.ArrayDeque;

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
        @SuppressWarnings("unchecked")
        ArrayDeque<Lexeme> lrHandler = (ArrayDeque<Lexeme>)args[1];
        Lexeme factor = (Lexeme) factorNode.evaluate(args);

        if(operator != null && termNode != null){
            this.termNode.evaluate(args);
            lrHandler.push(operator);
            lrHandler.push(factor);
            System.out.println("PUSHING TERM 2 : " + operator + " Factor: " + factor);
        }else{
            System.out.println("Pushing TERM : " + factor);
            lrHandler.push(factor);
        }
        return null;
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
