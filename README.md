
# Compiler Project

Este projeto implementa um compilador simples em Java, organizado em módulos de análise léxica, sintática e semântica, seguindo boas práticas e padrões de projeto.

---

## Como Gerar e Executar o Jar

### 1. Compilar o código

```bash
javac -d out src/*.java
```
````

### 2. Gerar o arquivo JAR

```bash
jar cfm MeuPrograma.jar MANIFEST.MF -C out .
```

### 3. Executar o programa

```bash
java -jar MeuPrograma.jar
```

---

## Padrões de Projeto Utilizados

- **Lexer**

  - Lê o código fonte caractere a caractere e gera tokens.
  - Utiliza **Factory** para instanciar diferentes tipos de tokens.

- **Parser**

  - Constrói a **AST (Abstract Syntax Tree)**.
  - Utiliza o **Visitor** para percorrer a árvore.

- **SemanticAnalyzer**

  - Verifica tipos, variáveis não declaradas, etc.
  - Utiliza **SymbolTable (Tabela de Símbolos)** para gerenciar contexto.

---

## Estrutura de Pastas

```
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
│   │   ├── nodes/           # Nós da AST
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
│   └── Main.java            # Classe principal
│
└── tests/                   # Testes unitários
    ├── lexer/
    ├── parser/
    ├── semantic/
    ├── optimizer/
    └── codegen/
```

---

## Próximos Passos

- [ ] Implementar otimizador de código
- [ ] Implementar gerador de código
- [ ] Adicionar mais casos de teste

---

## Observações

- O projeto segue uma arquitetura modular.
- Padrões de projeto aplicados ajudam na extensibilidade do compilador.
- Recomenda-se manter testes unitários atualizados conforme novas funcionalidades forem adicionadas.
