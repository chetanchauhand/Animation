
import javax.swing.*;
import java.awt.*;

public class MiniSmartFactory extends JPanel {
    private int productX = 30;
    private int beltOffset = 0;
    private int produced = 0;
    private String status = "Factory running";
    private Color productColor = Color.ORANGE;

    public MiniSmartFactory() {
        setPreferredSize(new Dimension(800, 450));

        new Timer(40, e -> {
            productX += 3;
            beltOffset = (beltOffset + 3) % 40;

            if (productX > 590) {
                productX = 30;
                produced++;

                // Alternate product types
                productColor = produced % 2 == 0
                        ? Color.CYAN : Color.ORANGE;

                status = produced % 2 == 0
                        ? "Product sorted: Type A"
                        : "Product sorted: Type B";
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

        g2.setColor(new Color(235, 240, 245));
        g2.fillRect(0, 0, 800, 450);

        // Header
        g2.setColor(new Color(25, 45, 65));
        g2.fillRect(0, 0, 800, 85);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 23));
        g2.drawString("SMART FACTORY CONTROL", 25, 35);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("Products processed: " + produced, 25, 62);

        // Conveyor belt
        g2.setColor(new Color(60, 65, 75));
        g2.fillRoundRect(30, 270, 620, 65, 12, 12);

        // Moving belt strips
        g2.setColor(Color.LIGHT_GRAY);
        for (int x = 30 - beltOffset; x < 650; x += 40) {
            g2.fillRect(x, 280, 4, 45);
        }

        // Conveyor rollers
        g2.setColor(Color.GRAY);
        for (int x = 45; x < 650; x += 60) {
            g2.fillOval(x, 320, 22, 22);
        }

        // Factory robot arm
        g2.setColor(new Color(30, 100, 180));
        g2.fillRect(665, 165, 28, 115);
        g2.fillRoundRect(640, 145, 80, 35, 10, 10);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(650, 175, 8, 40);
        g2.fillRect(702, 175, 8, 40);

        // Sorting bins
        g2.setColor(new Color(60, 170, 100));
        g2.fillRect(665, 340, 55, 45);
        g2.setColor(new Color(60, 120, 220));
        g2.fillRect(730, 340, 55, 45);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 13));
        g2.drawString("TYPE A", 668, 405);
        g2.drawString("TYPE B", 733, 405);

        // Moving product
        g2.setColor(productColor);
        g2.fillRoundRect(productX, 225, 48, 45, 8, 8);
        g2.setColor(Color.BLACK);
        g2.drawRect(productX + 8, 235, 32, 25);

        // Status panel
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.drawString("STATUS: " + status, 30, 130);
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString("Automated production demo", 30, 155);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mini Smart Factory");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new MiniSmartFactory());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
