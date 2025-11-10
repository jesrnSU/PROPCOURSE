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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
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
