package prop.assignment0;

import java.util.ArrayDeque;
import java.util.Deque;

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

    @SuppressWarnings("unchecked")
    @Override
    public Object evaluate(Object[] args) throws Exception {
        Deque<Lexeme> valueStack = (ArrayDeque<Lexeme>) args[1];
        Deque<Lexeme> operatorStack = (ArrayDeque<Lexeme>) args[2];
        Lexeme term = (Lexeme) this.termNode.evaluate(args);

        if(this.operator != null && this.expressionNode != null){
            this.expressionNode.evaluate(args);
            operatorStack.push(this.operator);
        } 
        valueStack.push(term);
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
