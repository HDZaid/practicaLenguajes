/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexical_analizer.Cosas;

public class Contador {
    
    public int countLineBreaksBeforeIndex(String text, int endIndex) {
        if (endIndex < 0 || endIndex > text.length()) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < endIndex; i++) {
            if (text.charAt(i) == '\n') {
                count++;
            }
        }
        return count;
    }
    
    public int getIndiceowo(String text, int caretPosition) {
        if (caretPosition < 0 || caretPosition > text.length()) {
            throw new IllegalArgumentException("Invalid caret position");
        }
        if (caretPosition == text.length() && caretPosition > 0 &&
            (text.charAt(caretPosition - 1) == ' ' || text.charAt(caretPosition - 1) == '\n')) {
            return caretPosition - 1;
        }
        int ultimoIndice = caretPosition - 1;
        while (ultimoIndice >= 0 && (text.charAt(ultimoIndice) == ' ' || text.charAt(ultimoIndice) == '\n')) {
            ultimoIndice--;
        }
        int primerIndice = ultimoIndice;
        while (primerIndice >= 0 && text.charAt(primerIndice) != ' ' && text.charAt(primerIndice) != '\n') {
            primerIndice--;
        }
        return primerIndice + 1;
    }
}
