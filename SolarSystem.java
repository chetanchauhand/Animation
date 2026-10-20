
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SolarSystem extends JPanel {
    String[] names = {"Mercury", "Venus", "Earth", "Mars",
                      "Jupiter", "Saturn"};
    int[] orbit = {55, 85, 115, 145, 185, 225};
    int[] size = {8, 12, 14, 11, 24, 20};
    Color[] colors = {Color.GRAY, Color.ORANGE, Color.CYAN,
                      Color.RED, Color.ORANGE, Color.YELLOW};
    double[] angle = new double[6];
    String selected = "Click a planet";
    Timer timer;

    public SolarSystem() {
        setBackground(Color.BLACK);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int cx = getWidth() / 2, cy = getHeight() / 2;
                for (int i = 0; i < 6; i++) {
                    int px = cx + (int)(orbit[i] * Math.cos(angle[i]));
                    int py = cy + (int)(orbit[i] * Math.sin(angle[i]));
                    if (Point.distance(e.getX(), e.getY(), px, py)
                            < size[i] + 8) {
                        selected = names[i];
                    }
                }
                repaint();
            }
        });
        timer = new Timer(30, e -> {
            for (int i = 0; i < 6; i++)
                angle[i] += 0.01 / (1 + i * 0.5);
            repaint();
        });
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D a = (Graphics2D) g;
        a.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = getWidth() / 2, cy = getHeight() / 2;

        a.setColor(Color.WHITE);
        a.drawString("INTERACTIVE SOLAR SYSTEM", 20, 25);

        a.setColor(Color.YELLOW);
        a.fillOval(cx - 24, cy - 24, 48, 48);

        for (int i = 0; i < 6; i++) {
            a.setColor(new Color(90, 90, 90));
            a.drawOval(cx - orbit[i], cy - orbit[i],
                       orbit[i] * 2, orbit[i] * 2);

            int px = cx + (int)(orbit[i] * Math.cos(angle[i]));
            int py = cy + (int)(orbit[i] * Math.sin(angle[i]));

            a.setColor(colors[i]);
            a.fillOval(px - size[i] / 2, py - size[i] / 2,
                       size[i], size[i]);

            if (names[i].equals(selected)) {
                a.setColor(Color.WHITE);
                a.drawString(names[i], px + 8, py - 8);
            }
        }

        a.setColor(Color.CYAN);
        a.drawString(selected, 20, getHeight() - 20);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Solar System Animation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new SolarSystem());
        f.setSize(800, 650);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
