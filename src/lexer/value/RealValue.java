package lexer.value;

public class RealValue implements Value {
    private final float value;

    public RealValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitReal(this);
    }

    @Override
    public boolean eval() {
        return value != 0.0;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }
}
