
import javax.swing.*;
import java.awt.*;

public class SmartRailwaySimulator extends JPanel {
    int trainX = -180;
    Timer timer;

    public SmartRailwaySimulator() {
        setBackground(new Color(20, 30, 45));
        timer = new Timer(30, e -> {
            trainX += 3;
            if (trainX > getWidth()) trainX = -180;
            repaint();
        });
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D a = (Graphics2D) g;
        a.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        a.setColor(Color.WHITE);
        a.setFont(new Font("Arial", Font.BOLD, 24));
        a.drawString("SMART RAILWAY SIMULATOR", 25, 40);

        a.setColor(new Color(75, 85, 95));
        a.fillRect(0, h - 145, w, 145);

        a.setColor(Color.LIGHT_GRAY);
        a.fillRect(0, h - 85, w, 7);
        a.fillRect(0, h - 48, w, 7);

        a.setColor(new Color(170, 120, 75));
        for (int i = 0; i < w; i += 35)
            a.fillRect(i, h - 90, 9, 55);

        // Train body and coaches
        for (int i = 0; i < 3; i++) {
            int tx = trainX + i * 65;
            a.setColor(new Color(30, 140, 210));
            a.fillRoundRect(tx, h - 200, 60, 95, 10, 10);

            a.setColor(Color.CYAN);
            a.fillRect(tx + 8, h - 185, 18, 22);
            a.fillRect(tx + 34, h - 185, 18, 22);

            a.setColor(Color.BLACK);
            a.fillOval(tx + 10, h - 115, 18, 18);
            a.fillOval(tx + 40, h - 115, 18, 18);
        }

        // Railway signal
        a.setColor(Color.GRAY);
        a.fillRect(w - 80, h - 260, 8, 115);
        a.setColor(trainX % 500 < 250 ? Color.GREEN : Color.RED);
        a.fillOval(w - 92, h - 275, 32, 32);

        a.setColor(Color.WHITE);
        a.drawString("Station: " +
            (trainX % 500 < 250 ? "Departure" : "Signal Check"),
            25, 75);
        a.drawString("Train Status: RUNNING",
                     25, h - 20);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Smart Railway Simulator");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new SmartRailwaySimulator());
        f.setSize(850, 500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
