package prop.assignment0;

import java.util.ArrayDeque;
import java.util.Deque;

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
        Deque<Lexeme> lrHandler = (ArrayDeque<Lexeme>)args[1];
        this.factorNode.evaluate(args);

        if(this.operator != null && this.termNode != null){
            lrHandler.addLast(operator);
            this.termNode.evaluate(args); 
        } else{
            System.out.println("No more TERM or DIV/MUL OP");
            double result = (double) (lrHandler.removeFirst()).value();
            System.out.println("RESULT NOW : " + result);
            if(lrHandler.peekFirst() != null){
                while(lrHandler.peekFirst().token().equals(Token.MULT_OP) || lrHandler.peekFirst().token().equals(Token.DIV_OP)){
                    if(lrHandler.removeFirst().token().equals(Token.MULT_OP)){
                        System.out.println("MULT " + result + " * " + lrHandler.getFirst().value());
                        result *= (double) lrHandler.removeFirst().value();
                    }else{
                        System.out.println("DIV " + result + " / " + lrHandler.getFirst().value());
                        result /= (double) lrHandler.removeFirst().value();
                    }
                }
            }
            System.out.println("RESULT after Term : " + result);
            lrHandler.addLast(new Lexeme(result, Token.INT_LIT));
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
