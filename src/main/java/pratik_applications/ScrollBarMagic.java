package pratik_applications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

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

    public static void main(String[] args) {
        new ScrollBarMagic();
    }

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
