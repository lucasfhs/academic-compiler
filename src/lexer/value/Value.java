package lexer.value;

public interface Value {
    <T> T accept(ValueVisitor<T> visitor);

    boolean eval();

    String toString();
}

