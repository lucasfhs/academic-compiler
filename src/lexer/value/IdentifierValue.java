package lexer.value;

public class IdentifierValue implements Value {
    private final String name;
    
    public IdentifierValue(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public <T> T accept(ValueVisitor<T> visitor) {
        return visitor.visitIdentifier(this);
    }
    
    @Override
    public boolean eval() {
        return true; // Identificadores sempre avaliam como true
    }
    
    @Override
    public String toString() {
        return name;
    }
}