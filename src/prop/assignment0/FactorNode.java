package prop.assignment0;

import java.util.HashMap;

public class FactorNode implements INode{
    private Lexeme factor; 
    private Lexeme leftParen;
    private ExpressionNode expressionNode;
    private Lexeme rightParen;

    public FactorNode(Lexeme factor){
        this.factor = factor;
    }

    public FactorNode(Lexeme left, ExpressionNode expressionNode, Lexeme right){
        this.leftParen = left;
        this.expressionNode = expressionNode;
        this.rightParen = right;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if(factor != null && factor.token().equals(Token.IDENT)){ 
            @SuppressWarnings("unchecked")               
            HashMap<Object, Lexeme> namespace = (HashMap<Object, Lexeme>) args[0];
            System.out.println("Returning IDENT : " + factor);
            return namespace.get(factor.value());
        } else if(factor != null && factor.token().equals(Token.INT_LIT)){
            System.out.println("Returning INT : " + factor);
            return factor;
        } else{
            return expressionNode.evaluate(args);
        }
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        String tabString = BlockNode.tabBuilder(tabs);
        tabs += 1;
        builder.append(tabString + this.getClass().getSimpleName() + '\n');
        tabString += '\t';

        if(this.factor != null){
            builder.append(tabString + this.factor + '\n');
        }else{
            builder.append(tabString + this.leftParen + '\n');
            this.expressionNode.buildString(builder, tabs);
            builder.append(tabString + this.rightParen + '\n');
        }
    }
}
