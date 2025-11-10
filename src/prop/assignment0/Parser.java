package prop.assignment0;

import java.io.IOException;

public class Parser implements IParser{
    private Tokenizer tokenizer;
    public Parser(){
        tokenizer = new Tokenizer();
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        tokenizer.open(fileName);
    }

    @Override
    public INode parse() throws IOException, TokenizerException, ParserException {
        tokenizer.moveNext();
        INode startingNode = procedureBlock();
        return startingNode;
    }

    private BlockNode procedureBlock() throws IOException, TokenizerException, ParserException{
        Lexeme startSymbol = null;
        StatementNode statementNode = null;
        Lexeme endSymbol = null;

        if(tokenizer.current().token().equals(Token.LEFT_CURLY)){
            startSymbol = tokenizer.current();
            tokenizer.moveNext();
        }else{ 
            throw new ParserException("Expected " + Token.LEFT_CURLY + " but got " + tokenizer.current().toString());
        }

        statementNode = procedureStatement();

        if(tokenizer.current().token().equals(Token.RIGHT_CURLY)){
            endSymbol = tokenizer.current();
            tokenizer.moveNext();
            return new BlockNode(startSymbol, statementNode, endSymbol);
        }else{
            throw new ParserException("Expected right curly");
        }
    }

    private StatementNode procedureStatement() throws IOException, TokenizerException, ParserException{
        AssignmentNode assignmentNode;

        if(tokenizer.current().token().equals(Token.RIGHT_CURLY)){
            return new StatementNode();
        }else{
            assignmentNode = procedureAssignment();
            return new StatementNode(assignmentNode);
        }
    }

    private AssignmentNode procedureAssignment() throws IOException, TokenizerException, ParserException{
        Lexeme id;
        Lexeme assignSymbol;        
        ExpressionNode expressionNode;
        Lexeme semi;

        if(tokenizer.current().token().equals(Token.IDENT)){
            id = tokenizer.current();
            tokenizer.moveNext();
        }else{
            throw new ParserException("Expected " + Token.IDENT + " but got " + tokenizer.current().toString());
        }

        if(tokenizer.current().token().equals(Token.ASSIGN_OP)){
            assignSymbol = tokenizer.current();
            tokenizer.moveNext();

            expressionNode = procedureExpression();
            tokenizer.moveNext();
        }else{
            throw new ParserException("Expect =");
        }

        if(tokenizer.current().token().equals(Token.SEMICOLON)){
            semi = tokenizer.current();
            return new AssignmentNode(id, assignSymbol, expressionNode, semi);
        }else{
            System.out.println(tokenizer.current().toString());
            throw new ParserException("Semi");
        }
    }

    private ExpressionNode procedureExpression() throws IOException, TokenizerException, ParserException {
        TermNode termNode;
        Lexeme operator = null;
        ExpressionNode expressionNode;

        termNode = procedureTerm();
        tokenizer.moveNext();

        operator = tokenizer.current();
        if(operator.token().equals(Token.ADD_OP) | operator.token().equals(Token.SUB_OP)){
            tokenizer.moveNext();
            expressionNode = procedureExpression();
            return new ExpressionNode(termNode, operator, expressionNode);
        }else{
            return new ExpressionNode(termNode);
        }
    }

    private TermNode procedureTerm() throws IOException, TokenizerException, ParserException{
        FactorNode factorNode;
        Lexeme operator;
        TermNode termNode;

        factorNode = procedureFactor();
        tokenizer.moveNext();

        operator = tokenizer.current();
        if(operator.token().equals(Token.MULT_OP) | operator.token().equals(Token.DIV_OP)){
            tokenizer.moveNext();
            termNode = procedureTerm();
            return new TermNode(factorNode, operator, termNode);
        }else{
            return new TermNode(factorNode);
        }
    }

    private FactorNode procedureFactor() throws IOException, TokenizerException, ParserException{
        Lexeme factor = tokenizer.current();
        ExpressionNode expressionNode; 

        System.out.println(factor.toString());
        if(factor.token().equals(Token.INT_LIT) | factor.token().equals(Token.IDENT)){
            return new FactorNode(factor);
        }else if(factor.token().equals(Token.LEFT_PAREN)){
            expressionNode = procedureExpression();    
        }else {
            throw new ParserException("Factor expectd");
        }

        tokenizer.moveNext();
        if(tokenizer.current().token().equals(Token.RIGHT_PAREN)){
            return new FactorNode(factor, expressionNode, tokenizer.current());
        }else{
            throw new ParserException("Expected right paren");
        }
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        tokenizer.close();
    }
}
