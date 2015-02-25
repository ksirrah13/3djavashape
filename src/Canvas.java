import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/**
 * Created by kyle on 11/20/14.
 */
public class Canvas extends JPanel {

    private Image img;
    private Graphics2D g2;

    private Shape s;

    //blank default constructor
    public Canvas() {


    }

    @Override
    public void paintComponent(Graphics g) {

        //override paint to get graphic and change to 2D
        g2 = (Graphics2D)g;

        //if no image then paint canvas for first time
        if (img == null) {
            img = createImage(getSize().width, getSize().height);
        }

        //every time clear the canvas so that there isn't an overlap of images
        clear();

        //get edges and vertex point from shape for painting
        int [][] e = s.getEdge();
        Vertex [] v = s.getPoint();


        //loop through each vertex value and create an ellipse centered around that point
        g2.setColor(Color.cyan);
        for (int i=0; i<v.length; i++) {
            double x, y;
            double size=20;

            x = v[i].getX();
            y = v[i].getY();

            g2.draw(new Ellipse2D.Double(getWidth()/2+x-size*.5, getHeight()/2+y-size*.5,size,size));

        }


        //loop through each edge value
        //get each vertex on the ends of the edge
        //get x and y values for each vertex and draw a line between them
        g2.setColor(Color.black);
        for (int i=0; i<e.length; i++) {
            int o;
            double x1, x2, y1, y2;

            for (int j=0; j<e[i].length; j++) {
                x1 = v[i].getX();
                y1 = v[i].getY();

                o = e[i][j];

                x2 = v[o].getX();
                y2 = v[o].getY();

                g2.draw(new Line2D.Double(getWidth()/2 + x1, getHeight()/2 + y1, getWidth()/2 + x2, getHeight()/2 + y2));

            }

        }

    }

    public void clear() {
        g2.setPaint(Color.lightGray);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        repaint();
    }

    public Shape getS() {
        return s;
    }

    public void setS(Shape s) {
        this.s = s;
    }


}

