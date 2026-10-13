
import javax.swing.*;
import java.awt.*;

public class RailTrackSimulator extends JPanel {
    private int trainX = -250;
    private final Timer timer;

    public RailTrackSimulator() {
        setPreferredSize(new Dimension(800, 450));
        setBackground(new Color(180, 220, 245));

        timer = new Timer(30, e -> {
            trainX += 3;
            if (trainX > getWidth()) trainX = -280;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Sky and sun
        g2.setColor(new Color(180, 220, 245));
        g2.fillRect(0, 0, getWidth(), 300);
        g2.setColor(Color.ORANGE);
        g2.fillOval(650, 35, 65, 65);

        // Station building
        g2.setColor(new Color(70, 80, 105));
        g2.fillRect(40, 100, 230, 130);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("RAILWAY STATION", 55, 140);
        g2.setFont(new Font("Arial", Font.PLAIN, 15));
        g2.drawString("PLATFORM 1", 95, 175);

        // Platform and railway track
        g2.setColor(new Color(190, 190, 190));
        g2.fillRect(0, 280, 800, 65);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 345, 800, 8);
        g2.fillRect(0, 370, 800, 8);

        g2.setColor(new Color(130, 90, 60));
        for (int x = 0; x < 800; x += 35) {
            g2.fillRect(x, 340, 12, 40);
        }

        // Animated train
        g2.setColor(new Color(190, 35, 55));
        g2.fillRoundRect(trainX, 205, 280, 110, 18, 18);

        // Front cabin
        g2.setColor(new Color(30, 45, 65));
        g2.fillRoundRect(trainX + 220, 215, 52, 65, 12, 12);

        // Windows
        g2.setColor(new Color(130, 220, 245));
        for (int i = 0; i < 4; i++) {
            g2.fillRoundRect(trainX + 15 + i * 48,
                    220, 37, 35, 5, 5);
        }

        // Train door
        g2.setColor(new Color(100, 20, 35));
        g2.fillRect(trainX + 165, 220, 35, 90);

        // Wheels
        g2.setColor(Color.BLACK);
        for (int i = 0; i < 5; i++) {
            g2.fillOval(trainX + 15 + i * 55, 295, 30, 30);
        }
        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 5; i++) {
            g2.fillOval(trainX + 23 + i * 55, 303, 14, 14);
        }

        // Status
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 17));
        g2.drawString("RAILTRACK SIMULATOR", 20, 30);
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString("Train 12001 | New Delhi Express", 20, 52);
        g2.drawString("Status: Train in motion", 20, 72);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("RailTrack Simulator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new RailTrackSimulator());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
