/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lexical_analizer.InterfazGrafica;

import java.awt.Color;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ColorSelecter {
    
    public Color getIdentifierColor(){
        return new ColorUIResource(Color.black);
    }
    
    public Style getRojoStyle(StyledDocument doc){
        Style redStyle = doc.addStyle("RedStyle", null);
        StyleConstants.setForeground(redStyle, Color.RED);
        return redStyle;
    }
    
    public Style getCelesteStyle(StyledDocument doc){
        Style blueStyle = doc.addStyle("BlueStyle", null);
        StyleConstants.setForeground(blueStyle, Color.cyan);
        return blueStyle;
    }
    
    public Style getNegroStyle(StyledDocument doc){
        Style blackStyle = doc.addStyle("BlackStyle", null);
        StyleConstants.setForeground(blackStyle, Color.BLACK);
        return blackStyle;
    }
    
    public Style getMoradoStyle(StyledDocument doc){
        Style magentaStyle = doc.addStyle("MagentaStyle", null);
        StyleConstants.setForeground(magentaStyle, Color.MAGENTA);
        return magentaStyle;
    }
    
    public Style getGrisStyle(StyledDocument doc){
        Style grayStyle = doc.addStyle("GrayStyle", null);
        StyleConstants.setForeground(grayStyle, Color.GRAY);
        return grayStyle;
    }
    
    public Style getVerdeStyle(StyledDocument doc){
        Style greenStyle = doc.addStyle("GreenStyle", null);
        StyleConstants.setForeground(greenStyle, Color.GREEN);
        return greenStyle;
    }
    
}
