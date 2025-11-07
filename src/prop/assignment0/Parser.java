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
        while(tokenizer.current().token().compareTo(Token.EOF) < 0){
            System.out.println("NOW : " + tokenizer.current());
            

            tokenizer.moveNext();
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        tokenizer.close();
    }

}
