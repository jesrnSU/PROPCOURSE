package prop.assignment0;

public class StatementsNode implements INode{
    private AssignmentNode assignmentNode;
    private StatementsNode statementNode;

    public StatementsNode(){

    }

    public StatementsNode(AssignmentNode assignmentNode, StatementsNode statementNode){
        this.assignmentNode = assignmentNode;
        this.statementNode = statementNode;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        StringBuilder result = new StringBuilder();

        if(this.assignmentNode != null && this.statementNode != null){
            result.append(this.assignmentNode.evaluate(args)); 
            result.append("\n");
            result.append(this.statementNode.evaluate(args));
        }
        return result.toString();
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        String tab = BlockNode.tabBuilder(tabs);
        tabs += 1;

        builder.append(tab + this.getClass().getSimpleName() + '\n');
        if(assignmentNode != null && statementNode != null){
            assignmentNode.buildString(builder, tabs);
            builder.append('\n');
            statementNode.buildString(builder, tabs);
        }
    }

    // @Override
    // public String toString(){
    //     if(assignmentNode == null || statementNode == null){
    //         return '\n' + this.getClass().getSimpleName(); 
    //     }else{
    //         return '\n' + assignmentNode.toString() + statementNode.toString();
    //     }
    // }
}
