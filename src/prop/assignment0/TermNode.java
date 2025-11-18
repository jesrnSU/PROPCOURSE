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

    @SuppressWarnings("unchecked")
    @Override
    public Object evaluate(Object[] args) throws Exception {
        Deque<Lexeme> valueStack = (ArrayDeque<Lexeme>)args[1];
        Deque<Lexeme> operatorStack = (ArrayDeque<Lexeme>)args[2];
        Lexeme factor = (Lexeme) this.factorNode.evaluate(args);
        valueStack.push(factor);

        if(this.operator == null || this.termNode == null){
            factor = precedenceResult(valueStack, operatorStack);
        }else{
            operatorStack.push(this.operator);
            return this.termNode.evaluate(args); 
        }
        return factor;
    }

    private Lexeme precedenceResult(Deque<Lexeme> valueStack, Deque<Lexeme> operatorStack) {
        double result = (double) valueStack.removeLast().value();
        double nextVal;
        while (!valueStack.isEmpty()) {
            char op = (char) operatorStack.pop().value();
            nextVal = (double) valueStack.removeLast().value();
            result = op == '*' ? (result * nextVal) : (result / nextVal);
        }
        return new Lexeme(result, Token.INT_LIT);
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
