package lexer.token;

import lexer.value.Value;

public class Token {
    private TokenType type;
    private Value value;
    private String lexeme;

    public Token(TokenType type, Value value, String lexeme) {
        this.type = type;
        this.value = value;
        this.lexeme = lexeme;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
