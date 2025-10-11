package lexer.value;

public interface ValueVisitor<T> {
    T visitInt(IntValue value);
    
    T visitString(StringValue value);
    
    T visitReal(RealValue value);

    T visitLiteral(LiteralValue value);

    T visitBoolean(BooleanValue value);

    /* Provavelmente essa de Id vai rodar... */
    T visitIdentifier(IdentifierValue value);
    
    T visitChar(CharValue value);

    T visitNull(NullValue value);
}