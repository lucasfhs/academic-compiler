package lexer.value;

public interface ValueVisitor<T> {
    T visitNumber(NumberValue value);

    T visitString(StringValue value);

    T visitBoolean(BooleanValue value);

    /* Provavelmente essa de Id vai rodar... */
    T visitIdentifier(IdentifierValue value);

    T visitNull(NullValue value);
}