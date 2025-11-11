package prop.assignment0;

import java.io.IOException;

public class Tokenizer implements ITokenizer {
    private Scanner scanner;
    private Lexeme currentLexeme;
    private StringBuilder sb;

    public Tokenizer() {
        scanner = new Scanner();
        sb = new StringBuilder();
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner.open(fileName);
    }

    @Override
    public Lexeme current() {
        return currentLexeme;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {
        char currentChar = scanner.current();
        currentChar = ignoreWhitespace(currentChar);

        if(currentChar == Scanner.EOF){
            currentLexeme = new Lexeme(currentChar, Token.EOF);
            return;
        } else if (currentChar >= '1' && currentChar <= '9') {
            buildNumberHelper(currentChar);
            return;
        } else if (currentChar >= 'a' && currentChar <= 'z') {
            buildIdHelper(currentChar);
            return;
        }else { 
            evaluateSymbolHelper(currentChar);
        }
    }

    private char ignoreWhitespace(char c) throws IOException{
        while(c == Scanner.NULL | c == '\n' | c == ' ' | c == '\r' | c == '\t'){
            scanner.moveNext();
            c = scanner.current();
        }
        return c;
    }

    private void buildNumberHelper(char c) throws IOException {
        while (c >= '0' && c <= '9') {
            sb.append(c);
            scanner.moveNext();
            c = scanner.current();
        }
        currentLexeme = new Lexeme(Double.parseDouble(sb.toString()), Token.INT_LIT);
        sb.setLength(0);
    }

    private void buildIdHelper(char c) throws IOException {
        while (c >= 'a' && c <= 'z') {
            sb.append(c);
            scanner.moveNext();
            c = scanner.current();
        }
        currentLexeme = new Lexeme(sb.toString(), Token.IDENT);
        sb.setLength(0);
    }

    private void evaluateSymbolHelper(char c) throws IOException, TokenizerException{
         Token tempToken = null;
         switch (scanner.current()) {
            case '{': tempToken = Token.LEFT_CURLY; break;
            case '}': tempToken = Token.RIGHT_CURLY; break;
            case '(': tempToken = Token.LEFT_PAREN; break;
            case ')': tempToken = Token.RIGHT_PAREN; break;
            case ';': tempToken = Token.SEMICOLON; break;
            case '=': tempToken = Token.ASSIGN_OP; break;
            case '+': tempToken = Token.ADD_OP; break;
            case '-': tempToken = Token.SUB_OP; break;
            case '*': tempToken = Token.MULT_OP; break;
            case '/': tempToken = Token.DIV_OP; break;
            
            // Special cases
            case (char)0 : tempToken = Token.NULL; break;
            default: throw new TokenizerException("Character could not be handled correctly");
         }
         if (tempToken != null) {
            currentLexeme = new Lexeme(c, tempToken);
        }
        scanner.moveNext();
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        scanner.close();
    }

}
