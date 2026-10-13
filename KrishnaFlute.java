
import javax.swing.*;
import java.awt.*;

public class KrishnaFlute extends JPanel {
    private int flowerOffset = 0;
    private int noteOffset = 0;

    public KrishnaFlute() {
        setPreferredSize(new Dimension(800, 600));

        new Timer(45, e -> {
            flowerOffset = (flowerOffset + 2) % 800;
            noteOffset = (noteOffset + 1) % 100;
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

        // Twilight background
        g2.setPaint(new GradientPaint(0, 0,
                new Color(25, 25, 90), 0, h,
                new Color(235, 130, 150)));
        g2.fillRect(0, 0, w, h);

        // Moon
        g2.setColor(new Color(255, 245, 200));
        g2.fillOval(620, 55, 85, 85);

        // Distant hills
        g2.setColor(new Color(60, 100, 95));
        g2.fillOval(-100, 330, 450, 250);
        g2.fillOval(300, 350, 550, 250);

        // Tree trunk and canopy
        g2.setColor(new Color(90, 50, 35));
        g2.fillRect(90, 160, 32, 260);
        g2.setColor(new Color(30, 100, 65));
        g2.fillOval(25, 115, 150, 130);
        g2.fillOval(75, 80, 130, 120);

        // Peacock feather motif
        g2.setColor(new Color(20, 130, 90));
        g2.fillOval(390, 115, 35, 115);
        g2.setColor(new Color(40, 180, 170));
        g2.fillOval(395, 125, 25, 65);
        g2.setColor(new Color(40, 60, 150));
        g2.fillOval(398, 135, 20, 35);
        g2.setColor(new Color(255, 205, 70));
        g2.fillOval(403, 143, 10, 17);

        // Stylized devotional figure
        g2.setColor(new Color(40, 145, 210));
        g2.fillOval(330, 210, 110, 110);
        g2.fillRoundRect(330, 300, 110, 130, 35, 35);

        // Crown
        g2.setColor(new Color(245, 190, 50));
        int[] cx = {335, 350, 365, 385, 405, 425, 438};
        int[] cy = {230, 190, 215, 180, 215, 190, 230};
        g2.fillPolygon(cx, cy, cx.length);

        // Flute
        g2.setColor(new Color(245, 205, 110));
        g2.setStroke(new BasicStroke(8));
        g2.drawLine(365, 295, 500, 280);

        g2.setColor(new Color(70, 45, 25));
        for (int i = 0; i < 5; i++) {
            int x = 390 + i * 20;
            int y = 292 - i * 2;
            g2.fillOval(x, y, 5, 5);
        }

        // Decorative floating flowers
        for (int i = 0; i < 12; i++) {
            int x = (i * 79 + flowerOffset) % w;
            int y = 150 + (i * 43) % 300;
            drawFlower(g2, x, y, 10);
        }

        // Floating musical notes
        g2.setColor(new Color(255, 245, 180));
        g2.setFont(new Font("Serif", Font.BOLD, 30));
        g2.drawString("♪", 490, 230 - noteOffset / 2);
        g2.drawString("♫", 530, 260 - noteOffset / 3);
        g2.drawString("♪", 570, 220 - noteOffset / 4);

        // Foreground grass
        g2.setColor(new Color(35, 95, 55));
        g2.fillRect(0, 445, w, 155);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 29));
        drawCentered(g2, "KRISHNA BHAGWAN", w / 2, 45);

        g2.setFont(new Font("Serif", Font.ITALIC, 17));
        drawCentered(g2, "A melody of peace and devotion",
                w / 2, h - 25);
    }

    private void drawFlower(Graphics2D g, int x, int y, int r) {
        g.setColor(new Color(255, 190, 215));
        for (int i = 0; i < 5; i++) {
            double a = i * Math.PI * 2 / 5;
            int px = x + (int)(Math.cos(a) * r);
            int py = y + (int)(Math.sin(a) * r);
            g.fillOval(px - 5, py - 5, 10, 10);
        }
        g.setColor(new Color(255, 220, 80));
        g.fillOval(x - 4, y - 4, 8, 8);
    }

    private void drawCentered(Graphics2D g, String s, int x, int y) {
        g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Krishna BHAGWAN Animation");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new KrishnaFlute());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
