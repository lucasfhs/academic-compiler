// package utils;

// import lexer.SourcePosition;

// public class Logger {

//     private static boolean hadError = false;

//     // Mensagens de informação (não encerram execução)
//     public static void info(String message) {
//         System.out.println("[INFO] " + message);
//     }

//     // Avisos (não encerram execução)
//     public static void warn(String message, SourcePosition pos) {
//         System.out.println(formatMessage("WARNING", message, pos));
//     }

//     // Erros que não encerram imediatamente (caso queira acumular erros)
//     public static void error(String message, SourcePosition pos) {
//         System.err.println(formatMessage("ERROR", message, pos));
//         hadError = true;
//     }

//     // Erro fatal: imprime e encerra imediatamente o programa
//     public static void fatal(String message, SourcePosition pos, String... args) {
//         System.err.println(formatMessage("FATAL", message, pos, args));
//         System.exit(1);
//     }

//     public static boolean hadError() {
//         return hadError;
//     }

//     // Formatação padrão
//     private static String formatMessage(String level, String message, SourcePosition pos, String... args) {
//         if (pos != null) {
//             String file = pos.getFileName();
//             // Simplify temp file names
//             if (file != null && file.contains("lexer_input")) {
//                 file = "input";
//             }
//             return String.format("[%s] %s na linha %d : coluna %d \n \"%s\"", level, message, pos.getStartLine(), pos.getStartColumn(), String.join(", ", args));
//         } else {
//             return String.format("[%s] %s", level, message);
//         }
//     }
// }
