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

    static final int WIDTH= 600;
    static final int HEIGHT = 600;

    Canvas canvas;
    BufferedImage fractalImage;

    static final int MAX_ITER= 200;

    static final double DEFAULT_ZOOM = 100.0;
    static final double DEFAULT_TOP_LEFT_X = -3.0;
    static final double DEFAULT_TOP_LEFT_Y = 3.0; 

    double zoomFactor = DEFAULT_ZOOM;
    double topLeftY = DEFAULT_TOP_LEFT_Y;
    double topLeftX = DEFAULT_TOP_LEFT_X;

    public FractalExplorer(){
        setInitialGUIProperties();
        addCanvas();
        updateFractal();
        this.setVisible(true);
    }

    public void updateFractal(){

        for (int x = 0; x< WIDTH; x++){
            for(int y= 0; y< HEIGHT; y++){

                double c_r = getXPos(x);
                double c_i = getYPos(y);

                int iterCount = computeIterations(c_r, c_i);

                int pixelColor = makeColor(iterCount);
                fractalImage.setRGB(x,y, pixelColor);
            }
        }
        canvas.repaint();
    }

    private int makeColor( int iterCount){

		// int color = 0b011011100001100101101000; 
		// int mask  = 0b000000000000010101110111; 
		// int shiftMag = iterCount / 13;
		
		if (iterCount == MAX_ITER) 
			return Color.BLACK.getRGB();
	
        return Color.BLUE.getRGB();
	        // return color | (mask << shiftMag);
    }

    private double getXPos(double x){
        return x/zoomFactor + topLeftX;
    }

    private double getYPos(double y){
        return y/zoomFactor - topLeftY;
    }

    private int computeIterations(double c_r, double c_i){

            /*
            let c = c_r + c_i
            let z = z_r + z_i

            z'  = z*z + c
                = (z_r + z_i)(z_r + z_i) + c_r + c_i
                = z_r^2 + 2*z_r*z_i - z_i^2 + c_r + c_i

                z_r' =  z_r^2 - z_i^2 + c_r
                z_i' =  2*z_r*z_i + c_i

            */

        double z_r = 0.0;
        double z_i = 0.0;
        
        int iterCount = 0;

        // sqr_root(a^2 + b^2) <= 2
        // (a^2 + b^2) <= 4

        while (z_r*z_r + z_i*z_i <=4.0){
            
            double z_r_tmp = z_r;

            z_r = z_r*z_r - z_i*z_i + c_r;
            z_i = 2*z_r_tmp*z_i + c_i;

            // point inside mandlebrot set
            if ( iterCount >= MAX_ITER){
                return MAX_ITER;
            }

            // point was outside mandlebrot set
            iterCount++;

        }
        return iterCount;
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
