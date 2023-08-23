/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexical_analizer.Cosas;

public class Spliter {
    
    public String[] splitLines(String text){
        return text.split("[\n\r]");
    }
    
    public String[] splitWords (String text){
        return text.split(" ");
    }
    
    public String lastWord(int cursorIndex, String text){
        return text.substring(cursorIndex);
    }
    
    public String getLastWord(int cursorIndex, String text){
        String[] lines = splitLines(text);
        String lastLine = lines[lines.length];
        String[] words = this.splitWords(lastLine);
        return words[words.length];
    }
    
}
