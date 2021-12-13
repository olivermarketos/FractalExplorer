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

    Canvas canvas;
    BufferedImage fractalImage;

    public FractalExplorer(){
        setInitialGUIProperties();
        addCanvas();
        this.setVisible(true);
    }
    private void addCanvas(){
        canvas = new Canvas();
        fractalImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        canvas.setVisible(true);
        this.add(canvas, BorderLayout.CENTER);

    }

    public void setInitialGUIProperties(){
        this.setTitle("Fractal Explorer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        new FractalExplorer();
    }

    private class Canvas extends JPanel{

        public Dimension getPreferredSize(){
            return new Dimension(WIDTH, HEIGHT);
        }

        public void paintComponent(Graphics drawingObj){
            drawingObj.drawImage(fractalImage, 0, 0, null);
        }
    }
}
