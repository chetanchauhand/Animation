
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class CricketGame extends JPanel implements ActionListener, KeyListener {

    Timer timer = new Timer(20, this);
    Random random = new Random();

    int runs = 0, wickets = 0, balls = 0;
    int target = 30;
    int ballX = 400, ballY = 220;
    int batsmanX = 550;
    int speed = 5;
    int hitPower = 0;
    boolean bowling = true;
    boolean gameOver = false;
    String message = "Press SPACE to play!";
    Color sky = new Color(120, 200, 255);

    public CricketGame() {
        setFocusable(true);
        addKeyListener(this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D a = (Graphics2D) g;

        // Sky
        a.setColor(sky);
        a.fillRect(0, 0, getWidth(), getHeight());

        // Stadium
        a.setColor(new Color(35, 130, 65));
        a.fillRect(0, 260, getWidth(), 300);

        a.setColor(Color.WHITE);
        a.fillRect(0, 255, getWidth(), 5);

        // Pitch
        a.setColor(new Color(194, 155, 100));
        a.fillRoundRect(230, 190, 300, 170, 10, 10);

        // Stumps
        a.setColor(Color.WHITE);
        for (int i = 0; i < 3; i++) {
            a.fillRect(300 + i * 7, 215, 4, 35);
            a.fillRect(560 + i * 7, 300, 4, 35);
        }

        // Bowler
        a.setColor(Color.BLUE);
        a.fillOval(250, 140, 30, 30);
        a.fillRect(258, 170, 15, 50);
        a.drawLine(260, 185, 290, 205);
        a.drawLine(265, 215, 250, 240);
        a.drawLine(270, 215, 285, 240);

        // Batsman
        a.setColor(Color.WHITE);
        a.fillOval(batsmanX, 240, 30, 30);
        a.setColor(Color.BLUE);
        a.fillRect(batsmanX + 5, 270, 22, 45);

        // Bat
        a.setColor(new Color(150, 85, 35));
        if (hitPower > 0) {
            a.drawLine(batsmanX + 10, 285,
                       batsmanX + 55, 250);
        } else {
            a.drawLine(batsmanX + 10, 285,
                       batsmanX - 5, 320);
        }

        // Ball
        a.setColor(Color.RED);
        a.fillOval(ballX, ballY, 14, 14);

        // Scoreboard
        a.setColor(new Color(15, 25, 40));
        a.fillRoundRect(15, 15, 340, 100, 15, 15);

        a.setColor(Color.WHITE);
        a.setFont(new Font("Arial", Font.BOLD, 18));
        a.drawString("JAVA CRICKET", 30, 42);
        a.setFont(new Font("Arial", Font.PLAIN, 16));
        a.drawString("Runs: " + runs + " / " + wickets, 30, 68);
        a.drawString("Overs: " + (balls / 6) + "." + (balls % 6), 30, 92);

        a.setColor(Color.WHITE);
        a.setFont(new Font("Arial", Font.BOLD, 19));
        a.drawString(message, 30, getHeight() - 25);

        if (gameOver) {
            a.setColor(new Color(0, 0, 0, 190));
            a.fillRect(0, 0, getWidth(), getHeight());
            a.setColor(Color.YELLOW);
            a.setFont(new Font("Arial", Font.BOLD, 34));
            a.drawString(runs >= target ? "YOU WIN!" : "MATCH OVER",
                         260, 230);
            a.setFont(new Font("Arial", Font.PLAIN, 20));
            a.drawString("Final Score: " + runs + "/" + wickets,
                         275, 275);
            a.drawString("Press R to restart", 285, 320);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver && bowling) {
            ballX += speed;
            ballY += 1;

            if (ballX >= batsmanX + 5) {
                if (hitPower > 0) {
                    int score = hitPower;
                    runs += score;
                    message = score == 6 ? "SIX!" :
                              score == 4 ? "FOUR!" : score + " RUNS!";
                    hitPower = 0;
                } else {
                    wickets++;
                    message = "OUT! Try again!";
                }

                balls++;
                ballX = 400;
                ballY = 220;
                speed = 4 + random.nextInt(5);

                if (runs >= target) {
                    gameOver = true;
                    message = "Target chased!";
                } else if (wickets >= 5 || balls >= 30) {
                    gameOver = true;
                    message = "Innings finished!";
                }
            }
        }

        if (hitPower > 0) hitPower--;
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            if (bowling && ballX > 450 && ballX < batsmanX + 10) {
                int timing = batsmanX + 10 - ballX;
                hitPower = timing < 15 ? 6 :
                           timing < 35 ? 4 :
                           timing < 55 ? 2 : 1;
                message = "SHOT!";
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            runs = 0;
            wickets = 0;
            balls = 0;
            ballX = 400;
            ballY = 220;
            speed = 5;
            hitPower = 0;
            gameOver = false;
            message = "Press SPACE to play!";
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Cricket Game");
        CricketGame game = new CricketGame();

        frame.add(game);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.requestFocusInWindow();
    }
}
