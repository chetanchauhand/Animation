
import javax.swing.*;
import java.awt.*;

public class ShriRamJourney extends JPanel {
    private int cloudX = 0;
    private int birdX = 0;

    public ShriRamJourney() {
        setPreferredSize(new Dimension(800, 600));
        new Timer(40, e -> {
            cloudX = (cloudX + 1) % 900;
            birdX = (birdX + 3) % 850;
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
                new Color(110, 190, 235), 0, h,
                new Color(245, 220, 145)));
        d.fillRect(0, 0, w, h);

        // Sun
        d.setColor(new Color(255, 220, 100));
        d.fillOval(610, 70, 90, 90);

        // Moving clouds
        d.setColor(new Color(255, 255, 255, 210));
        for (int i = 0; i < 3; i++) {
            int x = (cloudX + i * 300) % 900 - 100;
            int y = 75 + i * 35;
            d.fillOval(x, y, 75, 35);
            d.fillOval(x + 25, y - 18, 65, 45);
            d.fillOval(x + 55, y, 65, 35);
        }

        // Distant forest
        d.setColor(new Color(75, 135, 90));
        int[] fx = {0, 100, 200, 300, 420, 540, 650, 800};
        int[] fy = {340, 220, 345, 240, 355, 245, 350, 280};
        d.fillPolygon(fx, fy, fx.length);

        // Ground
        d.setColor(new Color(45, 115, 60));
        d.fillRect(0, 390, w, 210);

        // Trees
        for (int x : new int[]{75, 180, 590, 730}) {
            d.setColor(new Color(100, 60, 35));
            d.fillRect(x, 245, 25, 180);
            d.setColor(new Color(25, 100, 45));
            d.fillOval(x - 45, 195, 115, 100);
            d.fillOval(x - 30, 160, 85, 90);
        }

        // Stylized blue figure
        d.setColor(new Color(45, 115, 210));
        d.fillOval(330, 185, 80, 85);
        d.fillRoundRect(325, 260, 90, 145, 30, 30);

        // Yellow clothing
        d.setColor(new Color(255, 200, 50));
        int[] clothX = {325, 415, 435, 310};
        int[] clothY = {300, 300, 405, 405};
        d.fillPolygon(clothX, clothY, 4);

        // Bow
        d.setColor(new Color(120, 65, 25));
        d.setStroke(new BasicStroke(6));
        d.drawArc(410, 205, 95, 190, 270, 180);
        d.setColor(Color.WHITE);
        d.setStroke(new BasicStroke(1));
        d.drawLine(457, 210, 457, 390);

        // Moving birds
        d.setColor(new Color(40, 50, 55));
        for (int i = 0; i < 3; i++) {
            int x = (birdX + i * 120) % 850;
            int y = 130 + i * 22;
            d.drawArc(x, y, 20, 12, 0, 180);
            d.drawArc(x + 20, y, 20, 12, 0, 180);
        }

        d.setColor(Color.WHITE);
        d.setFont(new Font("Serif", Font.BOLD, 30));
        centered(d, "SHRI RAM FOREST JOURNEY", w / 2, 40);
        d.setFont(new Font("Serif", Font.PLAIN, 18));
        centered(d, "Dharma • Peace • Courage", w / 2, 555);
    }

    private void centered(Graphics2D g, String s, int x, int y) {
        g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Shri Ram Forest Journey");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new ShriRamJourney());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
