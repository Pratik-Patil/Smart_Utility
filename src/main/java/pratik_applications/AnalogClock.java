package pratik_applications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Calendar;

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

    public static void main(String[] args) {
        new AnalogClock();
    }

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
