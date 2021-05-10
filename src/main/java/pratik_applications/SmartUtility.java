// Pratik Softwares: Smart Utility Application
package pratik_applications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class SmartUtility extends JPanel implements ActionListener {

    transient Image image;
    JButton[] buttons = new JButton[6];
    Dimension screenSizeDimension;
    String[] appNames = {"JavaPad", "JCalculator", "JPaint", "AnalogClock", "ScrollBarMagic", "BouncingBall"};
    Color[] colors = {Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.PINK, Color.ORANGE};

    public static void main(String[] args) {
        new SmartUtility();
    }

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
