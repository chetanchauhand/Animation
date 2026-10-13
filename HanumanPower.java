
import javax.swing.*;
import java.awt.*;

public class HanumanPower extends JPanel {
    private int rayAngle = 0;
    private int auraSize = 0;

    public HanumanPower() {
        setPreferredSize(new Dimension(800, 600));

        new Timer(50, e -> {
            rayAngle = (rayAngle + 3) % 360;
            auraSize = (auraSize + 2) % 50;
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

        // Sunrise sky
        g2.setPaint(new GradientPaint(0, 0,
                new Color(70, 35, 110), 0, h,
                new Color(255, 165, 65)));
        g2.fillRect(0, 0, w, h);

        // Sun and glowing aura
        int sunX = 400, sunY = 255;
        int size = 135 + auraSize;

        g2.setColor(new Color(255, 230, 100, 35));
        g2.fillOval(sunX - size, sunY - size,
                size * 2, size * 2);

        g2.setColor(new Color(255, 215, 80));
        g2.fillOval(sunX - 65, sunY - 65, 130, 130);

        // Rotating symbolic sun rays
        Graphics2D rays = (Graphics2D) g2.create();
        rays.rotate(Math.toRadians(rayAngle), sunX, sunY);
        rays.setColor(new Color(255, 225, 130, 180));
        rays.setStroke(new BasicStroke(4));

        for (int i = 0; i < 12; i++) {
            double a = i * Math.PI / 6;
            int x1 = sunX + (int)(Math.cos(a) * 85);
            int y1 = sunY + (int)(Math.sin(a) * 85);
            int x2 = sunX + (int)(Math.cos(a) * 125);
            int y2 = sunY + (int)(Math.sin(a) * 125);
            rays.drawLine(x1, y1, x2, y2);
        }
        rays.dispose();

        // Mountain silhouettes
        g2.setColor(new Color(100, 65, 85));
        int[] mx1 = {0, 120, 250, 350, 480, 620, 800};
        int[] my1 = {390, 250, 365, 230, 360, 260, 385};
        g2.fillPolygon(mx1, my1, mx1.length);

        g2.setColor(new Color(65, 55, 80));
        int[] mx2 = {0, 180, 320, 440, 570, 690, 800};
        int[] my2 = {450, 335, 420, 315, 420, 335, 445};
        g2.fillPolygon(mx2, my2, mx2.length);

        // Foreground
        g2.setColor(new Color(40, 75, 55));
        g2.fillRect(0, 445, w, 155);

        // Symbolic golden gada
        Graphics2D gada = (Graphics2D) g2.create();
        gada.rotate(Math.toRadians(-25), 610, 350);

        gada.setColor(new Color(190, 115, 20));
        gada.fillRoundRect(600, 290, 18, 190, 9, 9);

        gada.setColor(new Color(255, 200, 55));
        gada.fillOval(565, 245, 90, 90);

        gada.setColor(new Color(255, 225, 115));
        gada.fillOval(580, 260, 60, 60);

        gada.setColor(new Color(170, 90, 15));
        gada.fillOval(594, 274, 32, 32);

        gada.setColor(new Color(255, 205, 65));
        gada.fillRoundRect(590, 455, 38, 15, 6, 6);
        gada.dispose();

        // Decorative floating particles
        for (int i = 0; i < 25; i++) {
            int x = (i * 97 + rayAngle * 2) % w;
            int y = 100 + (i * 47) % 400;
            g2.setColor(new Color(255, 225, 130, 180));
            g2.fillOval(x, y, 4, 4);
        }

        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 30));
        drawCentered(g2, "HANUMAN POWER", w / 2, 45);

        g2.setFont(new Font("Serif", Font.PLAIN, 17));
        drawCentered(g2, "Courage • Strength • Devotion",
                w / 2, h - 25);
    }

    private void drawCentered(Graphics2D g, String s, int x, int y) {
        g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Hanuman Power Animation");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new HanumanPower());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
