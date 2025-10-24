package lexer.value;

public class CharValue implements Value {
    private final char value;

    public CharValue(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitChar(this);
    }

    @Override
    public boolean eval() {
        return value != '\0';
    }

    @Override
    public String toString() {
        return "'" + String.valueOf(value) + "'";
    }
}