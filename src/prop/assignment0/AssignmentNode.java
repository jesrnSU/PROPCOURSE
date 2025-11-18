package prop.assignment0;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Locale;

public class AssignmentNode implements INode{
    private Lexeme id;
    private Lexeme assignSymbol;
    private ExpressionNode expressionNode;
    private Lexeme semiSymbol;

    public AssignmentNode(Lexeme id, Lexeme assign, ExpressionNode eNode, Lexeme semi){
        this.id = id;
        this.assignSymbol = assign;
        this.expressionNode = eNode;
        this.semiSymbol = semi;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object evaluate(Object[] args) throws Exception {
        HashMap<Object, Lexeme> namespace = (HashMap<Object, Lexeme>)args[0];
        StringBuilder resultString = new StringBuilder(); 
        Deque<Lexeme> valuesStack = new ArrayDeque<>();
        Deque<Lexeme> operatorStack = new ArrayDeque<>();
        args[1] = valuesStack;
        args[2] = operatorStack;

        resultString.append(this.id.value() + " " + this.assignSymbol.value());
        this.expressionNode.evaluate(args);

        double resultValue = (double) valuesStack.pop().value();
        while (!valuesStack.isEmpty()) {
            double nextVal = (double) valuesStack.pop().value();
            resultValue = operatorStack.pop().token().equals(Token.ADD_OP) ? (resultValue + nextVal) : (resultValue - nextVal); 
        }
        namespace.put(id.value(), new Lexeme(resultValue, Token.INT_LIT));
        return resultString.append(String.format(Locale.US, " %.1f", resultValue));
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        String tab = BlockNode.tabBuilder(tabs);
        builder.append(tab + this.getClass().getSimpleName() + '\n');
        tab += '\t';
        builder.append(tab + id.toString() + '\n'); 
        
        builder.append(tab + assignSymbol.toString() + '\n');
        expressionNode.buildString(builder, tabs + 1);
        builder.append(tab + semiSymbol.toString());
    }
}
