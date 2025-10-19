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
            // A string is defined as "character*", so it’s the content inside ""
                return new LiteralValue(lexeme.substring(1, lexeme.length() - 1));
            case CHAR_LITERAL:
            // A char is defined as 'character', so it’s the content inside ''
                return new CharValue(lexeme.charAt(1));
            case IDENTIFIER:
                return new IdentifierValue(lexeme);
            default:
                return new NullValue();
        }
    }

}