/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexical_analizer.Analyzer;

import com.mycompany.lexical_analizer.WordsToRecognize;

public class Analyzer {

    
    public boolean isPotentialReservatedWord(String text) {
        char[] word = text.toCharArray();
        return word[0] == 'a' || word[0] == 'b' || word[0] == 'c' || word[0] == 'd' || word[0] == 'e'
                || word[0] == 'f' || word[0] == 'g' || word[0] == 'i' || word[0] == 'l' || word[0] == 'n'
                || word[0] == 'o' || word[0] == 'p' || word[0] == 'r' || word[0] == 't' || word[0] == 'w'
                || word[0] == 'y';
    }

    public boolean isSymbol(String text) {
        char[] word = text.toCharArray();
        return word[0] == ',' || word[0] == '.' || word[0] == ';' || word[0] == ':' || word[0] == '-'
                || word[0] == '{' || word[0] == '}' || word[0] == '[' || word[0] == ']' || word[0] == '*'
                || word[0] == '(' || word[0] == ')' || word[0] == '+' || word[0] == '"' || word[0] == '<'
                || word[0] == '>' || word[0] == '%' || word[0] == '/' || word[0] == '!';
    }

    private boolean isNumberTheFirstSpace(String text) {
        try {
            char firstLetter = text.charAt(0);
            int reference = Integer.parseInt(firstLetter + "");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isReservatedWord(String text) {
        WordsToRecognize words = new WordsToRecognize();
        if (text.equals(words.getAND())) {
            return true;
        } else if (text.equals(words.getAS())) {
            return true;
        } else if (text.equals(words.getASIGNACION())) {
            return true;
        } else if (text.equals(words.getASSERT())) {
            return true;
        } else if (text.equals(words.getBREAK())) {
            return true;
        } else if (text.equals(words.getCLASS())) {
            return true;
        } else if (text.equals(words.getCOMA())) {
            return true;
        } else if (text.equals(words.getCOMILLAS())) {
            return true;
        } else if (text.equals(words.getCONTINUE())) {
            return true;
        } else if (text.equals(words.getCORCHETE_CERRAR())) {
            return true;
        } else if (text.equals(words.getCORCHETE_ABRIR())) {
            return true;
        } else if (text.equals(words.getDEF())) {
            return true;
        } else if (text.equals(words.getDEL())) {
            return true;
        } else if (text.equals(words.getDIFERENTE())) {
            return true;
        } else if (text.equals(words.getDIVISION())) {
            return true;
        } else if (text.equals(words.getDOS_PUNTOS())) {
            return true;
        } else if (text.equals(words.getELIF())) {
            return true;
        } else if (text.equals(words.getELSE())) {
            return true;
        } else if (text.equals(words.getEXCEPT())) {
            return true;
        } else if (text.equals(words.getFALSE())) {
            return true;
        } else if (text.equals(words.getFINALLY())) {
            return true;
        } else if (text.equals(words.getFOR())) {
            return true;
        } else if (text.equals(words.getFROM())) {
            return true;
        } else if (text.equals(words.getGLOBAL())) {
            return true;
        } else if (text.equals(words.getIF())) {
            return true;
        } else if (text.equals(words.getIMPORT())) {
            return true;
        } else if (text.equals(words.getIN())) {
            return true;
        } else if (text.equals(words.getIS())) {
            return true;
        } else if (text.equals(words.getLAMBDA())) {
            return true;
        } else if (text.equals(words.getNEGACION())) {
            return true;
        } else if (text.equals(words.getNONE())) {
            return true;
        } else if (text.equals(words.getNONLOCAL())) {
            return true;
        } else if (text.equals(words.getNOT())) {
            return true;
        } else if (text.equals(words.getO())) {
            return true;
        } else if (text.equals(words.getOR())) {
            return true;
        } else if (text.equals(words.getPASS())) {
            return true;
        } else if (text.equals(words.getRAISE())) {
            return true;
        } else if (text.equals(words.getRETURN())) {
            return true;
        } else if (text.equals(words.getTRUE())) {
            return true;
        } else if (text.equals(words.getTRY())) {
            return true;
        } else if (text.equals(words.getWHILE())) {
            return true;
        } else if (text.equals(words.getWITH())) {
            return true;
        } else if (text.equals(words.getYIELD())) {
            return true;
        }
        return false;
    }

}
