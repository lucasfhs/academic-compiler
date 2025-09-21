package lexer.value;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitString(this);
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