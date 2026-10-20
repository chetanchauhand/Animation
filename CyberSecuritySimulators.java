
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CyberSecuritySimulators extends JPanel {
    private final Random random = new Random();
    private final int[] x = new int[18], y = new int[18];
    private double angle = 0;
    private int tick = 0;

    public CyberSecuritySimulators() {
        setBackground(new Color(5, 12, 25));
        for (int i = 0; i < x.length; i++) {
            x[i] = 40 + random.nextInt(720);
            y[i] = 60 + random.nextInt(420);
        }
        new Timer(40, e -> {
            angle += 0.06;
            tick++;
            repaint();
        }).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D a = (Graphics2D) g;
        a.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        a.setColor(Color.CYAN);
        a.setFont(new Font("Consolas", Font.BOLD, 23));
        a.drawString("CYBER SECURITY CENTER", 25, 35);
        a.setFont(new Font("Consolas", Font.PLAIN, 13));
        a.drawString("NETWORK SCANNER: ACTIVE", 25, 58);

        for (int i = 0; i < x.length; i++) {
            for (int j = i + 1; j < x.length; j++) {
                if (Point.distance(x[i], y[i], x[j], y[j]) < 180) {
                    a.setColor(new Color(0, 180, 220, 90));
                    a.drawLine(x[i], y[i], x[j], y[j]);
                }
            }
        }

        for (int i = 0; i < x.length; i++) {
            a.setColor(i == tick / 10 % x.length
                       ? Color.RED : Color.CYAN);
            a.fillOval(x[i] - 5, y[i] - 5, 10, 10);
        }

        int cx = getWidth() - 115, cy = getHeight() - 115;
        a.setColor(new Color(0, 255, 140, 150));
        a.drawOval(cx - 70, cy - 70, 140, 140);
        a.drawOval(cx - 45, cy - 45, 90, 90);
        a.drawLine(cx, cy, cx + (int)(65 * Math.cos(angle)),
                   cy + (int)(65 * Math.sin(angle)));

        a.setColor(Color.GREEN);
        a.drawString("FIREWALL: ACTIVE", 25, getHeight() - 45);
        a.drawString("THREATS DETECTED: SIMULATED",
                     25, getHeight() - 25);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Cyber Security Simulator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new CyberSecuritySimulator());
        f.setSize(800, 550);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
