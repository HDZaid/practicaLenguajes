/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package analizadorlexicosintactico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


class Token {

    String type;
    String lexeme;

    Token(String type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }
}
public class InterfazTablas extends javax.swing.JFrame {
    public List<String> getErrores() {
        return this.errores;
    }
    public void setInput(String newInput) {
    this.input = newInput;
    this.currentPos = 0;
    this.tokens.clear(); // Limpia los tokens existentes
    this.errores.clear(); // Limpia los errores existentes
    }
    private String input;
    private int currentPos;
    private List<Token> tokens;
    private Map<String, String> reservedWords;
    private List<String> errores = new ArrayList<>();
    FileNameExtensionFilter filtro= new FileNameExtensionFilter("Archivos Word y txt","docx","txt");
    File f;
    JFileChooser j= new JFileChooser();
    String data1 [][]={};
   String cabecera1[]={"No."," Token "," Tipo"};
    String path;
    int cont=0;

    String mensajini="";
    String tipo="";
    
  public InterfazTablas(String input) {
        this.input = input;
        this.currentPos = 0;
        this.tokens = new ArrayList<>();
        this.reservedWords = new HashMap<>();
        // Agregar palabras reservadas
        reservedWords.put("and", "AND");
        reservedWords.put("as", "AS");
        reservedWords.put("assert", "ASSERT");
        reservedWords.put("break", "BREAK");
        reservedWords.put("class", "CLASS");
        reservedWords.put("continue", "CONTINUE");
        reservedWords.put("def", "DEF");
        reservedWords.put("del", "DEL");
        reservedWords.put("elif", "ELIF");
        reservedWords.put("else", "ELSE");
        reservedWords.put("except", "EXCEPT");
        reservedWords.put("False", "FALSE");
        reservedWords.put("finally", "FINALLY");
        reservedWords.put("for", "FOR");
        reservedWords.put("from", "FROM");
        reservedWords.put("global", "GLOBAL");
        reservedWords.put("if", "IF");
        reservedWords.put("import", "IMPORT");
        reservedWords.put("in", "IN");
        reservedWords.put("is", "IS");
        reservedWords.put("lambda", "LAMBDA");
        reservedWords.put("None", "NONE");
        reservedWords.put("nonlocal", "NONLOCAL");
        reservedWords.put("not", "NOT");
        reservedWords.put("or", "OR");
        reservedWords.put("pass", "PASS");
        reservedWords.put("raise", "RAISE");
        reservedWords.put("return", "RETURN");
        reservedWords.put("True", "BOOL");
        reservedWords.put("try", "TRY");
        reservedWords.put("while", "WHILE");
        reservedWords.put("with", "WITH");
        reservedWords.put("yield", "YIELD");
    }
  
  
    public InterfazTablas() {
        initComponents();
         setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    
    public List<Token> tokenize() {
         int lineNumber = 1; // Inicializamos el número de línea en 1
        while (currentPos < input.length()) {
            char currentChar = input.charAt(currentPos);
            StringBuilder lexemeBuilder = new StringBuilder();

            // Reconocer comentarios
            if (currentChar == '#') {
                lexemeBuilder.append(currentChar);
                currentPos++;

                // Leer hasta el final de la línea
                while (currentPos < input.length() && input.charAt(currentPos) != '\n') {
                    lexemeBuilder.append(input.charAt(currentPos));
                    currentPos++;
                }
                // Obtiene comentarios
                String lexeme = lexemeBuilder.toString();
                tokens.add(new Token("COMENTARIO", lexeme));
                continue;
            }

            
            // Reconocer otros símbolos especiales
            switch (currentChar) {
                case '(':
                case ')':
                case '{':
                case '}':
                case '[':
                case ']':
                case ',':
                case ';':
                case ':':
                    lexemeBuilder.append(currentChar);
                    currentPos++;
                    String lexeme = lexemeBuilder.toString();
                    String description = getDescriptionForSymbol(lexeme);
                    tokens.add(new Token("SPECIAL_SYMBOL", description));
                    continue;
            }

            // Verificar si el carácter es un dígito (entero o decimal)
            if (Character.isDigit(currentChar)) {
                lexemeBuilder.append(currentChar);
                currentPos++;

                // Continuar leyendo dígitos y el punto decimal
                boolean isDecimal = false;
                while (currentPos < input.length()
                        && (Character.isDigit(input.charAt(currentPos)) || input.charAt(currentPos) == '.' && !isDecimal)) {
                    if (input.charAt(currentPos) == '.') {
                        isDecimal = true;
                    }
                    lexemeBuilder.append(input.charAt(currentPos));
                    currentPos++;
                }

                // Obtener el lexema
                String lexeme = lexemeBuilder.toString();

                // Clasificar como constante entera o decimal
                if (isDecimal) {
                    tokens.add(new Token("DECIMAL", lexeme));
                } else {
                    tokens.add(new Token("ENTERO", lexeme));
                }
            } // Verificar si el carácter es una comilla (inicio de una cadena)
            else if (currentChar == '"') {
                lexemeBuilder.append(currentChar);
                currentPos++;

                // Continuar leyendo la cadena hasta encontrar otra comilla
                while (currentPos < input.length() && input.charAt(currentPos) != '"') {
                    lexemeBuilder.append(input.charAt(currentPos));
                    currentPos++;
                }

                // Verificar si se cerró la cadena
                if (currentPos < input.length() && input.charAt(currentPos) == '"') {
                    lexemeBuilder.append(input.charAt(currentPos));
                    currentPos++;
                } else {
                    System.err.println("Error: Cadena no cerrada en la posición " + (currentPos - 1));
                }

                // Obtener el lexema
                String lexeme = lexemeBuilder.toString();
                tokens.add(new Token("CADENA", lexeme));
            } // Verificar si el carácter es una letra (inicio de una palabra)
            else if (Character.isLetter(currentChar) || currentChar == '_') {
                lexemeBuilder.append(currentChar);
                currentPos++;

                // Continuar leyendo letras, números y guiones bajos
                while (currentPos < input.length() && (Character.isLetterOrDigit(input.charAt(currentPos)) || input.charAt(currentPos) == '_')) {
                    lexemeBuilder.append(input.charAt(currentPos));
                    currentPos++;
                }

                // Obtener el lexema
                String lexeme = lexemeBuilder.toString();

                // Verificar si el lexema es una palabra reservada
                if (reservedWords.containsKey(lexeme)) {
                    tokens.add(new Token(reservedWords.get(lexeme), lexeme));
                } else {
                    tokens.add(new Token("IDENTIFICADOR", lexeme));
                }
            } else if (currentChar == '=') {
                if (currentPos + 1 < input.length() && input.charAt(currentPos + 1) == '=') {
                    tokens.add(new Token("OPERADOR: IGUAL", "=="));
                    currentPos += 2;
                } else if (currentPos + 1 < input.length() && input.charAt(currentPos + 1) == '>') {
                    tokens.add(new Token("OPERADOR: MAYOR IGUAL", "=>"));
                    currentPos += 2;
                } else {
                    tokens.add(new Token("OPERADOR: ASIGNACIÓN", "="));
                    currentPos++;
                }
            } // Manejar operadores +, -, *, /, etc.
            else if (currentChar == '+') {
                tokens.add(new Token("OPERADOR: SUMA", "+"));
                currentPos++;
            } else if (currentChar == '-') {
                tokens.add(new Token("OPERADOR: RESTA", "-"));
                currentPos++;
            } else if (currentChar == '*') {
                tokens.add(new Token("OPERADOR: MULTIPLIACIÓN", "*"));
                currentPos++;
            } else if (currentChar == '/') {
                tokens.add(new Token("OPERADOR: DIVISIÓN", "/"));
                currentPos++;
            } // Manejar operadores lógicos
            else if (currentChar == 'a' && currentPos + 2 < input.length() && input.substring(currentPos, currentPos + 3).equals("and")) {
                tokens.add(new Token("LOGICAL_AND", "and"));
                currentPos += 3;
            } else if (currentChar == 'o' && currentPos + 1 < input.length() && input.charAt(currentPos + 1) == 'r') {
                tokens.add(new Token("LOGICAL_OR", "or"));
                currentPos += 2;
            } else if (currentChar == 'n' && currentPos + 2 < input.length() && input.substring(currentPos, currentPos + 3).equals("not")) {
                tokens.add(new Token("LOGICAL_NOT", "not"));
                currentPos += 3;
            } // Manejar operadores de igualdad y comparación
            else if (currentChar == '=') {
                if (currentPos + 1 < input.length() && input.charAt(currentPos + 1) == '=') {
                    tokens.add(new Token("OPERADOR: IGUAL", "=="));
                    currentPos += 2;
                } else {
                    tokens.add(new Token("OPERADOR: ASIGNACIÓN", "="));
                    currentPos++;
                }
            } else if (currentChar == '!') {
                if (currentPos + 1 < input.length() && input.charAt(currentPos + 1) == '=') {
                    tokens.add(new Token("OPERADOR: DIFERENTE", "!="));
                    currentPos += 2;
                } else {
                    System.err.println("Error: Operador no válido en la posición " + currentPos);
                    currentPos++;
                }
            } else if (currentChar == '>') {
                if (currentPos + 1 < input.length() && input.charAt(currentPos + 1) == '=') {
                    tokens.add(new Token("OPERADOR: MAYOR IGUAL", ">="));
                    currentPos += 2;
                } else {
                    tokens.add(new Token("OPERADOR: MAYOR", ">"));
                    currentPos++;
                }
            } else if (currentChar == '<') {
                if (currentPos + 1 < input.length() && input.charAt(currentPos + 1) == '=') {
                    tokens.add(new Token("OPERADOR: MENOR IGUAL", "<="));
                    currentPos += 2;
                } else {
                    tokens.add(new Token("OPERDOR: MENOR", "<"));
                    currentPos++;
                }
            } // Manejar números enteros
            else if (Character.isDigit(currentChar)) {
                int start = currentPos;
                while (currentPos < input.length() && (Character.isDigit(input.charAt(currentPos)) || input.charAt(currentPos) == '.')) {
                    currentPos++;
                }
                String lexeme = input.substring(start, currentPos);
                tokens.add(new Token("ENTERO", lexeme));
            } // Manejar operadores
            else if (currentChar == '+') {
                tokens.add(new Token("OPERADOR: SUMA", "+"));
                currentPos++;
            } else if (currentChar == '-') {
                tokens.add(new Token("OPERADOR: RESTAR", "-"));
                currentPos++;
            } else if (currentChar == '*') {
                tokens.add(new Token("OPERDOR: MULTIPLCACIÓN", "*"));
                currentPos++;
            } else if (currentChar == '/') {
                tokens.add(new Token("OPERADOR: DIVISIÓN", "/"));
                currentPos++;
                // Manejar división entera
                if (currentPos < input.length() && input.charAt(currentPos) == '/') {
                    tokens.add(new Token("INTEGER_DIVIDE", "//"));
                    currentPos++;
                }
            } else if (currentChar == '%') {
                tokens.add(new Token("MODULO", "%"));
                currentPos++;
            } else if (currentChar == '*') {
                tokens.add(new Token("EXPONENT", "**"));
                currentPos++;
            } // Manejar identificadores
            else if (Character.isLetter(currentChar) || currentChar == '_') {
                int start = currentPos;
                while (currentPos < input.length() && (Character.isLetterOrDigit(input.charAt(currentPos)) || input.charAt(currentPos) == '_')) {
                    currentPos++;
                }
                String lexeme = input.substring(start, currentPos);

                if (isValidIdentifier(lexeme)) {
                    tokens.add(new Token("ID", lexeme));
                } else {
                    System.err.println("Error: Identificador no válido en la posición " + start);
                }
            } // Ignorar espacios en blanco
            else if (currentChar == ' ' || currentChar == '\n' || currentChar == '\r' || currentChar == '\t') {
                currentPos++;

            } // Manejar cualquier otro carácter como error
            else {
                
             String error = "Error en la línea " + lineNumber + ": Carácter no reconocido '" + currentChar + "'";
            errores.add(error);
            currentPos++;
            System.out.println("Error agregado: " + error);
        }
              if (currentChar == '\n') {
            lineNumber++;
        }
        }
        return tokens;
        
    }

      private boolean isValidIdentifier(String lexeme) {
        if (lexeme.length() == 0) {
            return false;
        }

        char firstChar = lexeme.charAt(0);
        if (!Character.isLetter(firstChar) && firstChar != '_') {
            return false;
        }

        for (int i = 1; i < lexeme.length(); i++) {
            char currentChar = lexeme.charAt(i);
            if (!Character.isLetterOrDigit(currentChar) && currentChar != '_') {
                return false;
            }
        }

        return true;
    }

 
     private String getDescriptionForSymbol(String symbol) {
        switch (symbol) {
            case "(":
                return "Paréntesis: (";
            case ")":
                return "Paréntesis: )";
            case "{":
                return "Llaves: {";
            case "}":
                return "Llaves: }";
            case "[":
                return "Corchetes: [";
            case "]":
                return "Corchetes: ]";
            case ",":
                return "Coma: ,";
            case ";":
                return "Punto y coma: ;";
            case ":":
                return "Dos puntos: :";
            default:
                return "Símbolo especial no reconocido";
        }
    }
    private void agregarTokensATabla(List<Token> tokens) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
    
    // Limpia la tabla antes de agregar nuevos tokens
    while (model.getRowCount() > 0) {
        model.removeRow(0);
    }
    
    for (int i = 0; i < tokens.size(); i++) {
        Token token = tokens.get(i);
        String tipo = token.type;
        String lexema = token.lexeme;
        
        // Agrega el token a la tabla como una fila
        model.addRow(new Object[] { i + 1, tipo, lexema });
    }
}
    
    //Sintactico
    // Método para analizar declaraciones de variables
    public static boolean analyzeVariableDeclaration(String line) {
    int state = 0;
    int index = 0;
    line = line.trim(); // Eliminar espacios en blanco al principio y al final

    while (index < line.length()) {
        char c = line.charAt(index);

        switch (state) {
            case 0:
                if (Character.isLetter(c) || c == '_') {
                    state = 1;
                } else {
                    return false;
                }
                break;
            case 1:
                if (Character.isLetterOrDigit(c) || c == '_') {
                    // Permanecer en el estado 1
                } else if (Character.isWhitespace(c)) {
                    state = 2;
                } else {
                    return false;
                }
                break;
            case 2:
                if (Character.isLetter(c) || c == '_') {
                    state = 1;
                } else {
                    return false;
                }
                break;
        }

        index++;
    }

    return state == 1;
}

    public static boolean analyzeAssignation(String linea) {
    int state = 0;
    int index = 0;
    linea = linea.trim(); // Eliminar espacios en blanco al principio y al final

    while (index < linea.length()) {
        char c = linea.charAt(index);

        switch (state) {
            case 0:
                if (Character.isWhitespace(c)) {
                    // Permanecer en el estado 0
                } else if (Character.isLetter(c)) {
                    state = 1;
                } else {
                    return false;
                }
                break;
            case 1:
                if (Character.isLetterOrDigit(c) || c == '_') {
                    // Permanecer en el estado 1
                } else if (Character.isWhitespace(c)) {
                    state = 2;
                } else if (isAssignmentOperator(c)) {
                    state = 3;
                } else {
                    return false;
                }
                break;
            case 2:
                if (isAssignmentOperator(c)) {
                    state = 3;
                } else if (!Character.isWhitespace(c)) {
                    return false;
                }
                break;
            case 3:
                // No se realiza ninguna validación adicional en este estado
                break;
        }

        index++;
    }

    return state == 3;
}

    public static boolean isAssignmentOperator(char c) {
    return c == '=' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }
    
    public static boolean analyzeCallFunction(String linea) {
    int state = 0;
    int index = 0;
    linea = linea.trim(); // Eliminar espacios en blanco al principio y al final

    while (index < linea.length()) {
        char c = linea.charAt(index);

        switch (state) {
            case 0:
                if (Character.isWhitespace(c)) {
                    // Permanecer en el estado 0
                } else if (Character.isLetter(c)) {
                    state = 1;
                } else {
                    return false;
                }
                break;
            case 1:
                if (Character.isLetterOrDigit(c) || c == '_') {
                    // Permanecer en el estado 1
                } else if (Character.isWhitespace(c)) {
                    state = 2;
                } else if (c == '(') {
                    state = 3;
                } else {
                    return false;
                }
                break;
            case 2:
                if (c == '(') {
                    state = 3;
                } else if (!Character.isWhitespace(c)) {
                    return false;
                }
                break;
            case 3:
                // Continuar en el estado 3 hasta encontrar el cierre de paréntesis ')'
                if (c == ')') {
                    state = 4;
                }
                break;
            case 4:
                // No se realiza ninguna validación adicional en este estado
                break;
        }

        index++;
    }

    return state == 4;
}
    
    
    // Método para analizar condicionales (if)
     public static boolean analyzeConditional(String line) {
    int state = 0;

    for (char c : line.toCharArray()) {
        switch (state) {
            case 0:
                if (c == ' ' || c == '\t') {
                    state = 0;
                } else if (c == 'i') {
                    state = 1;
                } else if (c == 'e') {
                    state = 7;
                } else {
                    return false; // Carácter no válido en el inicio
                }
                break;
            case 1:
                if (c == 'f') {
                    state = 2;
                } else if (c == 'l') {
                    state = 4;
                } else {
                    return false;
                }
                break;
            case 2:
            case 3:
            case 6:
            case 8:
                if (c == ' ' || c == '\t') {
                    state++;
                } else {
                    return false;
                }
                break;
            case 4:
                if (c == 'i') {
                    state = 5;
                } else {
                    return false;
                }
                break;
            case 5:
                if (c == 'f') {
                    state = 6;
                } else {
                    return false;
                }
                break;
            case 7:
                if (c == 'l') {
                    state = 8;
                } else {
                    return false;
                }
                break;
            case 9:
            case 10:
                if (c == ' ' || c == '\t') {
                    state = 9;
                } else if (c == ':') {
                    state = 10;
                } else {
                    return true; // Se ha detectado un "if", "elif" o "else"
                }
        }
    }

    return false; // No se ha detectado un "if", "elif" o "else"
}

    private static int lastIndentCount = 0;
    private static int activeIfBlocks = 0;
    private static boolean inIfBlock = false;

    // Método para analizar bucles (for)
    public static boolean analyzeLoop(String line) {
    int state = 0;

    for (char c : line.toCharArray()) {
        switch (state) {
            case 0:
                if (c == 'f') {
                    state = 1;
                } else if (c == ' ' || c == '\t') {
                    // Permite espacios o tabulaciones antes de la palabra "for" o "else"
                    state = 0;
                } else {
                    state = 0;
                }
                break;
            case 1:
                if (c == 'o') {
                    state = 2;
                } else if (c == 'e') {
                    state = 4;
                } else {
                    state = 0;
                }
                break;
            case 2:
                if (c == 'r') {
                    state = 3;
                } else {
                    state = 0;
                }
                break;
            case 3:
                if (c == ' ' || c == ':') {
                    return true; // Se ha detectado un ciclo "for"
                } else if (c != ' ') {
                    state = 0;
                }
                break;
            case 4:
                if (c == 'l') {
                    state = 5;
                } else {
                    state = 0;
                }
                break;
            case 5:
                if (c == 's') {
                    state = 6;
                } else {
                    state = 0;
                }
                break;
            case 6:
                if (c == 'e') {
                    state = 7;
                } else {
                    state = 0;
                }
                break;
            case 7:
                if (c == ' ' || c == ':') {
                    return true; // Se ha detectado un "else"
                } else if (c != ' ') {
                    state = 0;
                }
                break;
        }
    }

    return false;
}

    public static boolean analyzeWhileLoop(String line) {
    int state = 0;

    for (char c : line.toCharArray()) {
        switch (state) {
            case 0:
                if (c == 'w' || c == 'W') {
                    state = 1;
                } else if (c == ' ' || c == '\t') {
                    // Permite espacios o tabulaciones antes de la palabra "while"
                    state = 0;
                } else {
                    state = 0;
                }
                break;
            case 1:
                if (c == 'h' || c == 'H') {
                    state = 2;
                } else {
                    state = 0;
                }
                break;
            case 2:
                if (c == 'i' || c == 'I') {
                    state = 3;
                } else {
                    state = 0;
                }
                break;
            case 3:
                if (c == 'l' || c == 'L') {
                    state = 4;
                } else {
                    state = 0;
                }
                break;
            case 4:
                if (c == 'e' || c == 'E') {
                    state = 5;
                } else {
                    state = 0;
                }
                break;
            case 5:
                if (c == ' ' || c == ':') {
                    return true; // Se ha detectado un "while"
                } else if (c != ' ') {
                    state = 0;
                }
                break;
        }
    }

    return false;
}

    // Método para analizar definición de funciones
    public static boolean analyzeFunctionDeclaration(String line) {
    // Elimina los espacios en blanco al principio y al final de la línea.
    line = line.trim();

    // Define el estado inicial y un conjunto de estados finales.
    String currentState = "start";
    String[] finalStates = {"def"};

    int index = 0;
    boolean insideParentheses = false;

    // Itera a través de cada carácter en la línea.
    while (index < line.length()) {
        char c = line.charAt(index);

        switch (currentState) {
            case "start":
                if (c == 'd') {
                    currentState = "d";
                } else {
                    return false;
                }
                break;
            case "d":
                if (c == 'e') {
                    currentState = "de";
                } else {
                    return false;
                }
                break;
            case "de":
                if (c == 'f') {
                    currentState = "def";
                } else if (c == '(') {
                    currentState = "def(";
                    insideParentheses = true;
                } else {
                    return false;
                }
                break;
            case "def":
                if (insideParentheses && c == ')') {
                    currentState = "def)";
                } else if (insideParentheses && c == '(') {
                    // No permitir paréntesis anidados
                    return false;
                }
                break;
            case "def)":
                if (c == ':') {
                    currentState = "def):";
                } else if (c == '#' || Character.isWhitespace(c)) {
                    // Permitir comentarios después de los paréntesis de cierre
                } else {
                    return false;
                }
                break;
            case "def):":
                if (Character.isWhitespace(c)) {
                    // Permitir espacios en blanco antes de comentarios
                } else if (c == '#') {
                    currentState = "def):#";
                } else {
                    return false;
                }
                break;
            case "def):#":
                // Permitir cualquier carácter después de '#' en comentarios
                break;
            default:
                // Llegó a un estado final, se verifica si es uno de los estados finales válidos.
                if (isFinalState(currentState, finalStates)) {
                    return true;
                } else {
                    return false;
                }
        }

        index++;
    }

    // Verifica si el estado actual es un estado final válido.
    return isFinalState(currentState, finalStates);
}

// Función auxiliar para verificar si el estado es un estado final válido.
    private static boolean isFinalState(String state, String[] finalStates) {
    for (String finalState : finalStates) {
        if (state.equals(finalState)) {
            return true;
        }
    }
    return false;
}
    
    public static boolean analyzeBreak(String line) {
    int state = 0;
    int index = 0;
    line = line.trim(); // Eliminar espacios en blanco al principio y al final

    while (index < line.length()) {
        char c = line.charAt(index);

        switch (state) {
            case 0:
                if (Character.isWhitespace(c)) {
                    // Permanecer en el estado 0
                } else if (c == 'b') {
                    state = 1;
                } else {
                    return false;
                }
                break;
            case 1:
                if (c == 'r') {
                    state = 2;
                } else {
                    return false;
                }
                break;
            case 2:
                if (c == 'e') {
                    state = 3;
                } else {
                    return false;
                }
                break;
            case 3:
                if (c == 'a') {
                    state = 4;
                } else {
                    return false;
                }
                break;
            case 4:
                if (c == 'k') {
                    state = 5;
                } else {
                    return false;
                }
                break;
            case 5:
                if (Character.isWhitespace(c)) {
                    // Permanecer en el estado 5
                } else if (c == 'w') {
                    state = 6;
                } else {
                    return false;
                }
                break;
            case 6:
                if (Character.isLetterOrDigit(c) || c == '_' || Character.isWhitespace(c) || c == '#') {
                    // Permanecer en el estado 6
                } else {
                    return false;
                }
                break;
        }

        index++;
    }

    return state == 6;
}
    
    public static boolean analyzePrint(String line) {
    int state = 0;
    int index = 0;
    line = line.trim(); // Eliminar espacios en blanco al principio y al final

    while (index < line.length()) {
        char c = line.charAt(index);

        switch (state) {
            case 0:
                if (c == 'p') {
                    state = 1;
                } else {
                    return false;
                }
                break;
            case 1:
                if (c == 'r') {
                    state = 2;
                } else {
                    return false;
                }
                break;
            case 2:
                if (c == 'i') {
                    state = 3;
                } else {
                    return false;
                }
                break;
            case 3:
                if (c == 'n') {
                    state = 4;
                } else {
                    return false;
                }
                break;
            case 4:
                if (c == 't') {
                    state = 5;
                } else if (Character.isWhitespace(c)) {
                    // Permitir espacios en blanco después de 'print'
                } else {
                    return false;
                }
                break;
            case 5:
                if (c == '(') {
                    state = 6;
                } else if (Character.isWhitespace(c)) {
                    // Permitir espacios en blanco antes de '('
                }
                // Puedes extender este caso para manejar expresiones dentro de paréntesis
                break;
            case 6:
                if (c == ')') {
                    state = 7;
                } else {
                    // Puedes extender este caso para validar contenido dentro de los paréntesis
                }
                break;
            case 7:
                // Permitir cualquier carácter después de ')'
                break;
            default:
                return false; // Estado no válido
        }

        index++;
    }

    return state == 7; // El estado final es 7
}
    
    public static boolean analyzeLineEmpty(String line) {
    int state = 0;
    int index = 0;
    line = line.trim(); // Eliminar espacios en blanco al principio y al final

    while (index < line.length()) {
        char c = line.charAt(index);

        switch (state) {
            case 0:
                if (Character.isWhitespace(c)) {
                    // Permanecer en el estado 0
                } else {
                    return false;
                }
                break;
        }

        index++;
    }

    return true;
}

       public static boolean analyzeComment(String line) {
    int state = 0;
    int index = 0;
    line = line.trim(); // Eliminar espacios en blanco al principio y al final

    while (index < line.length()) {
        char c = line.charAt(index);

        switch (state) {
            case 0:
                if (c == '#') {
                    state = 1;
                } else {
                    return false;
                }
                break;
            case 1:
                // Se permite cualquier carácter dentro de un comentario
                // No se realiza ninguna validación adicional en este estado
                break;
        }

        index++;
    }

    return state == 1;
}

    
      
       public static boolean noCharacters(String line) {
    // Elimina los espacios en blanco al principio y al final de la línea
    line = line.trim();
    
    // Comprueba si la línea está vacía
    return line.isEmpty();
}
    public static boolean analyzeReturnStatement(String line) {
    // Elimina los espacios en blanco al principio y al final de la línea
    line = line.trim();

    // Verifica si la línea comienza con "return " (con un espacio después)
    return line.startsWith("return ");
}   
       
    public static void analyzePythonCode(String code, JTextArea textArea) {

         String[] codeLines = code.split("\n");

        for (String line : codeLines) {
            if (analyzeVariableDeclaration(line)) {
                textArea.append("Declaracion de Variable: " + line + "\n");
            }else if (analyzeComment(line)) {
                textArea.append("Comentario: " + line + "\n");
            }else if (analyzeReturnStatement(line)) {
                textArea.append("Retorna valor: " + line + "\n");
            }else if (analyzeAssignation(line)) {
                textArea.append("Asignación: " + line + "\n");
            } else if (analyzeConditional(line)) {
                textArea.append("Condicional: " + line + "\n");
            } else if (analyzeLoop(line)) {
                textArea.append("Bucle: " + line + "\n");
            } else if (analyzeWhileLoop(line)) {
                textArea.append("Bucle While: " + line + "\n");
            } else if (analyzePrint(line)) {
                textArea.append("Imprimir: " + line + "\n");
            }else if (analyzeBreak(line)) {
                textArea.append("Rompe while: " + line + "\n");
            } else if (noCharacters(line)) {
                textArea.append("Salto de Linea: " + line + "\n");
            } else if (analyzeCallFunction(line)) {
                textArea.append("Llamar función: " + line + "\n");
            } else if (analyzeFunctionDeclaration(line)) {
                textArea.append("Declaración de metodo: " + line + "\n");
            } else if (analyzeError(line)) {
                textArea.append("Error de sintaxis: " + line + "\n");
            }
        }
        // Analizar toda la cadena como una sola línea

    }

    ////////////
    // Método para analizar errores
    public static boolean analyzeError(String line) {
        // Si no coincide con ningún patrón anterior, se considera un error
        return true;
    }
    

    ////////////
    // Método para analizar errores

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jbtnImportar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        txtATexto1 = new javax.swing.JEditorPane();
        Lineas = new javax.swing.JEditorPane();
        LineaError = new javax.swing.JEditorPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jbtnGenerar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtAreaErrorLexico = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtxtASintactico = new javax.swing.JTextArea();
        jbtnGenerar1 = new javax.swing.JButton();
        jbtnGenerar2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel1.setText("CODIGO :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("ERROR :");

        jbtnImportar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbtnImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/analizadorlexicosintactico/importar (1).png"))); // NOI18N
        jbtnImportar.setText("IMPORTAR CODIGO");
        jbtnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImportarActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtATexto1.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        txtATexto1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtATexto1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtATexto1KeyReleased(evt);
            }
        });

        Lineas.setEditable(false);
        Lineas.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        Lineas.setText("1");
        Lineas.setMinimumSize(new java.awt.Dimension(100000, 23));
        Lineas.setOpaque(false);

        LineaError.setEditable(false);
        LineaError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LineaError.setForeground(java.awt.Color.red);
        LineaError.setToolTipText("");
        LineaError.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(LineaError, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Lineas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtATexto1, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LineaError, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
            .addComponent(txtATexto1)
            .addComponent(Lineas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel1);

        tabla.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        tabla.setForeground(new java.awt.Color(0, 51, 204));
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "N°", "OPERADORES", "VALOR"
            }
        ));
        jScrollPane3.setViewportView(tabla);

        jbtnGenerar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbtnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/analizadorlexicosintactico/software-de-analisis-de-texto.png"))); // NOI18N
        jbtnGenerar.setText("ANALISIS LEXICO - CODIGO");
        jbtnGenerar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jbtnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGenerarActionPerformed(evt);
            }
        });

        jtxtAreaErrorLexico.setEditable(false);
        jtxtAreaErrorLexico.setColumns(20);
        jtxtAreaErrorLexico.setRows(5);
        jScrollPane1.setViewportView(jtxtAreaErrorLexico);

        jtxtASintactico.setEditable(false);
        jtxtASintactico.setColumns(20);
        jtxtASintactico.setRows(5);
        jScrollPane4.setViewportView(jtxtASintactico);

        jbtnGenerar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbtnGenerar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/analizadorlexicosintactico/Generar.png"))); // NOI18N
        jbtnGenerar1.setText("GENERAR TABLA DE SIMBOLOS");
        jbtnGenerar1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jbtnGenerar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGenerar1ActionPerformed(evt);
            }
        });

        jbtnGenerar2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jbtnGenerar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/analizadorlexicosintactico/software-de-analisis-de-texto.png"))); // NOI18N
        jbtnGenerar2.setText("ANALISIS SINTACTICO");
        jbtnGenerar2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jbtnGenerar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGenerar2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setText("ANALISIS :");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/analizadorlexicosintactico/4698732 (1).png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnImportar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnGenerar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnGenerar2))
                    .addComponent(jbtnGenerar1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jbtnImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnGenerar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(52, 52, 52)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jbtnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtnGenerar2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(187, 187, 187))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImportarActionPerformed
        j.setCurrentDirectory(new File("src\\lexicosintactico"));
        j.getSelectedFile();
        j.setFileFilter(filtro);
        j.showOpenDialog(j);  
        int contPalabra=0;
       try{
        path= j.getSelectedFile().getAbsolutePath();
        String name=j.getSelectedFile().getName();
        String lectura="";
        f = new File(path);
        try{
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String aux;
       //Aqui cuento cuantas palabras hay
       StreamTokenizer st=new StreamTokenizer(new FileReader(f));
       while(st.nextToken()!=StreamTokenizer.TT_EOF){
          if(st.ttype==StreamTokenizer.TT_WORD){
              contPalabra++;             
          }      
       }
            while((aux = br.readLine())!=null)
               lectura = lectura+aux+"\n";
        }catch(IOException e){}
            txtATexto1.setText(lectura);           
            int contador=0;
        StringTokenizer st = new StringTokenizer(txtATexto1.getText(),"\n",true);
                String Text = "",token;
                contador = 1;
                while (st.hasMoreTokens()){
                    token= st.nextToken();
                    if("\n".equals(token)) contador++;
                }
                for(int i = 1; i <= contador; i++){
                    Text += i+"\n";
                }
                Lineas.setText(Text);    
         }catch(NullPointerException e){
            javax.swing.JOptionPane.showMessageDialog(j, "Has seleccionado cerrar programa, saliendo...");
        System.exit(0);
}   
    }//GEN-LAST:event_jbtnImportarActionPerformed

    private void jbtnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGenerarActionPerformed
           String input = txtATexto1.getText();
           InterfazTablas lexer = new InterfazTablas(input);
    // Verificar si ya existe una instancia de InterfazTablas
    if (lexer == null) {
        lexer = new InterfazTablas(input);
    } else {
        lexer.setInput(input);
    }
    
    // Realizar la tokenización
    List<Token> tokens = lexer.tokenize();
        StringBuilder tokenText = new StringBuilder();
        
        for (Token token : tokens) {
        String tokenType = token.type;
        String tokenLexeme = token.lexeme;
        tokenText.append(tokenType).append(": ").append(tokenLexeme).append("\n");
        }
        
    // Mostrar errores de tokens
    StringBuilder erroresText = new StringBuilder();
    
        if (lexer.getErrores().isEmpty()) {
        erroresText.append("No se encontraron tokens desconocidos");
        } else {
        for (String error : lexer.getErrores()) {
            erroresText.append(error).append("\n");
            }
        }
    jtxtAreaErrorLexico.setText(erroresText.toString());

    }//GEN-LAST:event_jbtnGenerarActionPerformed

    private void jbtnGenerar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGenerar1ActionPerformed
           String input = txtATexto1.getText();
           InterfazTablas lexer = new InterfazTablas(input);
           List<Token> tokens = lexer.tokenize();
            agregarTokensATabla(tokens);
    

        
    
    }//GEN-LAST:event_jbtnGenerar1ActionPerformed

    
    
    private void jbtnGenerar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGenerar2ActionPerformed
        
        jtxtASintactico.setText("");
        Document document = txtATexto1.getDocument();
                String textWithoutFormat;
        try {
            textWithoutFormat = document.getText(0, document.getLength());
            
            analyzePythonCode(textWithoutFormat,jtxtASintactico);
        } catch (BadLocationException ex) {
            Logger.getLogger(InterfazTablas.class.getName()).log(Level.SEVERE, null, ex);
        }
                
         

       
    }//GEN-LAST:event_jbtnGenerar2ActionPerformed

    private void txtATexto1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtATexto1KeyReleased
        StringTokenizer st = new StringTokenizer(txtATexto1.getText(),"\n",true);
        String txt = "",token;
        cont = 1;

        while (st.hasMoreTokens()){
            token= st.nextToken();
            if("\n".equals(token)) cont++;
        }

        for(int i = 1; i <= cont; i++){
            txt += i+"\n";
        }
        Lineas.setText(txt);
    }//GEN-LAST:event_txtATexto1KeyReleased

    private void txtATexto1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtATexto1KeyPressed

    }//GEN-LAST:event_txtATexto1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazTablas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazTablas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazTablas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazTablas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new MetalLookAndFeel());
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(InterfazTablas.class.getName()).log(Level.SEVERE, null, ex);
                }
                new InterfazTablas().setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane LineaError;
    private javax.swing.JEditorPane Lineas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbtnGenerar;
    private javax.swing.JButton jbtnGenerar1;
    private javax.swing.JButton jbtnGenerar2;
    private javax.swing.JButton jbtnImportar;
    public javax.swing.JTextArea jtxtASintactico;
    private javax.swing.JTextArea jtxtAreaErrorLexico;
    private javax.swing.JTable tabla;
    private javax.swing.JEditorPane txtATexto1;
    // End of variables declaration//GEN-END:variables
}
