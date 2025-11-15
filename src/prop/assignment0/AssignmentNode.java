package prop.assignment0;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

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

    @Override
    public Object evaluate(Object[] args) throws Exception {
        StringBuilder result = new StringBuilder(); 
        Deque<Lexeme> leftRecursionHandler = new ArrayDeque<>();
        Lexeme expressionResult;
        args[1] = leftRecursionHandler;

        result.append(this.id.value() + " " + this.assignSymbol.value() + " ");
        this.expressionNode.evaluate(args);
        expressionResult = leftRecursionHandler.removeFirst();

        result.append(expressionResult.value());

        while (!leftRecursionHandler.isEmpty()) {
           System.out.println(leftRecursionHandler.removeFirst()); 
        }

        return result.toString();
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
