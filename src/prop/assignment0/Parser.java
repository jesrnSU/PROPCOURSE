package prop.assignment0;

import java.io.IOException;

public class Parser implements IParser{
    private Tokenizer tokenizer;
    public Parser(){

        tokenizer = new Tokenizer();

    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        // TODO Auto-generated method stub
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
            statementNode = new StatementNode();
        }else{ 
            throw new ParserException("Expected " + Token.LEFT_CURLY + " but got " + tokenizer.current().toString());
        }

        procedureStatement();

        if(tokenizer.current().token().equals(Token.RIGHT_CURLY)){
            return new BlockNode(startSymbol, statementNode, endSymbol);
        }else{
            throw new ParserException("Expected " + Token.RIGHT_CURLY + " but got " + tokenizer.current().toString());
        }
    }

    private StatementNode procedureStatement() throws IOException, TokenizerException, ParserException{
        procedureAssignment();
        return null;
    }

    private AssignmentNode procedureAssignment() throws IOException, TokenizerException, ParserException{
        Lexeme id;
        Lexeme assignSymbol;        
        ExpressionNode expressionNode;
        Lexeme semi;

        tokenizer.moveNext();
        if(!tokenizer.current().token().equals(Token.IDENT)){
            throw new ParserException("Expected " + Token.IDENT + " but got " + tokenizer.current().toString());
        }
        id = tokenizer.current();
        tokenizer.moveNext();

        if(!tokenizer.current().token().equals(Token.ASSIGN_OP)){
            throw new ParserException("Expect =");
        }
        assignSymbol = tokenizer.current();
        tokenizer.moveNext();

        expressionNode = procedureExpression();
        tokenizer.moveNext();

        if(!tokenizer.current().token().equals(Token.SEMICOLON))
            throw new ParserException("Semi");
        semi = tokenizer.current();
        return new AssignmentNode(id, assignSymbol, expressionNode, semi);

    }

    private ExpressionNode procedureExpression() throws IOException, TokenizerException, ParserException {
        tokenizer.moveNext();
        return null;

    }

    private TermNode procedureTerm() throws IOException, TokenizerException, ParserException{
        return null;
    }

    private FactorNode procedureFactor() throws IOException, TokenizerException, ParserException{
        return null;
    }
    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        tokenizer.close();
    }
}
