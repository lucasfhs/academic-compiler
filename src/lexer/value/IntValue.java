package lexer.value;

public class IntValue implements Value {
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitInt(this);
    }

    @Override
    public boolean eval() {
        return value != 0;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
