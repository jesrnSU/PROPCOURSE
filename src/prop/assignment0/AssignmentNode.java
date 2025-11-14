package prop.assignment0;

import java.util.ArrayDeque;
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
        args[1] = new ArrayDeque<Lexeme>(); 
        Lexeme assignLexeme;


        result.append(this.id.value() + " " + this.assignSymbol.value() + " ");
        assignLexeme = (Lexeme) this.expressionNode.evaluate(args);
        result.append(assignLexeme.value());

        @SuppressWarnings("unchecked")
        ArrayDeque<Lexeme> stack = (ArrayDeque<Lexeme>) args[1];
        System.out.println("\n\n\n");
        while (!stack.isEmpty()) {
            System.out.println(stack.poll());
        }
        System.out.println("\n\n\n");

        @SuppressWarnings("unchecked")
        HashMap<Object, Lexeme> namespace = (HashMap<Object, Lexeme>) args[0];
        System.out.println("Adding key : " + this.id + " & value : " + assignLexeme);
        namespace.put(this.id.value(), assignLexeme);
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
