
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ShivaUniverse extends JPanel {
    private final Random random = new Random(7);
    private final int[] sx = new int[100];
    private final int[] sy = new int[100];
    private int glow = 0;
    private int moonX = 0;

    public ShivaUniverse() {
        setPreferredSize(new Dimension(800, 600));

        for (int i = 0; i < sx.length; i++) {
            sx[i] = random.nextInt(800);
            sy[i] = random.nextInt(600);
        }

        new Timer(80, e -> {
            glow = (glow + 1) % 100;
            moonX = (moonX + 1) % 800;
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        g2.setPaint(new GradientPaint(0, 0,
                new Color(5, 8, 40), 0, h,
                new Color(50, 15, 90)));
        g2.fillRect(0, 0, w, h);

        // Animated stars
        g2.setColor(Color.WHITE);
        for (int i = 0; i < sx.length; i++) {
            int size = (i + glow / 10) % 3 + 1;
            g2.fillOval(sx[i], sy[i], size, size);
        }

        // Glowing aura
        int aura = 160 + glow;
        g2.setColor(new Color(50, 130, 255, 25));
        g2.fillOval(w / 2 - aura / 2, 160 - aura / 2,
                aura, aura);
        g2.setColor(new Color(60, 150, 255, 45));
        g2.fillOval(w / 2 - 65, 95, 130, 130);

        // Crescent moon
        g2.setColor(new Color(255, 235, 160));
        g2.fillOval(600 + moonX / 8, 55, 55, 55);
        g2.setColor(new Color(10, 12, 55));
        g2.fillOval(615 + moonX / 8, 45, 55, 55);

        // Mount Kailash
        g2.setColor(new Color(100, 115, 155));
        int[] mx = {0, 130, 245, 330, 410, 520, 650, 800};
        int[] my = {410, 300, 390, 230, 380, 290, 390, 330};
        g2.fillPolygon(mx, my, mx.length);

        g2.setColor(new Color(225, 230, 250));
        int[] snowX = {275, 330, 365, 410, 455};
        int[] snowY = {285, 230, 285, 380, 300};
        g2.fillPolygon(snowX, snowY, snowX.length);

        // Meditating symbolic silhouette
        g2.setColor(new Color(40, 100, 200));
        g2.fillOval(w / 2 - 42, 170, 84, 84);
        g2.fillRoundRect(w / 2 - 45, 245, 90, 100, 35, 35);
        g2.fillOval(w / 2 - 100, 300, 200, 45);

        // Face details kept simple
        g2.setColor(new Color(220, 235, 255));
        g2.drawLine(w / 2 - 15, 205, w / 2 + 15, 205);
        g2.drawLine(w / 2, 210, w / 2, 225);

        // Symbolic trident
        int tx = 620, ty = 215;
        g2.setStroke(new BasicStroke(7));
        g2.setColor(new Color(255, 195, 65));
        g2.drawLine(tx, ty, tx, 435);
        g2.drawLine(tx, ty, tx - 35, ty - 30);
        g2.drawLine(tx - 35, ty - 30, tx - 35, ty + 5);
        g2.drawLine(tx - 35, ty + 5, tx - 10, ty + 25);
        g2.drawLine(tx, ty, tx + 35, ty - 30);
        g2.drawLine(tx + 35, ty - 30, tx + 35, ty + 5);
        g2.drawLine(tx + 35, ty + 5, tx + 10, ty + 25);
        g2.drawLine(tx, ty - 10, tx, ty + 35);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 30));
        drawCentered(g2, "SHIVA UNIVERSE", w / 2, 45);

        g2.setFont(new Font("Serif", Font.PLAIN, 17));
        drawCentered(g2, "Peace • Meditation • Cosmic Energy",
                w / 2, h - 25);
    }

    private void drawCentered(Graphics2D g, String s, int x, int y) {
        g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Shiva Universe");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new ShivaUniverse());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
