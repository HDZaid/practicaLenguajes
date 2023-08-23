/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexical_analizer.Analyzer;

import com.mycompany.lexical_analizer.Tokens.Comentario;
import com.mycompany.lexical_analizer.Tokens.Constant;
import com.mycompany.lexical_analizer.Tokens.Identifier;
import com.mycompany.lexical_analizer.Tokens.PalabraReservada;
import com.mycompany.lexical_analizer.Tokens.Symbol;
import com.mycompany.lexical_analizer.Tokens.Token;

public class SelectorDeSimbolos {

    private final Analyzer analyzer;

    public SelectorDeSimbolos() {
        this.analyzer = new Analyzer();
    }

    public Token tipoDeToken(String text) {
        if (text.length() == 1) {
            return this.posibleSimbolo(text);
        } else if (this.posibleConstante(text)) {
            return new Constant();
        } else if (this.posibleComentario(text)) {
            return new Comentario();
        } else if (text.length() >= 1) {
            return this.posiblePalabraReservada(text);
        } else if (text.length() == 0) {
            return null;
        } else {
            return new Token();
        }
    }

    private Token posibleSimbolo(String text) {
        if (this.analyzer.isSymbol(text)) {
            return new Symbol();
        } else if (this.posibleConstante(text)) {
            return new Constant();
        } else if(this.posibleComentario(text)){
            return new Comentario();
        }else {
            return this.posiblePalabraReservada(text);
        }
    }

    private Token posiblePalabraReservada(String text) {
        if (this.analyzer.isPotentialReservatedWord(text)) {
            if (this.analyzer.isReservatedWord(text)) {
                return new PalabraReservada();
            } else if (this.verificarIdentificador(text)) {
                return new Identifier();
            } else {
                return null;
            }
        } else if (this.verificarIdentificador(text)) {
            return new Identifier();
        } else {
            return null;
        }
    }

    private boolean verificarIdentificador(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        char primeraLetra = text.charAt(0);
        if (!((primeraLetra >= 'a' && primeraLetra <= 'z') || (primeraLetra >= 'A' && primeraLetra <= 'Z') || primeraLetra == '_')) {
            return false;
        }
        for (int i = 1; i < text.length(); i++) {
            char letra = text.charAt(i);
            if (!((letra >= 'a' && letra <= 'z') || (letra >= 'A' && letra <= 'Z') || (letra >= '0' && letra <= '9') || letra == '_')) {
                return false;
            }
        }

        return true;
    }

    private boolean posibleConstante(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
        }
        if (text.length() >= 2 && text.charAt(0) == '"' && text.charAt(text.length() - 1) == '"') {
            return true;
        }
        return false;
    }

    private boolean posibleComentario(String text) {
        if (text.length() > 0) {
            return text.charAt(0) == '#';
        }
        return false;
    }
}
