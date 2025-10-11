package lexer.value;

public class LiteralValue implements Value {
    private final String value;

    public LiteralValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitLiteral(this);
    }

    @Override
    public boolean eval() {
        return value != null && !value.isEmpty();
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}