/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package analizadorlexicosintactico;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class AnalizadorLexicoSintactico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     try {
                    UIManager.setLookAndFeel(new MetalLookAndFeel());
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(InterfazTablas.class.getName()).log(Level.SEVERE, null, ex);
                }
                new InterfazTablas().setVisible(true);
    }
    
}
