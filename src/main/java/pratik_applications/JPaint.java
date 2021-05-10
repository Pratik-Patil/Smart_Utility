package pratik_applications;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class JPaint extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    //  Declaration of Required Fields
    String[] myshape = {"Point", "Line", "Circle", "Rectangle", "Round Rect"};
    String text;

    JButton[] buttons = new JButton[11];
    JButton insertTextButton;

    JRadioButton fillRadioButton;
    JRadioButton drawRadioButton;
    JRadioButton eraserRadioButton;

    Cursor paintCursor;
    transient BufferedImage bufferedImage;
    transient BufferedImage cursorImage;

    Panel centerPanel;
    Panel bottomPanel;
    Panel leftPanel;

    boolean point = true;
    boolean circle;
    boolean line;
    boolean rect;
    boolean roundRect;
    boolean clear;
    boolean fix;

    Color selectedColor = Color.RED;
    JCheckBox xOrPaintModeCheckBox;
    transient Raster raster;
    int x = 0;
    int y = 0;
    int x1;
    int y1;
    int x2;
    int y2;
    int w;
    int h;
    int thinkness = 2;
    int fontSize;

    JComboBox<String> shapes;
    JLabel cords;

    public static void main(String[] args) {
        new JPaint();
    }

    public JPaint()  // Constructor
    {
        JPanel mainPanel = new JPanel(new BorderLayout());
        Border matteBorder = new MatteBorder(5, 5, 5, 5, Color.GREEN);
        JFrame frame = new JFrame("Paint Application");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int dimentionWidth = dimension.width;
        int dimentionHeight = dimension.height - 30;
        String title = "Paint Application By PRATIK PATIL";
        mainPanel.setBorder(BorderFactory.createTitledBorder(matteBorder, title));
        frame.setVisible(true);

        setLayout(new BorderLayout());
        centerPanel = new Panel();
        centerPanel.setBackground(Color.BLACK);
        add(centerPanel);
        bottomPanel = new Panel();
        bottomPanel.setBackground(Color.PINK);
        cords = new JLabel("Co-Ordinates   " + x + " , " + y);
        bottomPanel.add(cords);
        JLabel select = new JLabel("   Select Tool");
        bottomPanel.add(select);

        // ComboBox Section
        shapes = new JComboBox<>(myshape);
        shapes.addActionListener(this);
        bottomPanel.add(shapes);

        // RadioButton Section
        ButtonGroup group = new ButtonGroup();
        drawRadioButton = new JRadioButton("Draw", true);
        fillRadioButton = new JRadioButton("Fill");
        eraserRadioButton = new JRadioButton("Eraser");
        group.add(drawRadioButton);
        group.add(fillRadioButton);
        group.add(eraserRadioButton);
        drawRadioButton.addActionListener(this);
        bottomPanel.add(drawRadioButton);
        fillRadioButton.addActionListener(this);
        bottomPanel.add(fillRadioButton);
        eraserRadioButton.addActionListener(this);
        bottomPanel.add(eraserRadioButton);

        // CheckBox Section
        xOrPaintModeCheckBox = new JCheckBox("XOR Paint Mode");
        bottomPanel.add(xOrPaintModeCheckBox);
        xOrPaintModeCheckBox.addActionListener(this);

        // Drawing Text
        insertTextButton = new JButton("Insert Text");
        bottomPanel.add(insertTextButton);
        insertTextButton.addActionListener(this);

        // west_panel with Color Selection Buttons
        leftPanel = new Panel(new GridLayout(10, 1));
        JLabel j = new JLabel("Select Color", SwingConstants.CENTER);
        leftPanel.add(j);
        String[] mycolor = {"Red", "Green", "Blue", "Yellow", "Cyan", "Orange", "Magenta", "White", "My Favourite"};
        Color[] c1 = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.ORANGE, Color.MAGENTA, Color.WHITE, Color.RED};

        for (int i = 0; i < 9; i++) {

            buttons[i + 2] = new JButton(mycolor[i]);
            leftPanel.add(buttons[i + 2]);
            buttons[i + 2].setBackground(c1[i]);
            buttons[i + 2].addActionListener(this);
        }
        add(leftPanel, "West");

        // Clear All & Save Drawing Button
        buttons[0] = new JButton("Clear All");
        bottomPanel.add(buttons[0]);
        buttons[1] = new JButton("Save Drawing & Exit");
        bottomPanel.add(buttons[1]);
        buttons[0].addActionListener(this);
        buttons[1].addActionListener(this);
        add(bottomPanel, "South");
        centerPanel.addMouseMotionListener(this);
        centerPanel.addMouseListener(this);
        bottomPanel.addMouseListener(this);

        // JSlider Section for Thickness Variation
        JSlider slider = new JSlider(2, 17, 2);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(5);
        bottomPanel.add(new JLabel("Thickness"));
        bottomPanel.add(slider);

        ChangeListener sliderAction = changeEvent -> {
            JSlider jSlider = (JSlider) changeEvent.getSource();
            thinkness = jSlider.getValue();
            paint(centerPanel.getGraphics());
        };

        slider.addChangeListener(sliderAction);

        // Set BufferSize & Initialize BufferedImage
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        bufferedImage = new BufferedImage(this.getWidth(), this.getHeight() - 35, BufferedImage.TYPE_INT_RGB);

        // Set cursorimage of center_panel
        paintCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        centerPanel.setCursor(paintCursor);
        mainPanel.add(this);
        frame.setContentPane(mainPanel);
        frame.setSize(dimentionWidth, dimentionHeight);
        validate();
    }

    @Override
    public void paint(Graphics g) {

        w = this.getWidth();
        h = this.getHeight();

        if (clear)  // Clear Screen Code
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, w, h);
            bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        }
        else // Code to restore the Previous Drawing
        {
            if (bufferedImage.getHeight() != h - 35 || bufferedImage.getWidth() != w) {

                raster = bufferedImage.getData();
                bufferedImage = new BufferedImage(w, h - 35, BufferedImage.TYPE_INT_RGB);
                bufferedImage.getGraphics().setColor(Color.BLACK);
                bufferedImage.getGraphics().fillRect(0, 0, w, h);
                bufferedImage.setData(raster);
            }

            try {
                draw(bufferedImage.getGraphics());
            } catch (Exception e) {
                e.printStackTrace();
            }

            g.drawImage(bufferedImage, 0, 0, null);

        }
    }

    public void draw(Graphics g) {

        int x3 = Math.abs(x2 - x1), y3 = Math.abs(y2 - y1);
        Graphics2D g1 = (Graphics2D) g;
        g1.setStroke(new BasicStroke(thinkness));

        if (!point && !eraserRadioButton.isSelected()) {

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getWidth());
            bufferedImage.setData(raster);
        } else {
            raster = bufferedImage.getData();
        }

        if (xOrPaintModeCheckBox.isSelected() && !eraserRadioButton.isSelected() && !point && !line) {
            g.setXORMode(selectedColor);
        } else {
            g.setColor(selectedColor);
        }

        if (fix) {
            g.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, fontSize));
            g.drawString(text, x, y);
        }

        if (drawRadioButton.isSelected()) {
            if (circle) {
                if (x1 < x2 && y1 < y2) {
                    g.drawOval(x1, y1, x3, y3);
                } else if (x1 > x2 && y1 > y2) {
                    g.drawOval(x2, y2, x3, y3);
                } else if (y1 < y2) {
                    g.drawOval(x2, y1, x3, y3);
                } else if (y1 > y2) {
                    g.drawOval(x1, y2, x3, y3);
                }
            } else if (rect) {
                if (x1 < x2 && y1 < y2) {
                    g.drawRect(x1, y1, x3, y3);
                } else if (x1 > x2 && y1 > y2) {
                    g.drawRect(x2, y2, x3, y3);
                } else if (y1 < y2) {
                    g.drawRect(x2, y1, x3, y3);
                } else if (y1 > y2) {
                    g.drawRect(x1, y2, x3, y3);
                }
            } else if (roundRect) {
                if (x1 < x2 && y1 < y2) {
                    g.drawRoundRect(x1, y1, x3, y3, 20, 20);
                } else if (x1 > x2 && y1 > y2) {
                    g.drawRoundRect(x2, y2, x3, y3, 20, 20);
                } else if (y1 < y2) {
                    g.drawRoundRect(x2, y1, x3, y3, 20, 20);
                } else if (y1 > y2) {
                    g.drawRoundRect(x1, y2, x3, y3, 20, 20);
                }
            } else {
                g.drawLine(x1, y1, x2, y2);
            }
        } else if (fillRadioButton.isSelected()) {
            if (circle) {
                if (x1 < x2 && y1 < y2) {
                    g.fillOval(x1, y1, x3, y3);
                } else if (x1 > x2 && y1 > y2) {
                    g.fillOval(x2, y2, x3, y3);
                } else if (y1 < y2) {
                    g.fillOval(x2, y1, x3, y3);
                } else if (y1 > y2) {
                    g.fillOval(x1, y2, x3, y3);
                }
            } else if (rect) {
                if (x1 < x2 && y1 < y2) {
                    g.fillRect(x1, y1, x3, y3);
                } else if (x1 > x2 && y1 > y2) {
                    g.fillRect(x2, y2, x3, y3);
                } else if (y1 < y2) {
                    g.fillRect(x2, y1, x3, y3);
                } else if (y1 > y2) {
                    g.fillRect(x1, y2, x3, y3);
                }
            } else if (roundRect) {
                if (x1 < x2 && y1 < y2) {
                    g.fillRoundRect(x1, y1, x3, y3, 20, 20);
                } else if (x1 > x2 && y1 > y2) {
                    g.fillRoundRect(x2, y2, x3, y3, 20, 20);
                } else if (y1 < y2) {
                    g.fillRoundRect(x2, y1, x3, y3, 20, 20);
                } else if (y1 > y2) {
                    g.fillRoundRect(x1, y2, x3, y3, 20, 20);
                }
            } else {
                g.drawLine(x1, y1, x2, y2);
            }
        }//end if for fill option selected
        else if (eraserRadioButton.isSelected()) {
            g.setColor(Color.BLACK);
            g.fillRect(x2 - 15, y2 - 15, 30, 30);
        }

        if (point) {
            x1 = x2;
            y1 = y2;
        }
    }//end of draw

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == insertTextButton) // Insert Text
        {
            x = 0;
            y = 0;
            fix = true;
            text = JOptionPane.showInputDialog("Enter the Text: ");

            if (text != null) {
                fontSize = Integer.parseInt(JOptionPane.showInputDialog("Enter Font Size: "));
                x = Integer.parseInt(JOptionPane.showInputDialog("Enter X-CoOrdinate: "));
                y = Integer.parseInt(JOptionPane.showInputDialog("Enter Y-CoOrdinate: "));
            }

            paint(centerPanel.getGraphics());
            raster = bufferedImage.getData();
            fix = false;
            paint(centerPanel.getGraphics());
        }

        if (actionEvent.getSource() == buttons[0]) // Clear All
        {
            clear = true;
            paint(centerPanel.getGraphics());
            clear = false;
        }

        if (actionEvent.getSource() == buttons[2]) {
            selectedColor = Color.RED;
        }

        if (actionEvent.getSource() == buttons[3]) {
            selectedColor = Color.GREEN;
        }

        if (actionEvent.getSource() == buttons[4]) {
            selectedColor = Color.BLUE;
        }

        if (actionEvent.getSource() == buttons[5]) {
            selectedColor = Color.YELLOW;
        }

        if (actionEvent.getSource() == buttons[6]) {
            selectedColor = Color.CYAN;
        }

        if (actionEvent.getSource() == buttons[7]) {
            selectedColor = Color.ORANGE;
        }

        if (actionEvent.getSource() == buttons[8]) {
            selectedColor = Color.MAGENTA;
        }

        if (actionEvent.getSource() == buttons[9]) {
            selectedColor = Color.WHITE;
        }

        if (actionEvent.getSource() == buttons[10]) // My Favourite Color
        {
            selectedColor = JColorChooser.showDialog(JPaint.this, "Select Any Color", selectedColor);
        }

        buttons[10].setBackground(selectedColor);

        if (actionEvent.getSource() == buttons[1]) // Save Drawing & Exit
        {
            FileDialog fileDialog = new FileDialog((Dialog) null, "Save File ", FileDialog.SAVE);
            fileDialog.setVisible(true);
            String fileName = fileDialog.getDirectory() + fileDialog.getFile();

            if (fileDialog.getFile() != null) {

                FileOutputStream fileOutputStream = null;

                try {
                    fileOutputStream = new FileOutputStream(fileName);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                try {
                    assert fileOutputStream != null;
                    ImageIO.write(bufferedImage, "gif", fileOutputStream);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Drawing is Saved To File " + fileDialog.getFile());
                JOptionPane.showMessageDialog(null, "Now the Application will be Terminated");
                System.exit(0);
            }
        }
        if (actionEvent.getSource() == shapes) {

            point = line = circle = rect = roundRect = false;

            if (shapes.getSelectedItem() == myshape[0]) {
                point = true;
            }

            if (shapes.getSelectedItem() == myshape[1]) {
                line = true;
            }

            if (shapes.getSelectedItem() == myshape[2]) {
                circle = true;
            }

            if (shapes.getSelectedItem() == myshape[3]) {
                rect = true;
            }

            if (shapes.getSelectedItem() == myshape[4]) {
                roundRect = true;
            }
        }//end of if statement for shapes

        if (actionEvent.getSource() == eraserRadioButton) {

            cursorImage = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
            cursorImage.getGraphics().drawRect(0, 0, 30, 30);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            paintCursor = toolkit.createCustomCursor(cursorImage, new Point(15, 15), "mycursor");
        }

        if (actionEvent.getSource() == fillRadioButton || actionEvent.getSource() == drawRadioButton) {
            paintCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        }
        centerPanel.requestFocus();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == bottomPanel) {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if (me.getSource() == centerPanel) {
            centerPanel.setCursor(paintCursor);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {
        x2 = me.getX();
        y2 = me.getY();
        paint(centerPanel.getGraphics());
        cords.setText("Co-Ordinates   " + me.getX() + " , " + me.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        x1 = me.getX();
        y1 = me.getY();
        x2 = x1;
        y2 = y1;
        paint(centerPanel.getGraphics());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cords.setText("Co-Ordinates   " + e.getX() + " , " + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!point) {
            raster = bufferedImage.getData();
        }
    }

}

