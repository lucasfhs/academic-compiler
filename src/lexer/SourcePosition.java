package lexer;

public class SourcePosition {
    private final String fileName;
    private final int startLine;
    private final int startColumn;
    private final int endLine;
    private final int endColumn;
    private final int absoluteStart;
    private final int absoluteEnd;

    public SourcePosition(String fileName, int startLine, int startColumn,
            int endLine, int endColumn, int absoluteStart, int absoluteEnd) {
        this.fileName = fileName;
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
        this.absoluteStart = absoluteStart;
        this.absoluteEnd = absoluteEnd;
    }

    // Versão simplificada para tokens de um único caractere
    public SourcePosition(String fileName, int line, int column, int absolutePos) {
        this(fileName, line, column, line, column, absolutePos, absolutePos + 1);
    }

    // Getters
    public String getFileName() {
        return fileName;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public int getAbsoluteStart() {
        return absoluteStart;
    }

    public int getAbsoluteEnd() {
        return absoluteEnd;
    }

    // Métodos úteis
    public int getLength() {
        return absoluteEnd - absoluteStart;
    }

    public boolean contains(int line, int column) {
        return (line >= startLine && line <= endLine) &&
                (line > startLine || column >= startColumn) &&
                (line < endLine || column <= endColumn);
    }

    public SourcePosition merge(SourcePosition other) {
        return new SourcePosition(
                fileName,
                Math.min(startLine, other.startLine),
                Math.min(startColumn, other.startColumn),
                Math.max(endLine, other.endLine),
                Math.max(endColumn, other.endColumn),
                Math.min(absoluteStart, other.absoluteStart),
                Math.max(absoluteEnd, other.absoluteEnd));
    }

    @Override
    public String toString() {
        if (startLine == endLine && startColumn == endColumn) {
            return fileName + ":" + startLine + ":" + startColumn;
        } else if (startLine == endLine) {
            return fileName + ":" + startLine + ":" + startColumn + "-" + endColumn;
        } else {
            return fileName + ":" + startLine + ":" + startColumn +
                    " to " + endLine + ":" + endColumn;
        }
    }
}