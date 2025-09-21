package lexer.value;

public class BooleanValue implements Value {
    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitBoolean(this);
    }

    @Override
    public boolean eval() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}