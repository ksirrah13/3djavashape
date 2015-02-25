
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Created by kyle on 11/20/14.
 */
public class GUI extends JFrame implements ActionListener, KeyListener {

    //Set this to true to show panels as different colors
    static final boolean showPanels = false;

    //define const variables
    static final int width = 1000;
    static final int height = 600;
    static final double rotate = 10;

    //define member variables
    private JComboBox cbShapes;
    private JPanel sizingPane;
    private CardLayout cl;

    private Canvas canvas;
    private Shape builder, shape, cube, rect, pyr;

    private int cSize = 250;
    private int rWidth = 300, rHeight = 100, rDepth = 200;
    private int pBase = 200, pHeight = 200;

    private JPanel cubeP, rectP, pyrP;

    private JButton up, down, right, left, reset;


    public GUI() {
        //used as a blank object to acces the createShape functions
        builder = new Shape();

        createGUI();


    }


    private void createGUI() {
        //set default JFrame options
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(700, 550));
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //add keylistener to frame
        this.requestFocus();
        this.addKeyListener(this);

        JPanel container = new JPanel();
        BoxLayout bl = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setLayout(bl);
        container.setPreferredSize(new Dimension(width, height));

        if (showPanels) {
            container.setBackground(Color.gray);
        }

        container.add(createTop());
        container.add(createMain());

        this.getContentPane().add(container);
        this.pack();

    }

    private JPanel createTop() {
        JPanel top = new JPanel();
        top.setBorder(BorderFactory.createRaisedBevelBorder());

        JLabel title = new JLabel("My 3D Shape Generator");
        title.setFont(new Font(new JLabel().getFont().getFontName(), Font.BOLD, 32));

        top.add(title);
        top.setMaximumSize(new Dimension(500, 200));

        if (showPanels) {
            top.setBackground(Color.orange);
        }

        return top;
    }

    private JPanel createMain() {
        JPanel main = new JPanel();
        BoxLayout bl = new BoxLayout(main, BoxLayout.X_AXIS);
        main.setLayout(bl);

        if (showPanels) {
            main.setBackground(Color.yellow);
        }

        main.add(createLeft());
        main.add(createRight());

        return main;
    }

    private JPanel createLeft() {
        JPanel left = new JPanel();
        BoxLayout bl = new BoxLayout(left, BoxLayout.Y_AXIS);
        left.setLayout(bl);

        if (showPanels) {
            left.setBackground(Color.red);
        }

        left.setMaximumSize(new Dimension(800, 450));

        JPanel[] panes = createShapeSizingPanes();

        sizingPane = createShapeSizing(panes);
        cl = (CardLayout)sizingPane.getLayout();

        left.add(createShapeSelection());
        left.add(sizingPane);

        return left;

    }

    private JPanel createRight() {
        JPanel right = new JPanel();
        BoxLayout bl = new BoxLayout(right, BoxLayout.Y_AXIS);
        right.setLayout(bl);

        if (showPanels) {
            right.setBackground(Color.blue);
        }

        right.add(createCanvas());
        right.add(createControls());

        return right;

    }


    private JPanel createShapeSelection() {
        JPanel select = new JPanel();
        select.setBorder(BorderFactory.createTitledBorder("Select shape"));
        BoxLayout bl = new BoxLayout(select, BoxLayout.X_AXIS);
        select.setLayout(bl);

        if (showPanels) {
            select.setBackground(Color.green);
        }

        String[] shapes = {"Cube", "Rectangle", "Pyramid"};

        cbShapes = new JComboBox(shapes);
        cbShapes.addActionListener(this);
        cbShapes.setFocusable(false);

        select.add(cbShapes);

        return select;
    }

    private JPanel createShapeSizing(JPanel[] panes) {
        JPanel sizePane = new JPanel(new CardLayout());
        sizePane.setBorder(BorderFactory.createTitledBorder("Change dimensions"));

        sizePane.add(panes[0], "cube");
        sizePane.add(panes[1], "rect");
        sizePane.add(panes[2], "pyr");

        return sizePane;


    }

    private JPanel[] createShapeSizingPanes() {
        JPanel[] panes = new JPanel[3];

        cubeP = new JPanel();
        //set names for each panel to be used in listeners later
        cubeP.setName("cube");

        BoxLayout blc = new BoxLayout(cubeP, BoxLayout.Y_AXIS);
        cubeP.setLayout(blc);

        if (showPanels) {
            cubeP.setBackground(Color.cyan);
        }

        cubeP.add(Box.createRigidArea(new Dimension(0, 25)));
        cubeP.add(createDimension("Size", cSize));
        cubeP.add(Box.createVerticalGlue());


        rectP = new JPanel();
        rectP.setName("rect");

        BoxLayout blr = new BoxLayout(rectP, BoxLayout.Y_AXIS);
        rectP.setLayout(blr);

        if (showPanels) {
            rectP.setBackground(Color.cyan);
        }

        rectP.add(Box.createRigidArea(new Dimension(0, 25)));
        rectP.add(createDimension("Width", rWidth));
        rectP.add(createDimension("Height", rHeight));
        rectP.add(createDimension("Depth", rDepth));
        rectP.add(Box.createVerticalGlue());


        pyrP = new JPanel();
        pyrP.setName("pyr");

        BoxLayout blp = new BoxLayout(pyrP, BoxLayout.Y_AXIS);
        pyrP.setLayout(blp);

        if (showPanels) {
            pyrP.setBackground(Color.cyan);
        }

        pyrP.add(Box.createRigidArea(new Dimension(0, 25)));
        pyrP.add(createDimension("Base", pBase));
        pyrP.add(createDimension("Height", pHeight));
        pyrP.add(Box.createVerticalGlue());

        panes[0] = cubeP;
        panes[1] = rectP;
        panes[2] = pyrP;


        return panes;
    }

    private JPanel createDimension(String label, int value) {

        JPanel dim = new JPanel();
        dim.setBorder(BorderFactory.createLoweredBevelBorder());
        //use label as name to use with listeners later
        dim.setName(label);
        BoxLayout bl = new BoxLayout(dim, BoxLayout.Y_AXIS);
        dim.setLayout(bl);

        if (showPanels) {
            dim.setBackground(Color.gray);
        }

        JPanel dimTop = new JPanel();
        BoxLayout dtbl = new BoxLayout(dimTop, BoxLayout.X_AXIS);
        dimTop.setLayout(dtbl);

        if (showPanels) {
            dimTop.setBackground(Color.WHITE);
        }

        JLabel name = new JLabel(label);
        name.setMinimumSize(new Dimension(200, 100));

        JTextField size = new JTextField();
        size.setMaximumSize(new Dimension(150, 100));
        size.setEditable(false);
        size.setText(Integer.toString(value));
        size.setFocusable(false);

        dimTop.add(Box.createRigidArea(new Dimension(25, 0)));
        dimTop.add(name);
        dimTop.add(Box.createRigidArea(new Dimension(50, 0)));
        dimTop.add(size);

        JPanel dimBot = new JPanel();
        //set dimension wide enough to occupy parent panel
        dimBot.setMaximumSize(new Dimension(99999, 200));

        if (showPanels) {
            dimBot.setBackground(Color.BLACK);
        }

        JSlider slider = new JSlider(0, 500);
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(25);
        slider.setPaintTicks(true);
        slider.setValue(value);
        slider.setFocusable(false);

        slider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //get active slider
                JSlider source = (JSlider)e.getSource();
                //get parents parent container (panel created by createDimension)
                Container dim = source.getParent().getParent();
                //get top panel
                JPanel top = (JPanel)dim.getComponent(0);
                //get the textbox in that panel
                JTextField f = (JTextField)top.getComponent(3);
                //update text in box to match slider value
                f.setText(Integer.toString(source.getValue()));

                //update shape size accordingly
                changeShape(f);

            }
        });

        dimBot.add(slider);

        dim.add(dimTop);
        dim.add(dimBot);
        dim.add(Box.createRigidArea(new Dimension(0, 30)));

        return dim;

    }

    private JPanel createCanvas() {
        //create extended JPanel Canvas class for painted shapes
        canvas = new Canvas();
        canvas.setBorder(BorderFactory.createLoweredBevelBorder());

        //set default starting shape
        cube = builder.createCube(cSize, 0, 0);
        shape = cube;
        canvas.setS(cube);

        return canvas;
    }

    private void updateShape(Shape s) {

        //take passed shape and rotate before painting
        s.rotateX(s.getRotX());
        s.rotateY(s.getRotY());

        //pass shape to canvas to be painted
        canvas.setS(s);
        canvas.repaint();
    }

    private void changeShape(JTextField text) {
        //get the names of the dimension being altered and current active shape panel
        String dim = text.getParent().getParent().getName();
        String card = text.getParent().getParent().getParent().getName();

        //depending on which card is active and what dimension is being changed
        //update the member variable representing the shape dimensions
        //recreate the shape using new dimensions and pass to update to be painted
        if (dim == "Size") {
            cSize = Integer.parseInt(text.getText());
            cube = builder.createCube(cSize, cube.getRotX(), cube.getRotY());
            updateShape(cube);
        } else if (card == "rect") {
            if (dim == "Width") {
                rWidth = Integer.parseInt(text.getText());
            } else if (dim == "Height") {
                rHeight = Integer.parseInt(text.getText());
            } else if (dim == "Depth") {
                rDepth = Integer.parseInt(text.getText());
            }
            rect = builder.createRect(rWidth, rHeight, rDepth, rect.getRotX(), rect.getRotY());
            updateShape(rect);
        } else if (card == "pyr") {
            if (dim == "Base") {
                pBase = Integer.parseInt(text.getText());
            } else if (dim == "Height") {
                pHeight = Integer.parseInt(text.getText());
            }
            pyr = builder.createPyr(pBase, pHeight, pyr.getRotX(), pyr.getRotY());
            updateShape(pyr);

        }

    }


    private JPanel createControls() {
        JPanel controls = new JPanel();
        controls.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Rotate shape"));

        controls.setLayout(new GridLayout(3,3));

        if (showPanels) {
            controls.setBackground(Color.gray);
        }

        controls.setMaximumSize(new Dimension(250, 250));

        up = new JButton("UP");
        up.addActionListener(this);
        up.setFocusable(false);

        down = new JButton("DOWN");
        down.addActionListener(this);
        down.setFocusable(false);

        left = new JButton("LEFT");
        left.addActionListener(this);
        left.setFocusable(false);

        right = new JButton("RIGHT");
        right.addActionListener(this);
        right.setFocusable(false);

        reset = new JButton("Reset");
        reset.addActionListener(this);
        reset.setFocusable(false);

        //use empty jlabes to fill in empty grid spaces
        controls.add(new JLabel());
        controls.add(up);
        controls.add(new JLabel());

        controls.add(left);
        controls.add(reset);
        controls.add(right);

        controls.add(new JLabel());
        controls.add(down);
        controls.add(new JLabel());


        return controls;
    }


    private void rotateShape(Shape s, ActionEvent e) {

        //accepts a shape and updates it rotation values depending button pressed
        if (e.getSource().equals(up)) {
            s.setRotX(s.getRotX() + rotate);
        } else if (e.getSource().equals(down)) {
            s.setRotX(s.getRotX() - rotate);
        } else if (e.getSource().equals(left)) {
            s.setRotY(s.getRotY() + rotate);
        } else if (e.getSource().equals(right)) {
            s.setRotY(s.getRotY() - rotate);
        } else if (e.getSource().equals(reset)) {
            s.setRotX(0);
            s.setRotY(0);
        }

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cbShapes)) {

            //toggle visible cards and recreate shapes depending on selection
            switch (cbShapes.getSelectedIndex()) {
                case 0: {
                    cl.show(sizingPane, "cube");
                    if (cube == null) {
                        cube = builder.createCube(cSize, 0, 0);
                    }
                    cube = builder.createCube(cSize, cube.getRotX(), cube.getRotY());
                    shape = cube;
                    break;
                }
                case 1: {
                    cl.show(sizingPane, "rect");
                    if (rect == null) {
                        rect = builder.createRect(rWidth, rHeight, rDepth, 0, 0);
                    }
                    rect = builder.createRect(rWidth, rHeight, rDepth, rect.getRotX(), rect.getRotY());
                    shape = rect;
                    break;
                }
                case 2: {
                    cl.show(sizingPane, "pyr");
                    if (pyr == null) {
                        pyr = builder.createPyr(pBase, pHeight, 0, 0);
                    }
                    pyr = builder.createPyr(pBase, pHeight, pyr.getRotX(), pyr.getRotY());
                    shape = pyr;
                    break;
                }

            }

        }

        //pass newly created shape to canvas
        updateShape(shape);



       //handle cube rotation events
        //get current shape on the canvas and check what button pressed by passing to rotate
        if (cubeP.isVisible()) {
            cube = canvas.getS();
            rotateShape(cube, e);
            shape = builder.createCube(cSize, cube.getRotX(), cube.getRotY());
        } else if (rectP.isVisible()) {
            rect = canvas.getS();
            rotateShape(rect,e);
            shape = builder.createRect(rWidth, rHeight, rDepth, rect.getRotX(), rect.getRotY());
        } else if (pyrP.isVisible()) {
            pyr = canvas.getS();
            rotateShape(pyr, e);
            shape = builder.createPyr(pBase, pHeight, pyr.getRotX(), pyr.getRotY());
        }

        //pass updated shape to be painted
        updateShape(shape);

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        switch (e.getKeyCode()) {
            case 38: {
                up.doClick();
                break;
            }
            case 40: {
                down.doClick();
                break;
            }
            case 37: {
                left.doClick();
                break;
            }
            case 39: {
                right.doClick();
                break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}