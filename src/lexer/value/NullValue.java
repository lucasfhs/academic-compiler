package lexer.value;

public class NullValue implements Value {
    public NullValue() {
    }

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitNull(this);
    }

    @Override
    public boolean eval() {
        return false;
    }

    @Override
    public String toString() {
        return "null";
    }
}