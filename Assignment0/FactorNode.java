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
        if(factor != null && factor.token().equals(Token.IDENT)){ 
            HashMap<Object, Lexeme> namespace = (HashMap<Object, Lexeme>) args[0];
            Lexeme value = namespace.get(factor.value());
            return value;
        } else if(factor != null && factor.token().equals(Token.INT_LIT)){
            return factor; 
        } else{
            Deque<Lexeme> valueStack = new ArrayDeque<>();
            Deque<Lexeme> operatorStack = new ArrayDeque<>();
            Object[] innerArgs = {args[0], valueStack, operatorStack};

            expressionNode.evaluate(innerArgs);
            return calculateInnerExpression(valueStack, operatorStack);
        }
    }

    private Lexeme calculateInnerExpression(Deque<Lexeme> innerValues, Deque<Lexeme> innerOperators){
        double result = (double) innerValues.pop().value();
        double nextVal; 
        while(!innerValues.isEmpty()){
            char op = (char) innerOperators.pop().value();
            nextVal = (double) innerValues.pop().value();
            result = op == '+' ? (result + nextVal) : (result - nextVal); 
        }
        return new Lexeme(result, Token.INT_LIT);
    }

    @Override public void buildString(StringBuilder builder, int tabs) {
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
