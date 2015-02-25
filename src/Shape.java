/**
 * Created by kyle on 11/20/14.
 */
public class Shape {


    private Vertex [] point;
    private int [][] edge;
    private double rotX=0, rotY=0;


    public int[][] getEdge() {
        return edge;
    }

    public Vertex[] getPoint() {
        return point;
    }


    public double getRotX() {
        return rotX;
    }

    public double getRotY() {
        return rotY;
    }

    public void setRotX(double rotX) {
        this.rotX = rotX;
    }

    public void setRotY(double rotY) {
        this.rotY = rotY;
    }


    //create a shape using array of point and edges
    public Shape(Vertex [] points, int[][] edges) {
        this.point = points;
        this.edge = edges;
        this.rotX = 0;
        this.rotY = 0;

    }

    //default construct, used as the shape builder
    public Shape() {
        this.rotX = 0;
        this.rotY = 0;

    }

    //create a cube by setting the edges and vertices appropriately and storing rotation values
    public Shape createCube(int s, double x, double y) {
        this.point = new Vertex[8];
        this.edge = new int[][]{
                {1, 3, 4}, {0, 2, 5}, {1, 3, 6}, {0, 2, 7}, {0, 5, 7}, {1, 4, 6}, {2, 5, 7}, {3, 4, 6}
        };

        double h = s / 2;

        point[0] = new Vertex(-h, -h, h);
        point[1] = new Vertex(-h, h, h);
        point[2] = new Vertex(h, h, h);
        point[3] = new Vertex(h, -h, h);
        point[4] = new Vertex(-h, -h, -h);
        point[5] = new Vertex(-h, h, -h);
        point[6] = new Vertex(h, h, -h);
        point[7] = new Vertex(h, -h, -h);


        Shape c = new Shape(point, edge);

        c.setRotX(x);
        c.setRotY(y);


        return c;
    }

    //create a rectangluar prism by setting the edges and vertices appropriately and storing rotation values
    public Shape createRect(int w, int h, int d, double x, double y) {
        this.point = new Vertex[8];
        this.edge = new int[][]{
                {1, 3, 4}, {0, 2, 5}, {1, 3, 6}, {0, 2, 7}, {0, 5, 7}, {1, 4, 6}, {2, 5, 7}, {3, 4, 6}
        };

        double W = w / 2, H = h / 2, D = d / 2;

        point[0] = new Vertex(-W, -H, D);
        point[1] = new Vertex(-W, H, D);
        point[2] = new Vertex(W, H, D);
        point[3] = new Vertex(W, -H, D);
        point[4] = new Vertex(-W, -H, -D);
        point[5] = new Vertex(-W, H, -D);
        point[6] = new Vertex(W, H, -D);
        point[7] = new Vertex(W, -H, -D);


        Shape r = new Shape(point, edge);

        r.setRotX(x);
        r.setRotY(y);


        return r;
    }

    //create a pyramid by setting the edges and vertices appropriately and storing rotation values
    public Shape createPyr(int b, int h, double x, double y) {
        this.point = new Vertex[5];
        this.edge = new int[][]{
                {1, 2, 3, 4}, {0, 2, 4}, {0, 1, 3}, {0, 2, 4}, {0, 1, 3}
        };

        double B = b / 2;
        double H = h / 2;

        point[0] = new Vertex(0, -H, 0);
        point[1] = new Vertex(B, H, B);
        point[2] = new Vertex(-B, H, B);
        point[3] = new Vertex(-B, H, -B);
        point[4] = new Vertex(B, H, -B);

        Shape p = new Shape(point, edge);

        p.setRotX(x);
        p.setRotY(y);


        return p;
    }


    //rotate shape around z axis by updating x,y,z vertex coords
    //see comments for this method to understand X and Y rotate methods also
    public void rotateZ(double angle) {

        //get shape objects array of vertices
        Vertex [] v = this.getPoint();

        //convert angle from degrees to radians
        angle = angle/180*Math.PI;

        //store sin and cos value for simplified code
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);


        //loop through each vertices and using formula
        //calculate new position of vertices based on rotation
        for (int i=0; i<v.length; i++) {
            double x, y;

            x = v[i].getX();
            y = v[i].getY();

            v[i].setX(x*cos-y*sin);
            v[i].setY(y*cos+x*sin);

        }

    }

    //rotate shape around x axis by updating x,y,z vertex coords
    public void rotateY(double angle) {

        Vertex [] v = this.getPoint();

        angle = angle/180*Math.PI;


        double sin = Math.sin(angle);
        double cos = Math.cos(angle);


        for (int i=0; i<v.length; i++) {
            double x, y;

            x = v[i].getX();
            y = v[i].getZ();

            v[i].setX(x*cos-y*sin);
            v[i].setZ(y*cos+x*sin);

        }

    }

    //rotate shape around x axis by updating x,y,z vertex coords
    public void rotateX(double angle) {

        Vertex [] v = this.getPoint();

        angle = angle/180*Math.PI;

        double sin = Math.sin(angle);
        double cos = Math.cos(angle);


        for (int i=0; i<v.length; i++) {
            double x, y;

            x = v[i].getY();
            y = v[i].getZ();

            v[i].setY(x*cos-y*sin);
            v[i].setZ(y*cos+x*sin);

        }

    }

}
