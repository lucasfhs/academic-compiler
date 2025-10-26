package lexer.token;

import lexer.value.Value;

public class Token {
    private TokenType type;
    private Value value;

    public Token(TokenType type, Value value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    // Print token information
    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }

}
