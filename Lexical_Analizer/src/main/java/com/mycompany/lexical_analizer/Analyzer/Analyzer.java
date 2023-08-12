/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexical_analizer.Analyzer;

import com.mycompany.lexical_analizer.WordsToRecognize;

/**
 *
 * @author Vicky_soch
 */
public class Analyzer {
    
    public void analyze(String text){
//        if(text){
//            
//        }else if(){
//            
//        }else if(){
//            
//        }else if(){
//            
//        }
    }
    //abcdefgilnoprtwy
    private boolean isPotentialReservatedWord(String text){
        char[] word = text.toCharArray();
        return word[0] == 'a' || word[0] == 'b' || word[0] == 'c' || word[0] == 'd' || word[0] == 'e' 
                || word[0] == 'f' || word[0] == 'g' || word[0] == 'i' || word[0] == 'l' || word[0] == 'n'
                || word[0] == 'o' || word[0] == 'p' || word[0] == 'r' || word[0] == 't' || word[0] == 'w'
                || word[0] == 'y';
    }
    
    private boolean isSymbol(String text){
        char[] word = text.toCharArray();
        return word[0] == ',' || word[0] == '.' || word[0] == ';' || word[0] == ':' || word[0] == '-' 
                || word[0] == '{' || word[0] == '}' || word[0] == '[' || word[0] == ']' || word[0] == '#'
                || word[0] == '(' || word[0] == ')' || word[0] == '+' || word[0] == '"' || word[0] == '<'
                || word[0] == '>' || word[0] == '%' || word[0] == '/' || word[0] == '!' || word[0] == '*';
    }
    
    private boolean isNumberTheFirstSpace(String text){
        try {
            char firstLetter = text.charAt(0);
            int reference = Integer.parseInt(firstLetter + "");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isReservatedWord(String text){
        WordsToRecognize words = new WordsToRecognize();
        if(text.equals(words.getAND())){
            
        }else if(text.equals(words.getAS())){
            
        }else if(text.equals(words.getASIGNACION())){
            
        }else if(text.equals(words.getASSERT())){
            
        }else if(text.equals(words.getBREAK())){
            
        }else if(text.equals(words.getCLASS())){
            
        }else if(text.equals(words.getCOMA())){
            
        }else if(text.equals(words.getCOMILLAS())){
            
        }else if(text.equals(words.getCONTINUE())){
            
        }else if(text.equals(words.getCORCHETE_CERRAR())){
            
        }else if(text.equals(words.getCORCHETE_ABRIR())){
            
        }else if(text.equals(words.getDEF())){
            
        }else if(text.equals(words.getDEL())){
            
        }else if(text.equals(words.getDIFERENTE())){
            
        }else if(text.equals(words.getDIVISION())){
            
        }else if(text.equals(words.getDOS_PUNTOS())){
            
        }else if(text.equals(words.getELIF())){
            
        }else if(text.equals(words.getELSE())){
            
        }else if(text.equals(words.getEXCEPT())){
            
        }else if(text.equals(words.getFALSE())){
            
        }else if(text.equals(words.getFINALLY())){
            
        }else if(text.equals(words.getFOR())){
            
        }else if(text.equals(words.getFROM())){
            
        }else if(text.equals(words.getGLOBAL())){
            
        }else if(text.equals(words.getIF())){
            
        }else if(text.equals(words.getIMPORT())){
            
        }else if(text.equals(words.getIN())){
            
        }else if(text.equals(words.getIS())){
            
        }else if(text.equals(words.getLAMBDA())){
            
        }else if(text.equals(words.getNEGACION())){
            
        }else if(text.equals(words.getNONE())){
            
        }else if(text.equals(words.getNONLOCAL())){
            
        }else if(text.equals(words.getNOT())){
            
        }else if(text.equals(words.getO())){
            
        }else if(text.equals(words.getOR())){
            
        }else if(text.equals(words.getPASS())){
            
        }else if(text.equals(words.getRAISE())){
            
        }else if(text.equals(words.getRETURN())){
            
        }else if(text.equals(words.getTRUE())){
            
        }else if(text.equals(words.getTRY())){
            
        }else if(text.equals(words.getWHILE())){
            
        }else if(text.equals(words.getWITH())){
            
        }else if(text.equals(words.getYIELD())){
            
        }
        return false;
    }
}
