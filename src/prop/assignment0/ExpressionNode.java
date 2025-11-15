package prop.assignment0;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import org.w3c.dom.ls.LSSerializer;

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
        Deque<Lexeme> lrHandler = (ArrayDeque<Lexeme>) args[1];
        this.termNode.evaluate(args);

        if(this.operator != null && this.expressionNode != null){
            lrHandler.addLast(this.operator);
            this.expressionNode.evaluate(args);
        }else{
            System.out.println("No more add or sub");
            double result = (double) lrHandler.removeFirst().value();

            while(lrHandler.peekFirst().token().equals(Token.ADD_OP) || lrHandler.peekFirst().token().equals(Token.SUB_OP)){
                if(lrHandler.removeFirst().token().equals(Token.ADD_OP)){
                    System.out.println("ADD " + result + " + " + lrHandler.getFirst().value());
                    result += (double) lrHandler.removeFirst().value();
                }else{
                    System.out.println("SUB " + result + " - " + lrHandler.getFirst().value());
                    result -= (double) lrHandler.removeFirst().value();
                }
            }
            lrHandler.addLast(new Lexeme(result, Token.INT_LIT));
        }
        
        System.out.println("Exiting expression with current");
        Iterator<Lexeme> it = lrHandler.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
           System.out.println();
        return null; 
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
