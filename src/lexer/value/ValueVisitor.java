package lexer.value;

public interface ValueVisitor<T> {

    // IntegerConst
    T visitInt(IntValue value);

    // RealConst
    T visitReal(RealValue value);

    // CharConst
    T visitChar(CharValue value);

    // LiteralConst(String)
    T visitLiteral(LiteralValue value);

    // Bool
    T visitBoolean(BooleanValue value);

    // Null
    T visitNull(NullValue value);

    // Id
    T visitIdentifier(IdentifierValue value);
}