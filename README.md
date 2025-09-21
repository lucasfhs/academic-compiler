Gerar o Jar:

Compile :
 
javac -d out src/*.java

Gere o jar:

jar cfm MeuPrograma.jar MANIFEST.MF -C out .

Execute o Jar:

java -jar MeuPrograma.jar



Padrões de Projeto Sugeridos :

Lexer

Lê o código fonte caractere a caractere e gera tokens.

Usa Factory para instanciar tokens diferentes.

Parser

Constrói a AST (Abstract Syntax Tree).

Usa Padrão Visitor para percorrer a árvore.

SemanticAnalyzer

Verifica tipos, variáveis não declaradas, etc.

Usa SymbolTable (tabela de símbolos).


Estrutura das Pastas:

compiler/
│── src/
│   ├── lexer/               # Analisador Léxico
│   │   ├── Lexer.java
│   │   ├── Token.java
│   │   ├── TokenType.java
│   │   └── LexerException.java
│   │
│   ├── parser/              # Analisador Sintático
│   │   ├── Parser.java
│   │   ├── ASTNode.java
│   │   ├── nodes/           # Nós da árvore sintática abstrata
│   │   │   ├── BinaryOpNode.java
│   │   │   ├── NumberNode.java
│   │   │   └── IdentifierNode.java
│   │   └── ParserException.java
│   │
│   ├── semantic/            # Analisador Semântico
│   │   ├── SemanticAnalyzer.java
│   │   ├── SymbolTable.java
│   │   ├── Symbol.java
│   │   └── TypeChecker.java
│   │
│   ├── utils/               # Utilidades
│   │   ├── ErrorHandler.java
│   │   └── Logger.java
│   │
│   └── Main.java        # Classe principal
│
└── tests/                   # Testes unitários
    ├── lexer/
    ├── parser/
    ├── semantic/
    ├── optimizer/
    └── codegen/

