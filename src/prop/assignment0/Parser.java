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
        procedureStatement();
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
        TermNode termNode;
        Lexeme operator = null;
        ExpressionNode expressionNode;

        termNode = procedureTerm();
        tokenizer.moveNext();

        operator = tokenizer.current();
        if(operator.token().equals(Token.ADD_OP) | operator.token().equals(Token.SUB_OP)){
            expressionNode = procedureExpression();
        }else{
            return new ExpressionNode(termNode);
        }

        return new ExpressionNode(termNode, operator, expressionNode);
    }

    private TermNode procedureTerm() throws IOException, TokenizerException, ParserException{
        
        FactorNode factorNode;
        Lexeme operator;
        TermNode termNode;

        factorNode = procedureFactor();
        tokenizer.moveNext();

        operator = tokenizer.current();
        if(operator.token().equals(Token.MULT_OP) | operator.token().equals(Token.DIV_OP)){
            termNode = procedureTerm();
        }else{
            return new TermNode();
        }
    
        return new TermNode();
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
