/**
 * A Fractal Explorer created in Java 
 * Followed from youtube tutorial by William Fiset
 * @author Oliver Marketos, olivermarketos@gmail.com
 **/

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

// -------------------------------------------------------------------
    public FractalExplorer(){
        setInitialGUIProperties();
        addCanvas();
        canvas.addKeyStrokeEvents();
        updateFractal();
        this.setVisible(true);
    }

// -------------------------------------------------------------------
    /**
	 * Updates the fractal by computing the number of iterations
	 * for each point in the fractal and changing the color
	 * based on that.
	 **/

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

// -------------------------------------------------------------------
    /** Returns a posterized color based off of the iteration count
	    of a given point in the fractal **/
    
    private int makeColor( int iterCount){

		// int color = 0b011011100001100101101000; 
		// int mask  = 0b000000000000010101110111; 
        // int shiftMag = iterCount / 13;
		
		if (iterCount == MAX_ITER) 
			return Color.BLACK.getRGB();

        return Color.HSBtoRGB((float)iterCount/MAX_ITER, 0.5f, 1);
	    // return color | (mask << shiftMag);
    }

// -------------------------------------------------------------------
    private double getXPos(double x){
        return x/zoomFactor + topLeftX;
    }
// -------------------------------------------------------------------
    private double getYPos(double y){
        return y/zoomFactor - topLeftY;
    }

// -------------------------------------------------------------------
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

        // Modulus (distance) formula:
		// ???(a?? + b??) <= 2.0
		// a?? + b?? <= 4.0

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

// -------------------------------------------------------------------
    private void addCanvas(){
        canvas = new Canvas();
        fractalImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        canvas.setVisible(true);
        this.add(canvas, BorderLayout.CENTER);

    }

// -------------------------------------------------------------------
    public void setInitialGUIProperties(){
        this.setTitle("Fractal Explorer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

    }

// -------------------------------------------------------------------
    private void adjustZoom(double newX, double newY, double newZoomFactor){

        topLeftX += newX/zoomFactor;
        topLeftY -= newY/zoomFactor;

        zoomFactor = newZoomFactor;

        topLeftX -= (WIDTH/2)/ zoomFactor;
        topLeftY += (HEIGHT/2)/ zoomFactor;

        updateFractal();
    }

// -------------------------------------------------------------------
private void moveUp() {
    double curHeight = HEIGHT / zoomFactor;
    topLeftY += curHeight / 6;
    updateFractal();
} // moveUp
// -------------------------------------------------------------------
private void moveDown() {
    double curHeight = HEIGHT / zoomFactor;
    topLeftY -= curHeight / 6;
    updateFractal();
} // moveDown
// -------------------------------------------------------------------
private void moveLeft() {
    double curWidth = WIDTH / zoomFactor;
    topLeftX -= curWidth / 6;
    updateFractal();
} // moveLeft
// -------------------------------------------------------------------
private void moveRight() {
    double curWidth = WIDTH / zoomFactor;
    topLeftX += curWidth / 6;
    updateFractal();
} // moveRight
// -------------------------------------------------------------------
    private class Canvas extends JPanel implements MouseListener{

        public Canvas(){
            addMouseListener(this);
        }

        @Override public Dimension getPreferredSize(){
            return new Dimension(WIDTH, HEIGHT);
        }

        public void paintComponent(Graphics drawingObj){
            drawingObj.drawImage(fractalImage, 0, 0, null);
        }

        @Override public void mousePressed(MouseEvent mouse){

            double x = (double) mouse.getX();
            double y = (double) mouse.getY();

            switch (mouse.getButton()){

                // left
                case MouseEvent.BUTTON1:
                    adjustZoom(x,y, zoomFactor*2);
                    break;
                
                // right
                case MouseEvent.BUTTON3:
                    adjustZoom(x,y, zoomFactor/2);
                    break;
            }
        }

        @Override public void mouseReleased(MouseEvent mouse){ }
		@Override public void mouseClicked(MouseEvent mouse) { }
		@Override public void mouseEntered(MouseEvent mouse) { }
		@Override public void mouseExited (MouseEvent mouse) { }

        public void addKeyStrokeEvents(){
            KeyStroke w = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);
            KeyStroke a = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
            KeyStroke s = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
            KeyStroke d = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);

            Action wPressed = new AbstractAction(){
                public void actionPerformed(ActionEvent e){
                    moveUp();
                }
            }; 
            Action aPressed = new AbstractAction(){
                public void actionPerformed(ActionEvent e){
                    moveLeft();
                }
            } ;
            Action sPressed = new AbstractAction(){
                public void actionPerformed(ActionEvent e){
                    moveDown();
                }
            } ;
            Action dPressed = new AbstractAction(){
                public void actionPerformed(ActionEvent e){
                    moveRight();
                }
            } ;

			this.getInputMap().put( w, "w_key" );
			this.getInputMap().put( a, "a_key" );
			this.getInputMap().put( s, "s_key" );
			this.getInputMap().put( d, "d_key" );
			
			this.getActionMap().put( "w_key", wPressed );
			this.getActionMap().put( "a_key", aPressed );
			this.getActionMap().put( "s_key", sPressed );
			this.getActionMap().put( "d_key", dPressed );
			

        }
		
    }

// -------------------------------------------------------------------
    public static void main(String[] args) {
        new FractalExplorer();
    }
}
