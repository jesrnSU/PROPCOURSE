package prop.assignment0;

import java.util.ArrayDeque;

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
        @SuppressWarnings("unchecked")
        ArrayDeque<Lexeme> lrHandler = (ArrayDeque<Lexeme>)args[1];
        termNode.evaluate(args);
        Lexeme termResult = calculateProductAndQuotient(lrHandler);

        if(this.operator != null && this.expressionNode != null){
            this.expressionNode.evaluate(args);
            lrHandler.push(this.operator);
            lrHandler.push(termResult);
            System.out.println("PUSHING EXPR 2 : " + operator + " Factor: " + termResult);
        }else{
            System.out.println("Pushing EXPR : " + termResult);
            lrHandler.push(termResult);
        } 
        return null;
    }

    private Lexeme calculateProductAndQuotient(ArrayDeque<Lexeme> queue){
        double result = (double) queue.poll().value();
        while(!queue.isEmpty()){
            if(queue.peek().token().equals(Token.MULT_OP)){
                queue.poll();
                result *= (double) queue.poll().value(); 
            }else{ 
                queue.poll();
                result /= (double) queue.poll().value();
            }
        }
        System.out.println("RESULT FROM PRODUCT : " + result);
        return new Lexeme(result, Token.IDENT);
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
