
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PhoenixRebirth extends JPanel {
    Random random = new Random();
    int[] px = new int[180];
    int[] py = new int[180];
    int[] speed = new int[180];
    float glow = 0;

    public PhoenixRebirth() {
        setBackground(Color.BLACK);

        for (int i = 0; i < px.length; i++) {
            px[i] = random.nextInt(800);
            py[i] = random.nextInt(600);
            speed[i] = 1 + random.nextInt(4);
        }

        new Timer(35, e -> {
            glow += 0.08;
            for (int i = 0; i < py.length; i++) {
                py[i] -= speed[i];
                px[i] += random.nextInt(3) - 1;

                if (py[i] < 0) {
                    py[i] = getHeight();
                    px[i] = getWidth() / 2
                            + random.nextInt(240) - 120;
                }
            }
            repaint();
        }).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D a = (Graphics2D) g;
        a.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int brightness = 180 + (int)(75 * Math.abs(Math.sin(glow)));

        // Glowing body
        a.setColor(new Color(255, brightness, 0));
        a.fillOval(cx - 17, cy - 35, 34, 90);

        // Phoenix head and beak
        a.fillOval(cx - 12, cy - 58, 24, 24);
        a.setColor(Color.RED);
        a.fillPolygon(
            new int[]{cx + 8, cx + 27, cx + 8},
            new int[]{cy - 51, cy - 45, cy - 41}, 3);

        // Spread wings using layered curves
        for (int i = 0; i < 9; i++) {
            int offset = i * 13;
            a.setColor(new Color(255, 70 + i * 15, 0));
            a.setStroke(new BasicStroke(4));

            a.drawLine(cx - 5, cy - 15,
                       cx - 45 - offset, cy - 45 - offset / 3);
            a.drawLine(cx + 5, cy - 15,
                       cx + 45 + offset, cy - 45 - offset / 3);

            a.drawLine(cx - 5, cy + 5,
                       cx - 35 - offset, cy + 25 + offset / 4);
            a.drawLine(cx + 5, cy + 5,
                       cx + 35 + offset, cy + 25 + offset / 4);
        }

        // Tail feathers
        for (int i = -3; i <= 3; i++) {
            a.setColor(i % 2 == 0 ? Color.ORANGE : Color.RED);
            a.setStroke(new BasicStroke(5));
            a.drawLine(cx, cy + 35, cx + i * 18, cy + 100);
        }

        // Rising fire particles
        for (int i = 0; i < px.length; i++) {
            a.setColor(i % 2 == 0
                ? Color.ORANGE : Color.YELLOW);
            a.fillOval(px[i], py[i], 4, 4);
        }

        a.setColor(Color.WHITE);
        a.setFont(new Font("Serif", Font.BOLD, 27));
        String title = "PHOENIX REBIRTH";
        FontMetrics fm = a.getFontMetrics();
        a.drawString(title, cx - fm.stringWidth(title) / 2,
                     40);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Phoenix Rebirth Animation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new PhoenixRebirth());
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
