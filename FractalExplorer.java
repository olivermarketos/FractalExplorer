/**
 * A Fractal Explorer created in Java 
 * Followed from youtube tutorial by William Fiset
 * @author Oliver Marketos, olivermarketos@gmail.com
 **/

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

public class FractalExplorer extends JFrame {

    static final int WIDTH= 1000;
    static final int HEIGHT = 600;

    public FractalExplorer(){
        setInitialGUIProperties();
    }

    public void setInitialGUIProperties(){
        this.setTitle("Fractal Explorer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }




    public static void main(String[] args) {
        new FractalExplorer();
    }
}
