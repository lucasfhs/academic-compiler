package parser;

import lexer.token.TokenType;
import lexer.Lexer;
import lexer.token.Token;
import lexer.token.TokenType;

public class Parser {

    private Lexer lex;
    private Token current;
    private Token previous;

    public Parser(Lexer lex) throws Exception {
        this.lex = lex;
        this.current = lex.scan();
        this.previous = null;
    }

    private void advance() throws Exception {
        System.out.println("Found " + current);
        previous = current;
        current = lex.scan();
    }

    private void eat(TokenType type) throws Exception {
        if (type == current.getType()) {
            advance();
        } else {
            // Implementar recuperação de erro...
            System.out.println("Expected (..., " + type + ", ..., ...), found " + current);
            // reportError();
        }
    }

    // Sinceramente não vejo uso para esses dois metodos... mas tinha no do andrei
    // vamos deixar...

    private boolean check(TokenType... types) {
        for (TokenType type : types) {
            if (current.getType() == type)
                return true;
        }

        return false;
    }

    private boolean match(TokenType... types) throws Exception {
        if (check(types)) {
            advance();
            return true;
        } else {
            return false;
        }
    }

    // Regra inicial da gramatica...

    public void process() throws Exception {
        // Alguma regra...
        eat(TokenType.EOF);
    }

}