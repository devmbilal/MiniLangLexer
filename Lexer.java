import java.util.*;

public class Lexer {
    private static final Set<String> keywords = new HashSet<>(Arrays.asList("if", "else", "while", "print", "int", "float"));
    private static final Set<String> operators = new HashSet<>(Arrays.asList("+", "-", "*", "/", "=", "==", "!=", "<", ">", "<=", ">="));
    private static final Set<Character> delimiters = new HashSet<>(Arrays.asList('(', ')', '{', '}', ';', ','));

    private List<Token> tokens = new ArrayList<>();
    private int lineNumber = 1;

    public List<Token> tokenize(String sourceCode) {
        int length = sourceCode.length();
        for (int i = 0; i < length; i++) {
            char currentChar = sourceCode.charAt(i);

            // Skip whitespace
            if (Character.isWhitespace(currentChar)) {
                if (currentChar == '\n') {
                    lineNumber++;
                }
                continue;
            }

            // Handle comments
            if (currentChar == '/' && i + 1 < length && sourceCode.charAt(i + 1) == '/') {
                while (i < length && sourceCode.charAt(i) != '\n') {
                    i++;
                }
                lineNumber++;
                continue;
            }

            // Handle keywords and identifiers
            if (Character.isLetter(currentChar)) {
                int start = i;
                while (i < length && (Character.isLetterOrDigit(sourceCode.charAt(i)))) {
                    i++;
                }
                String lexeme = sourceCode.substring(start, i);
                i--;
                if (keywords.contains(lexeme)) {
                    tokens.add(new Token(TokenType.KEYWORD, lexeme));
                } else {
                    tokens.add(new Token(TokenType.IDENTIFIER, lexeme));
                }
                continue;
            }

            // Handle numbers (literals)
            if (Character.isDigit(currentChar)) {
                int start = i;
                while (i < length && (Character.isDigit(sourceCode.charAt(i)) || sourceCode.charAt(i) == '.')) {
                    i++;
                }
                String lexeme = sourceCode.substring(start, i);
                i--;
                tokens.add(new Token(TokenType.LITERAL, lexeme));
                continue;
            }

            // Handle string literals
            if (currentChar == '"') {
                int start = i;
                i++;
                while (i < length && sourceCode.charAt(i) != '"') {
                    i++;
                }
                if (i < length) {
                    String lexeme = sourceCode.substring(start, i + 1);
                    tokens.add(new Token(TokenType.LITERAL, lexeme));
                } else {
                    System.err.println("Error: Unterminated string literal at line " + lineNumber);
                }
                continue;
            }

            // Handle operators
            if (operators.contains(String.valueOf(currentChar))) {
                int start = i;
                while (i < length && operators.contains(sourceCode.substring(start, i + 1))) {
                    i++;
                }
                String lexeme = sourceCode.substring(start, i);
                i--;
                tokens.add(new Token(TokenType.OPERATOR, lexeme));
                continue;
            }

            // Handle delimiters
            if (delimiters.contains(currentChar)) {
                tokens.add(new Token(TokenType.DELIMITER, String.valueOf(currentChar)));
                continue;
            }

            // Handle unrecognized characters
            System.err.println("Error: Unrecognized character '" + currentChar + "' at line " + lineNumber);
        }
        return tokens;
    }
}