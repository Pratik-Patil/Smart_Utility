// Pratik Softwares
package pratik_applications;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class SmartUtility extends JPanel implements ActionListener {

    transient Image image;
    JButton[] buttons = new JButton[6];
    Dimension screenSizeDimension;
    String[] appNames = {"JavaPad", "JCalculator", "JPaint", "AnalogClock", "ScrollBarMagic", "BouncingBall"};
    Color[] colors = {Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.PINK, Color.ORANGE};

    public SmartUtility() {

        super(new BorderLayout());

        String mainScreenMsg = "<HTML>Pratik Softwares<BR><BR>Select Your Favourite";
        JFrame mainFrame = new JFrame("Pratik Softwares (Copyright Protected)");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        URL imageUrl = getClass().getClassLoader().getResource("Images/z.jpg");
        assert imageUrl != null;
        image = new ImageIcon(imageUrl).getImage();

        Dimension dimension = new Dimension(image.getWidth(null), image.getHeight(null));
        screenSizeDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(dimension);
        mainFrame.setLocation(screenSizeDimension.width / 4, screenSizeDimension.height / 4);

        JLabel mainScreenMsgLabel = new JLabel(mainScreenMsg);
        mainScreenMsgLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 60));
        mainScreenMsgLabel.setForeground(new Color(255, 0, 155));
        add(mainScreenMsgLabel, BorderLayout.NORTH);
        JPanel jPanel = new JPanel(new GridLayout(2, 3));

        for (int i = 0; i < 6; i++) {
            buttons[i] = new JButton(appNames[i]);
            buttons[i].addActionListener(this);
            buttons[i].setBackground(colors[i]);
            buttons[i].setFont(new Font("TIMES NEW ROMAN", Font.BOLD | Font.ITALIC, 25));
            jPanel.add(buttons[i]);
        }

        add(jPanel, BorderLayout.SOUTH);
        mainFrame.add(this);
        mainFrame.setSize(screenSizeDimension.width / 2, screenSizeDimension.height / 2);
        mainFrame.setResizable(false);
    }

    public static void main(String[] args) {
        new SmartUtility();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == buttons[0]) {
            new JavaPad();
        }
        if (source == buttons[1]) {
            new JCalculator();
        }
        if (source == buttons[2]) {
            new JPaint();
        }
        if (source == buttons[3]) {
            new AnalogClock();
        }
        if (source == buttons[4]) {
            new ScrollBarMagic();
        }
        if (source == buttons[5]) {
            new BouncingBall();
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(image, 0, 0, screenSizeDimension.width / 2, screenSizeDimension.height / 2, null);
    }
}

class JavaPad extends JPanel {
    // Declaration of Required Fields
    JCheckBox boldCheckBox;
    JCheckBox italicCheckBox;
    JButton sizeButton;

    JComboBox<String> fontComboBox;
    JFrame frame;

    JTextArea textArea;
    JMenuItem setTextColorMenuItem;
    JMenuItem setPadColorMenuItem;
    JMenuItem aboutMenuItem;

    int fontStyle = Font.PLAIN;
    int fontSize = 25;

    transient Clipboard clipboard = getToolkit().getSystemClipboard();
    JCheckBoxMenuItem wordWrapCheckBoxMenuItem;
    String fileName;
    String javaPadTitle = "  - JavaPad_by_Pratik.Patil";
    String fontName = "TIMES NEW ROMAN";

    String aboutTextMsg = "<html><big>JavaPad by Pratik_Patil</big><hr><hr>"
                    + "Your Comments as well as bug reports are very welcome..<p align=center><br>"
                    + "<strong>For any Kind of help & Information</strong><br>Kindly Contact at<br><br>"
                    + "<hr><em><big>pratik05051991@gmail.com</big></em><hr><html>"
                    + "<strong>Thanx for using Javapad</strong><br>";

    String aboutTextMsgShort = "Dedicated to Pratik Patil";
    String[] fontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    public JavaPad() {

        super(new GridLayout(1, 1));
        textArea = new JTextArea();
        frame = new JFrame("UnTitled" + javaPadTitle);
        add(new JScrollPane(textArea));
        textArea.setFont(new Font(fontName, fontStyle, fontSize));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newMenuItem = new JMenuItem("New");
        fileMenu.add(newMenuItem);

        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edit");

        JMenuItem cutMenuItem = new JMenuItem("Cut");
        editMenu.add(cutMenuItem);

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        editMenu.add(copyMenuItem);

        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        editMenu.add(pasteMenuItem);

        JMenuItem selectALLMenuItem = new JMenuItem("Select All");
        editMenu.add(selectALLMenuItem);

        menuBar.add(editMenu);
        JMenu formatMenuItem = new JMenu("Format");

        wordWrapCheckBoxMenuItem = new JCheckBoxMenuItem("Word Wrap", false);
        formatMenuItem.add(wordWrapCheckBoxMenuItem);

        setTextColorMenuItem = new JMenuItem("Set Text Color");
        formatMenuItem.add(setTextColorMenuItem);

        setPadColorMenuItem = new JMenuItem("Set Pad Color");
        formatMenuItem.add(setPadColorMenuItem);
        menuBar.add(formatMenuItem);

        JMenu helpMenu = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About JavaPad");

        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);

        menuBar.add(new JLabel("              Select Font__"));
        fontComboBox = new JComboBox<>(fontFamilyNames);
        fontComboBox.setSelectedIndex(149);
        menuBar.add(fontComboBox);

        boldCheckBox = new JCheckBox("Bold");
        menuBar.add(boldCheckBox);
        italicCheckBox = new JCheckBox("Italic");
        menuBar.add(italicCheckBox);

        sizeButton = new JButton("Set Font Size");
        menuBar.add(sizeButton);

        frame.setJMenuBar(menuBar);

        // Registration of Components with their respective listeners
        newMenuItem.addActionListener(new New());
        openMenuItem.addActionListener(new Open());
        saveMenuItem.addActionListener(new Save());
        exitMenuItem.addActionListener(new Exit());

        copyMenuItem.addActionListener(new Cut());
        copyMenuItem.addActionListener(new Copy());
        pasteMenuItem.addActionListener(new Paste());
        selectALLMenuItem.addActionListener(new SelectAll());

        setTextColorMenuItem.addActionListener(new FormatSet());
        setPadColorMenuItem.addActionListener(new FormatSet());

        aboutMenuItem.addActionListener(new FormatSet());

        boldCheckBox.addActionListener(new FontSet());
        italicCheckBox.addActionListener(new FontSet());
        fontComboBox.addActionListener(new FontSet());
        sizeButton.addActionListener(new FontSet());
        wordWrapCheckBoxMenuItem.addItemListener(new FormatSet());

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int result = JOptionPane.showConfirmDialog(null,
                        "Save File?", "Select Yes or No", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    FileDialog fileDialog = new FileDialog((Dialog) null, "Save File", FileDialog.SAVE);
                    fileDialog.setVisible(true);
                    Save save = new Save();

                    if (fileDialog.getFile() != null) {
                        fileName = fileDialog.getDirectory() + fileDialog.getFile();
                        save.writeFile();
                        JOptionPane.showMessageDialog(null, "File is Saved");
                    }
                }
            }//windowClosing() method closed
        }); // addWindowListener() method closed
        frame.setVisible(true);
        frame.add(this);
        frame.setSize(700, 500);
    }

    public static void main(String[] args) {
        new JavaPad();
    }

    class FontSet implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            fontName = (String) fontComboBox.getSelectedItem();
            fontStyle = Font.PLAIN;

            if (e.getSource() == boldCheckBox || e.getSource() == italicCheckBox) {

                if (boldCheckBox.isSelected()) {
                    fontStyle = Font.BOLD;
                }

                if (italicCheckBox.isSelected()) {
                    fontStyle = Font.ITALIC;
                }

                if (boldCheckBox.isSelected() && italicCheckBox.isSelected()) {
                    fontStyle = Font.BOLD + Font.ITALIC;
                }
            }
            if (e.getSource() == sizeButton) {
                fontSize = Integer.parseInt(JOptionPane.showInputDialog("Enter Font Size"));
            }
            textArea.setFont(new Font(fontName, fontStyle, fontSize));
        }
    }// FontSet closed

    class FormatSet implements ActionListener, ItemListener {

        public void itemStateChanged(ItemEvent itemEvent) {
            textArea.setLineWrap(wordWrapCheckBoxMenuItem.getState());
        }

        public void actionPerformed(ActionEvent actionEvent) {

            if (actionEvent.getSource() == setTextColorMenuItem) {
                Color c1 = JColorChooser.showDialog(JavaPad.this, "Select Text Color", Color.BLACK);
                textArea.setForeground(c1);
            }

            if (actionEvent.getSource() == setPadColorMenuItem) {
                Color c2 = JColorChooser.showDialog(JavaPad.this, "Select Pad Color", Color.WHITE);
                textArea.setBackground(c2);
            }

            if (actionEvent.getSource() == aboutMenuItem) {
                JOptionPane.showMessageDialog(JavaPad.this, aboutTextMsg, aboutTextMsgShort, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }// FormatSet closed

    class New implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.setText(" ");
            frame.setTitle("UnTitled" + javaPadTitle);
        }
    }

    class Open implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FileDialog fd = new FileDialog((Dialog) null, "Select File", FileDialog.LOAD);
            fd.setVisible(true);

            if (fd.getFile() != null) {
                fileName = fd.getDirectory() + fd.getFile();
                frame.setTitle(fileName + javaPadTitle);
                readFile();
            }
            textArea.requestFocus();
        }

        void readFile() {
            StringBuilder stringBuilder = new StringBuilder();

            try (Scanner sc = new Scanner(new File(fileName))) {
                String line = "";
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    stringBuilder.append(line).append("\n");
                }
                textArea.setText(stringBuilder.toString());
            } catch (Exception ignored) {
            }

        } // ReadFile() method closed
    }// Open closed

    class Save implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FileDialog fd = new FileDialog((Dialog) null, "Save File", FileDialog.SAVE);
            fd.setVisible(true);
            if (fd.getFile() != null) {
                fileName = fd.getDirectory() + fd.getFile();
                writeFile();
            }
        }

        void writeFile() {
            try (DataOutputStream d = new DataOutputStream(new FileOutputStream(fileName))) {
                frame.setTitle(fileName + javaPadTitle);
                String line = textArea.getText();
                try (Scanner br = new Scanner(line)) {
                    while (br.hasNextLine()) {
                        line = br.nextLine();
                        d.writeBytes(line + "\r\n");
                    }
                }
            } catch (Exception ignored) {
            }
            textArea.requestFocus();
        }
    }// Save closed

    class Exit implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            frame.dispose();
        }
    }

    class Cut implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String sel = textArea.getSelectedText();
            StringSelection ss = new StringSelection(sel);
            clipboard.setContents(ss, ss);
            textArea.replaceRange(" ", textArea.getSelectionStart(), textArea.getSelectionEnd());
        }
    }

    class Copy implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String sel = textArea.getSelectedText();
            StringSelection clipString = new StringSelection(sel);
            clipboard.setContents(clipString, clipString);
        }
    }

    class Paste implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            Transferable clipContents = clipboard.getContents(JavaPad.this);
            String sel = null;

            try {
                sel = (String) clipContents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException unsupportedFlavorException) {
                unsupportedFlavorException.printStackTrace();
            }
            textArea.replaceRange(sel, textArea.getSelectionStart(), textArea.getSelectionEnd());

        }
    } // paste closed

    class SelectAll implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.selectAll();
        }
    }// SelectAll closed
} // JavaPad closed

class JCalculator extends JFrame implements ActionListener {
    // Declaration of Required Fields
    int result = 0;
    int previousValue = 0;
    int currentValue = 0;
    JButton[] buttons = new JButton[16];
    ImageIcon[] imageIcons = new ImageIcon[16];
    JTextField textField = new JTextField(10);
    String imageDirectory = "Images/";
    String imageExtension = ".jpg";
    String imageFilePath;
    String[] buttonLabels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "=", "Clear"};
    String operator = "";
    String[] imageNames = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "plus", "minus", "mult", "div", "equal", "clear"};

    public JCalculator() {
        setResizable(false);
        setVisible(true);
        setTitle("Java Calculator by Pratik_Patil");
        setLayout(new BorderLayout());
        add(textField, "North");
        textField.setText("0");
        JPanel calculatorPanel = new JPanel(new GridLayout(4, 4));

        for (int i = 0; i < 16; i++) {

            imageFilePath = imageDirectory + imageNames[i] + imageExtension;
            URL imageFileUrl = getClass().getClassLoader().getResource(imageFilePath);
            assert imageFileUrl != null;
            imageIcons[i] = new ImageIcon(imageFileUrl);
            buttons[i] = new JButton(buttonLabels[i], imageIcons[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 1));
            calculatorPanel.add(buttons[i]);
            buttons[i].addActionListener(this);
        }
        add(calculatorPanel, "Center");
        setSize(525, 525);
    }

    public static void main(String[] args) {
        new JCalculator();
    }

    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();
        currentValue = Integer.parseInt(textField.getText());

        switch (key) {

            case "Clear":
                textField.setText("0");
                previousValue = 0;
                currentValue = 0;
                result = 0;
                operator = "";
                break;

            case "=":
                result = 0;
                switch (operator) {
                    case "+":
                        result = previousValue + currentValue;
                        break;
                    case "-":
                        result = previousValue - currentValue;
                        break;
                    case "*":
                        result = previousValue * currentValue;
                        break;
                    case "/":
                        result = previousValue / currentValue;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + operator);
                }
                textField.setText(String.valueOf(result));
                break;

            case "+":
            case "-":
            case "*":
            case "/":
                previousValue = currentValue;
                operator = key;
                textField.setText("0");
                break;

            default:
                textField.setText(String.valueOf(currentValue * 10 + Integer.parseInt(key)));
                break;
        }
    }
}

class JPaint extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    //  Declaration of Required Fields
    String[] myshape = {"Point", "Line", "Circle", "Rectangle", "Round Rect"};
    String text;
    JButton[] buttons = new JButton[11];
    JButton insertTextButton;
    JRadioButton fill, draw, eraser;
    Cursor cursor;
    BufferedImage bufferedImage, cursorImage;
    JPanel centerPanel;
    Panel southPanel, westPanel;
    boolean point = true, circle, line, rect, roundRect, clear, fix;
    Color color = Color.RED;
    JCheckBox XorPaintModeCheckBox;
    Raster raster;
    int x = 0, y = 0, x1, y1, x2, y2, w, h, width = 2, f_size;
    JComboBox<String> shapes;
    JLabel cords;
    public JPaint()  // Constructor
    {
        JPanel mainPanel = new JPanel(new BorderLayout());
        Border matteBorder = new MatteBorder(5, 5, 5, 5, Color.GREEN);
        JFrame frame = new JFrame("Paint Application");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        AtomicInteger dimentionWidth = new AtomicInteger(dimension.width);
        int dimentionHeight = dimension.height - 30;
        String title = "Paint Application By PRATIK PATIL";
        mainPanel.setBorder(BorderFactory.createTitledBorder(matteBorder, title));
        frame.setVisible(true);

        setLayout(new BorderLayout());
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        add(centerPanel);
        southPanel = new Panel();
        southPanel.setBackground(Color.PINK);
        cords = new JLabel("Co-Ordinates   " + x + " , " + y);
        southPanel.add(cords);
        JLabel select = new JLabel("   Select Tool");
        southPanel.add(select);

        // ComboBox Section
        shapes = new JComboBox<>(myshape);
        shapes.addActionListener(this);
        southPanel.add(shapes);

        // RadioButton Section
        ButtonGroup group = new ButtonGroup();
        draw = new JRadioButton("Draw", true);
        fill = new JRadioButton("Fill");
        eraser = new JRadioButton("Eraser");
        group.add(draw);
        group.add(fill);
        group.add(eraser);
        draw.addActionListener(this);
        southPanel.add(draw);
        fill.addActionListener(this);
        southPanel.add(fill);
        eraser.addActionListener(this);
        southPanel.add(eraser);

        // CheckBox Section
        XorPaintModeCheckBox = new JCheckBox("XOR Paint Mode");
        southPanel.add(XorPaintModeCheckBox);
        XorPaintModeCheckBox.addActionListener(this);

        // Drawing Text
        insertTextButton = new JButton("Insert Text");
        southPanel.add(insertTextButton);
        insertTextButton.addActionListener(this);

        // west_panel with Color Selection Buttons
        westPanel = new Panel(new GridLayout(10, 1));
        JLabel j = new JLabel("Select Color", SwingConstants.CENTER);
        westPanel.add(j);
        String[] mycolor = {"Red", "Green", "Blue", "Yellow", "Cyan", "Orange", "Magenta", "White", "My Favourite"};
        Color[] c1 = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.ORANGE, Color.MAGENTA, Color.WHITE, Color.RED};

        for (int i = 0; i < 9; i++) {

            buttons[i + 2] = new JButton(mycolor[i]);
            westPanel.add(buttons[i + 2]);
            buttons[i + 2].setBackground(c1[i]);
            buttons[i + 2].addActionListener(this);
        }
        add(westPanel, "West");

        // Clear All & Save Drawing Button
        buttons[0] = new JButton("Clear All");
        southPanel.add(buttons[0]);
        buttons[1] = new JButton("Save Drawing & Exit");
        southPanel.add(buttons[1]);
        buttons[0].addActionListener(this);
        buttons[1].addActionListener(this);
        add(southPanel, "South");
        centerPanel.addMouseMotionListener(this);
        centerPanel.addMouseListener(this);
        southPanel.addMouseListener(this);

        // JSlider Section for Thickness Variation
        JSlider slider = new JSlider(2, 17, 2);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(5);
        southPanel.add(new JLabel("Thickness"));
        southPanel.add(slider);

        ChangeListener sliderAction = changeEvent -> {
            JSlider jSlider = (JSlider) changeEvent.getSource();
            dimentionWidth.set(jSlider.getValue());
            paint(centerPanel.getGraphics());
        };
        slider.addChangeListener(sliderAction);

        // Set BufferSize & Initialize BufferedImage
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        bufferedImage = new BufferedImage(this.getWidth(), this.getHeight() - 35, BufferedImage.TYPE_INT_RGB);

        // Set cursorimage of center_panel
        cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        centerPanel.setCursor(cursor);
        mainPanel.add(this);
        frame.setContentPane(mainPanel);
        frame.setSize(w, h);
        validate();
    }

    public static void main(String[] args) {
        new JPaint();
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
        g1.setStroke(new BasicStroke(width));

        if (!point && !eraser.isSelected()) {

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getWidth());
            bufferedImage.setData(raster);
        } else {
            raster = bufferedImage.getData();
        }

        if (XorPaintModeCheckBox.isSelected() && !eraser.isSelected() && !point && !line) {
            g.setXORMode(color);
        } else {
            g.setColor(color);
        }

        if (fix) {
            g.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, f_size));
            g.drawString(text, x, y);
        }

        if (draw.isSelected()) {
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
        } else if (fill.isSelected()) {
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
        else if (eraser.isSelected()) {
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
                f_size = Integer.parseInt(JOptionPane.showInputDialog("Enter Font Size: "));
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
            color = Color.RED;
        }

        if (actionEvent.getSource() == buttons[3]) {
            color = Color.GREEN;
        }

        if (actionEvent.getSource() == buttons[4]) {
            color = Color.BLUE;
        }

        if (actionEvent.getSource() == buttons[5]) {
            color = Color.YELLOW;
        }

        if (actionEvent.getSource() == buttons[6]) {
            color = Color.CYAN;
        }

        if (actionEvent.getSource() == buttons[7]) {
            color = Color.ORANGE;
        }

        if (actionEvent.getSource() == buttons[8]) {
            color = Color.MAGENTA;
        }

        if (actionEvent.getSource() == buttons[9]) {
            color = Color.WHITE;
        }

        if (actionEvent.getSource() == buttons[10]) // My Favourite Color
        {
            color = JColorChooser.showDialog(JPaint.this, "Select Any Color", color);
        }

        buttons[10].setBackground(color);

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

        if (actionEvent.getSource() == eraser) {

            cursorImage = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
            cursorImage.getGraphics().drawRect(0, 0, 30, 30);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            cursor = toolkit.createCustomCursor(cursorImage, new Point(15, 15), "mycursor");
        }

        if (actionEvent.getSource() == fill || actionEvent.getSource() == draw) {
            cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        }
        centerPanel.requestFocus();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == southPanel) {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        if (me.getSource() == centerPanel) {
            centerPanel.setCursor(cursor);
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

class AnalogClock extends JPanel {

    transient BufferedImage clockImage;

    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    int millis = 0;
    int spacing = 10;
    int size;
    int centerX;
    int centerY;

    float twoPi = (float) (2.0 * Math.PI);
    float threePi = (float) (3.0 * Math.PI);
    float radPerSecMin = (float) (Math.PI / 30.0);

    public AnalogClock() {

        JFrame clockFrame = new JFrame("Analog Clock... BY PRATIK PATIL");
        clockFrame.setVisible(true);
        setPreferredSize(new Dimension(450, 450));
        ActionListener listener = e -> repaint();
        new javax.swing.Timer(1000, listener).start();

        clockFrame.setBackground(Color.yellow);
        clockFrame.setResizable(false);
        clockFrame.add(this);
        clockFrame.pack();
    }

    public static void main(String[] args) {
        new AnalogClock();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(Color.white);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        size = (Math.min(width, height)) - 2 * spacing;
        centerX = size / 2 + spacing;
        centerY = size / 2 + spacing;

        if (clockImage == null || clockImage.getWidth() != width || clockImage.getHeight() != height) {

            clockImage = (BufferedImage) (this.createImage(width, height));
            Graphics2D gc = clockImage.createGraphics();
            gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            drawClockFace(gc);
            gc.setFont(new Font("Monotype Corsiva", Font.PLAIN, 30));
            gc.drawString("Pratik Times", 150, 110);
        }

        Calendar now = Calendar.getInstance();
        hours = now.get(Calendar.HOUR);
        minutes = now.get(Calendar.MINUTE);
        seconds = now.get(Calendar.SECOND);
        millis = now.get(Calendar.MILLISECOND);

        graphics2D.drawImage(clockImage, null, 0, 0);
        drawClockHands(graphics);
    }

    public void drawClockHands(Graphics g) {
        int secondRadius = size / 2;
        int minuteRadius = secondRadius * 3 / 4;
        int hourRadius = secondRadius / 2;

        float fseconds = seconds + (float) millis / 1000;
        float secondAngle = threePi - (radPerSecMin * fseconds);
        drawRadius(g, centerX, centerY, secondAngle, 0, secondRadius);

        float fminutes = (float) (minutes + fseconds / 60.0);
        float minuteAngle = threePi - (radPerSecMin * fminutes);
        drawRadius(g, centerX, centerY, minuteAngle, 0, minuteRadius);

        float fhours = (float) (hours + fminutes / 60.0);
        float hourAngle = threePi - (5 * radPerSecMin * fhours);
        drawRadius(g, centerX, centerY, hourAngle, 0, hourRadius);
    }

    public void drawClockFace(Graphics g) {

        g.setColor(Color.black);
        g.fillOval(spacing, spacing, size, size);
        g.setColor(Color.white);
        g.drawOval(spacing, spacing, size, size);

        for (int sec = 0; sec < 60; sec++) {

            int ticStart;
            if (sec % 5 == 0) {
                ticStart = size / 2 - 10;
            } else {
                ticStart = size / 2 - 5;
            }
            drawRadius(g, centerX, centerY, radPerSecMin * sec, ticStart, size / 2);
        }
    }

    public void drawRadius(Graphics g, int x, int y, double angle, int minRadius, int maxRadius) {

        float sine = (float) Math.sin(angle);
        float cosine = (float) Math.cos(angle);

        int dxmin = (int) (minRadius * sine);
        int dymin = (int) (minRadius * cosine);
        int dxmax = (int) (maxRadius * sine);
        int dymax = (int) (maxRadius * cosine);

        g.drawLine(x + dxmin, y + dymin, x + dxmax, y + dymax);
    }
}

class ScrollBarMagic extends JFrame implements AdjustmentListener {

    JScrollBar s1;
    JScrollBar s2;
    JScrollBar s3;
    JScrollBar s4;

    int x = 100;
    int y = 100;
    int a;
    int b;
    int c;
    int d;
    transient Image image;

    public ScrollBarMagic() {

        super("ScrollBars Magic by Pratik_Patil");
        setVisible(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        MediaTracker mediaTracker = new MediaTracker(this);

        image = toolkit.getImage(getClass().getClassLoader().getResource("Images/IM1.gif"));
        mediaTracker.addImage(image, 0);

        class CustomPanel extends JPanel {

            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(image, 5 * x, 2 * y, this);
            }
        }

        JPanel customPanel = new CustomPanel();
        customPanel.setLayout(new BorderLayout());

        s1 = new JScrollBar(Adjustable.HORIZONTAL, 100, 0, 0, 255);
        s2 = new JScrollBar(Adjustable.VERTICAL, 100, 0, 0, 255);
        s3 = new JScrollBar(Adjustable.HORIZONTAL, 100, 0, 0, 255);
        s4 = new JScrollBar(Adjustable.VERTICAL, 100, 0, 0, 255);

        customPanel.add(s1, "North");
        customPanel.add(s2, "East");
        customPanel.add(s3, "South");
        customPanel.add(s4, "West");

        s1.addAdjustmentListener(this);
        s2.addAdjustmentListener(this);
        s3.addAdjustmentListener(this);
        s4.addAdjustmentListener(this);

        customPanel.setBackground(Color.black);
        add(customPanel);
        setSize(dimension.width, dimension.height - 50);
    }

    public static void main(String[] args) {
        new ScrollBarMagic();
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {

        a = s1.getValue();
        b = s2.getValue();
        c = s3.getValue();
        d = s4.getValue();

        if (e.getSource() == s1 || e.getSource() == s3) {
            x = -a + c;
        }
        if (e.getSource() == s2 || e.getSource() == s4) {
            y = -b + d;
        }
        repaint();
    }
}

class BouncingBall extends JFrame {

    int frameWidth;
    int frameHeight;
    int x = 100;
    int y = 100;
    int size = 200;
    int xSpeed = 20;
    int ySpeed = 80;

    public BouncingBall() {

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        frameWidth = d.width;
        frameHeight = d.height;

        setTitle("Bouncing Ball by Pratik_Patil");
        setVisible(true);
        setResizable(false);

        add(new BallPanel());
        setSize(d);
    }

    public static void main(String[] args) {
        new BouncingBall();
    }

    public void updateXY() {

        x += xSpeed;
        y += ySpeed;

        if (x > (frameWidth - size) || x < 0) {
            xSpeed = -xSpeed;
        }
        if (y > (frameHeight - size) || y < 0) {
            ySpeed = -ySpeed;
        }
    }

    class BallPanel extends JPanel //Inner Class
    {
        public BallPanel() {

            ActionListener listener = actionEvent -> {
                updateXY();
                repaint();
            };
            new javax.swing.Timer(100, listener).start();
        }

        @Override
        public void paintComponent(Graphics graphics) {

            super.paintComponent(graphics);

            setBackground(Color.black);
            graphics.setColor(Color.green);
            graphics.fillOval(x, y, size, size);
            graphics.setColor(Color.red);

            Font f = new Font("TIMES NEW ROMAN", Font.BOLD, 25);
            setFont(f);
            graphics.drawString("PRATIK", x + 60, y + 100);
        }
    }
}
