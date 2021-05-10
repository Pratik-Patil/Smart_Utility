package pratik_applications;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

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

    public static void main(String[] args) {
        new JavaPad();
    }

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
