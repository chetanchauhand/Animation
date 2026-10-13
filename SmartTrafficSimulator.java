
import javax.swing.*;
import java.awt.*;

public class SmartTrafficSimulator extends JPanel {
    private int carX = -80;
    private boolean green = true;
    private int ticks = 0;

    public SmartTrafficSimulator() {
        setPreferredSize(new Dimension(800, 450));
        new Timer(30, e -> {
            ticks++;
            if (ticks % 100 == 0) green = !green;

            if (green || carX > 330) {
                carX += 3;
                if (carX > 850) carX = -100;
            }
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(160, 220, 245));
        g2.fillRect(0, 0, 800, 450);

        // Grass
        g2.setColor(new Color(70, 160, 90));
        g2.fillRect(0, 280, 800, 170);

        // Road
        g2.setColor(new Color(55, 60, 70));
        g2.fillRect(0, 180, 800, 160);
        g2.setColor(Color.WHITE);
        for (int x = 0; x < 800; x += 90) {
            g2.fillRect(x, 255, 45, 5);
        }

        // Traffic signal pole
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(355, 60, 12, 120);
        g2.setColor(Color.BLACK);
        g2.fillRoundRect(325, 25, 72, 130, 15, 15);

        g2.setColor(green ? Color.DARK_GRAY : Color.RED);
        g2.fillOval(341, 38, 40, 40);
        g2.setColor(green ? Color.GREEN : Color.DARK_GRAY);
        g2.fillOval(341, 100, 40, 40);

        // Moving car
        g2.setColor(new Color(35, 110, 240));
        g2.fillRoundRect(carX, 205, 90, 38, 12, 12);
        g2.setColor(Color.CYAN);
        g2.fillRect(carX + 18, 210, 23, 14);
        g2.fillRect(carX + 48, 210, 23, 14);

        g2.setColor(Color.BLACK);
        g2.fillOval(carX + 12, 233, 22, 22);
        g2.fillOval(carX + 62, 233, 22, 22);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillOval(carX + 18, 239, 10, 10);
        g2.fillOval(carX + 68, 239, 10, 10);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 21));
        g2.drawString("SMART TRAFFIC SIMULATOR", 20, 30);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("Signal: " + (green ? "GREEN" : "RED"), 20, 55);
        g2.drawString("Cars are simulated for demonstration", 20, 80);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Smart Traffic Simulator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SmartTrafficSimulator());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
