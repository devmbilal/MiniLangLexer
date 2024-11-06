import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        String sourceCode = "int x = 10;\nif (x > 5) {\nprint(\"Hello World\");\n}";
        List<Token> tokens = lexer.tokenize(sourceCode);
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}