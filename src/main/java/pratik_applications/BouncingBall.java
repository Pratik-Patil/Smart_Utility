package pratik_applications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class BouncingBall extends JFrame {

    int frameWidth;
    int frameHeight;
    int x = 100;
    int y = 100;
    int size = 200;
    int xSpeed = 20;
    int ySpeed = 80;

    public static void main(String[] args) {
        new BouncingBall();
    }

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

            Font font = new Font("TIMES NEW ROMAN", Font.BOLD, 25);
            setFont(font);
            graphics.drawString("PRATIK", x + 60, y + 100);
        }
    }
}
