package prop.assignment0;

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
        Double assignmentValue;
        result.append(this.id.value() + " " + this.assignSymbol.value() + " ");
        assignmentValue = (double) this.expressionNode.evaluate(args);
        result.append(assignmentValue);

        @SuppressWarnings("unchecked")
        HashMap<Object, Double> namespace = (HashMap<Object, Double>) args[0];
        namespace.put(this.id.value(), assignmentValue);
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
