package prop.assignment0;

import java.util.ArrayDeque;
import java.util.Deque;
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

    @SuppressWarnings("unchecked")
    @Override
    public Object evaluate(Object[] args) throws Exception {
        Deque<Lexeme> lAssociativity = (ArrayDeque<Lexeme>) args[1];
        if(factor != null && factor.token().equals(Token.IDENT)){ 
            HashMap<Object, Lexeme> namespace = (HashMap<Object, Lexeme>) args[0];
            Lexeme value = namespace.get(factor.value());
            System.out.println("ADD Last " + value);
            lAssociativity.addLast(value);
        } else if(factor != null && factor.token().equals(Token.INT_LIT)){
            System.out.println("ADD Last " + factor);
            lAssociativity.addLast(factor); 
        } else{
            System.out.println("Going into (expr)");
            expressionNode.evaluate(args);
            System.out.println("Returning from (expr) : ");
        }
        return null;
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
