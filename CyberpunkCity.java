
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CyberpunkCity extends JPanel {
    Random random = new Random();
    int[] rainX = new int[100];
    int[] rainY = new int[100];
    int carX = -100;
    int phase = 0;

    public CyberpunkCity() {
        for (int i = 0; i < rainX.length; i++) {
            rainX[i] = random.nextInt(800);
            rainY[i] = random.nextInt(450);
        }
        new Timer(35, e -> {
            carX += 4;
            if (carX > getWidth()) carX = -100;
            phase = (phase + 1) % 600;
            for (int i = 0; i < rainY.length; i++) {
                rainY[i] += 7;
                if (rainY[i] > getHeight()) {
                    rainY[i] = 0;
                    rainX[i] = random.nextInt(Math.max(1, getWidth()));
                }
            }
            repaint();
        }).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D a = (Graphics2D) g;
        int w = getWidth(), h = getHeight();

        boolean night = phase < 450;
        a.setColor(night ? new Color(10, 10, 38)
                         : new Color(100, 175, 220));
        a.fillRect(0, 0, w, h);

        // City buildings
        int[] heights = {180, 260, 210, 300, 225, 270, 190};
        for (int i = 0; i < 7; i++) {
            int bx = i * (w / 7);
            int bh = heights[i % heights.length];
            a.setColor(new Color(25, 30, 55));
            a.fillRect(bx, h - bh - 70, w / 7 - 5, bh);

            for (int yy = h - bh - 55; yy < h - 90; yy += 25) {
                for (int xx = bx + 10; xx < bx + w / 7 - 12; xx += 22) {
                    a.setColor(night
                        ? new Color(0, 230, 255)
                        : new Color(220, 220, 160));
                    a.fillRect(xx, yy, 9, 12);
                }
            }
        }

        // Road and moving car
        a.setColor(Color.DARK_GRAY);
        a.fillRect(0, h - 70, w, 70);
        a.setColor(Color.WHITE);
        for (int i = 0; i < w; i += 70)
            a.fillRect(i, h - 37, 35, 3);

        a.setColor(Color.MAGENTA);
        a.fillRoundRect(carX, h - 65, 85, 28, 8, 8);
        a.setColor(Color.CYAN);
        a.fillRect(carX + 15, h - 60, 25, 12);

        // Rain
        a.setColor(new Color(80, 220, 255));
        for (int i = 0; i < rainY.length; i++)
            a.drawLine(rainX[i], rainY[i],
                       rainX[i] - 5, rainY[i] + 12);

        a.setColor(Color.WHITE);
        a.setFont(new Font("Consolas", Font.BOLD, 20));
        a.drawString("NEON CITY  |  " +
                     (night ? "NIGHT" : "DAY"), 20, 30);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Living Cyberpunk City");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new CyberpunkCity());
        f.setSize(800, 500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
