
import javax.swing.*;
import java.awt.*;

public class GaneshBlessings extends JPanel {
    private int tick = 0;

    public GaneshBlessings() {
        setPreferredSize(new Dimension(800, 600));
        new Timer(45, e -> {
            tick++;
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d = (Graphics2D) g;
        d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight();

        d.setPaint(new GradientPaint(0, 0,
                new Color(65, 15, 70), 0, h,
                new Color(240, 125, 45)));
        d.fillRect(0, 0, w, h);

        // Glowing halo
        int glow = 175 + (int)(25 * Math.sin(tick * 0.08));
        d.setColor(new Color(255, 210, 60, 45));
        d.fillOval(w / 2 - glow / 2, 95 - glow / 2,
                glow, glow);
        d.setColor(new Color(255, 205, 70));
        d.drawOval(w / 2 - 95, 75, 190, 190);
        d.drawOval(w / 2 - 85, 85, 170, 170);

        // Stylized elephant head and ears
        d.setColor(new Color(245, 155, 175));
        d.fillOval(290, 165, 65, 90);
        d.fillOval(445, 165, 65, 90);
        d.setColor(new Color(235, 175, 190));
        d.fillOval(325, 145, 150, 160);

        // Crown
        d.setColor(new Color(255, 200, 45));
        int[] cx = {325, 345, 360, 400, 440, 455, 475};
        int[] cy = {180, 125, 150, 105, 150, 125, 180};
        d.fillPolygon(cx, cy, cx.length);

        // Trunk
        d.setColor(new Color(235, 175, 190));
        d.fillRoundRect(375, 230, 48, 95, 22, 22);
        d.fillOval(400, 295, 42, 30);

        // Eyes
        d.setColor(Color.BLACK);
        d.fillOval(350, 210, 10, 12);
        d.fillOval(440, 210, 10, 12);

        // Body and lotus seat
        d.setColor(new Color(235, 110, 140));
        d.fillOval(325, 305, 150, 115);
        d.setColor(new Color(255, 205, 55));
        for (int i = 0; i < 9; i++) {
            double a = Math.PI * i / 8;
            int x = 400 + (int)(100 * Math.cos(a));
            int y = 430 - (int)(35 * Math.sin(a));
            d.fillOval(x - 18, y - 12, 36, 24);
        }
        d.fillOval(320, 420, 160, 28);

        // Floating flower petals
        for (int i = 0; i < 22; i++) {
            int x = (i * 83 + tick * (1 + i % 3)) % w;
            int y = (i * 61 + tick * 2) % h;
            d.setColor(i % 2 == 0 ? Color.PINK
                    : new Color(255, 220, 80));
            d.fillOval(x, y, 9, 14);
        }

        // Temple lamps
        for (int x : new int[]{100, 670}) {
            d.setColor(new Color(180, 90, 20));
            d.fillOval(x, 455, 40, 20);
            d.setColor(Color.ORANGE);
            d.fillOval(x + 12, 435, 16, 25);
            d.setColor(Color.YELLOW);
            d.fillOval(x + 17, 440, 7, 15);
        }

        d.setColor(Color.WHITE);
        d.setFont(new Font("Serif", Font.BOLD, 30));
        centered(d, "GANESH DIVINE BLESSINGS", w / 2, 45);
        d.setFont(new Font("Serif", Font.PLAIN, 18));
        centered(d, "Wisdom • Peace • Prosperity", w / 2, 555);
    }

    private void centered(Graphics2D g, String s, int x, int y) {
        g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Ganesh Divine Blessings");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new GaneshBlessings());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
