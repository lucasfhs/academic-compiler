package lexer.value;

public class NumberValue implements Value {
    private final Number value;

    public NumberValue(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitNumber(this);
    }

    @Override
    public boolean eval() {
        return value.doubleValue() != 0.0;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
