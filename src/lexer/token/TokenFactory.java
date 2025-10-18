package lexer.token;

import lexer.value.*;

public class TokenFactory {
    private TokenFactory() {
    }

    public static Token createToken(TokenType type, String lexeme) {
        Value value = createValueForToken(type, lexeme);
        return new Token(type, value);
    }

    private static Value createValueForToken(TokenType type, String lexeme) {
        switch (type) {
            case INT_LITERAL:
                return new IntValue(Integer.parseInt(lexeme));
            case REAL_LITERAL:
                return new RealValue(Float.parseFloat(lexeme));
            case STR_LITERAL:
                return new LiteralValue(lexeme);
            case CHAR_LITERAL:
                return new CharValue(lexeme.charAt(0));
            case IDENTIFIER:
                return new IdentifierValue(lexeme);
            default:
                return new NullValue();
        }
    }

}