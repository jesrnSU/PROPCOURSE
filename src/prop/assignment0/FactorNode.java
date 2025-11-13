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
            HashMap<Object, Double> namespace = (HashMap<Object, Double>) args[0];
            return namespace.get(factor.value());
        } else if(factor != null && factor.token().equals(Token.INT_LIT)){
            return factor.value();
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

    // @Override
    // public String toString() {
    //     if(this.factor == null){
    //         return '\n' + leftParen.toString() + expressionNode.toString() + rightParen.toString(); 
    //     }else{
    //         return '\n' + factor.toString();
    //     }
    // }

}
