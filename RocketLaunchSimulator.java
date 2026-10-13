
import javax.swing.*;
import java.awt.*;

public class RocketLaunchSimulator extends JPanel {
    private int countdown = 10;
    private int rocketY = 270;
    private boolean launched = false;
    private Timer timer;
    private final JLabel info = new JLabel("Launch sequence starting...");

    public RocketLaunchSimulator() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(new Color(10, 15, 40));

        timer = new Timer(1000, e -> {
            if (countdown > 0) {
                countdown--;
                info.setText("T-Minus " + countdown + " seconds");
            } else {
                launched = true;
                rocketY -= 12;
                info.setText("ROCKET LAUNCH IN PROGRESS!");

                if (rocketY < -100) {
                    timer.stop();
                    info.setText("Launch sequence complete!");
                }
            }
            repaint();
        });

        JButton launch = new JButton("Start Countdown");
        launch.addActionListener(e -> {
            countdown = 10;
            rocketY = 270;
            launched = false;
            info.setText("T-Minus 10 seconds");
            timer.start();
        });

        setLayout(new BorderLayout());
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setForeground(Color.GREEN);
        info.setFont(new Font("Monospaced", Font.BOLD, 17));
        info.setOpaque(true);
        info.setBackground(new Color(10, 15, 40));

        JPanel controls = new JPanel(new GridLayout(2, 1));
        controls.add(info);
        controls.add(launch);
        add(controls, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Stars
        g2.setColor(Color.WHITE);
        for (int i = 0; i < 50; i++) {
            int x = (i * 97 + 23) % getWidth();
            int y = (i * 53 + 15) % 330;
            g2.fillOval(x, y, 3, 3);
        }

        // Launch platform
        g2.setColor(Color.GRAY);
        g2.fillRect(180, 340, 240, 20);
        g2.fillRect(210, 360, 15, 35);
        g2.fillRect(375, 360, 15, 35);

        // Rocket body
        int x = 265;
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, rocketY, 70, 125, 30, 30);

        // Rocket nose
        int[] xs = {x, x + 35, x + 70};
        int[] ys = {rocketY + 25, rocketY - 35, rocketY + 25};
        g2.setColor(Color.RED);
        g2.fillPolygon(xs, ys, 3);

        // Window
        g2.setColor(new Color(30, 150, 255));
        g2.fillOval(x + 20, rocketY + 35, 30, 30);

        // Fins
        g2.setColor(Color.RED);
        int[] leftX = {x, x - 22, x};
        int[] finY = {rocketY + 90, rocketY + 125, rocketY + 125};
        g2.fillPolygon(leftX, finY, 3);

        int[] rightX = {x + 70, x + 92, x + 70};
        g2.fillPolygon(rightX, finY, 3);

        // Animated exhaust
        if (launched) {
            g2.setColor(Color.ORANGE);
            g2.fillOval(x + 15, rocketY + 110, 40, 55);
            g2.setColor(Color.YELLOW);
            g2.fillOval(x + 25, rocketY + 115, 20, 35);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.drawString("SPACE MISSION CONTROL", 125, 35);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("Countdown: " + countdown, 20, 75);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rocket Launch Simulator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new RocketLaunchSimulator());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
