package pratik_applications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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

    public static void main(String[] args) {
        new JCalculator();
    }

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
