
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;

public class CricketGame extends JPanel implements ActionListener, KeyListener {
    Timer timer = new Timer(25, this);
    Random random = new Random();

    int runs = 0, wickets = 0, balls = 0;
    int target = 24;
    int ballX = 360, ballY = 350;
    int shotX, shotY, shotRuns;
    int batSwing = 0, celebration = 0;
    int state = 0; // 0 ready, 1 bowling, 2 hit, 3 over
    int messageTimer = 0;
    double ballSpeed = 6;
    double shotVX, shotVY;
    double time = 0;
    String message = "PRESS SPACE TO START";
    String lastShot = "";
    boolean gameOver = false;

    final Color grass = new Color(35, 125, 64);
    final Color cream = new Color(242, 213, 153);

    public CricketGame() {
        setPreferredSize(new Dimension(1000, 650));
        setBackground(new Color(12, 22, 39));
        setFocusable(true);
        addKeyListener(this);
        timer.start();
    }

    void newBall() {
        ballX = 360;
        ballY = 355;
        ballSpeed = 5 + random.nextDouble() * 2.5;
        state = 0;
        message = "PRESS SPACE TO BAT";
        messageTimer = 0;
        batSwing = 0;
    }

    void restart() {
        runs = wickets = balls = 0;
        gameOver = false;
        lastShot = "";
        celebration = 0;
        newBall();
    }

    void finishBall(boolean out) {
        balls++;
        if (out) {
            wickets++;
            message = "BOWLED! WICKET!";
            lastShot = "WICKET";
        } else {
            runs += shotRuns;
            lastShot = shotRuns == 6 ? "SIX!" :
                       shotRuns == 4 ? "FOUR!" :
                       shotRuns + " RUN" + (shotRuns == 1 ? "" : "S");
            message = lastShot;
            if (shotRuns >= 4) celebration = 35;
        }

        if (runs >= target) {
            gameOver = true;
            message = "YOU WON THE MATCH!";
        } else if (wickets >= 3 || balls >= 12) {
            gameOver = true;
            message = runs >= target ? "YOU WON!" : "INNINGS OVER";
        } else {
            state = 3;
            messageTimer = 48;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D a = (Graphics2D) g.create();
        a.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        // Night sky
        a.setPaint(new GradientPaint(0, 0, new Color(10, 24, 52),
                                     0, 300, new Color(35, 70, 103)));
        a.fillRect(0, 0, w, h);

        // Stadium roof
        a.setColor(new Color(12, 20, 35));
        a.fillRect(0, 85, w, 115);

        // Stadium tiers
        a.setColor(new Color(60, 69, 85));
        a.fillRect(0, 105, w, 15);
        a.fillRect(0, 160, w, 12);
        a.fillRect(0, 197, w, 10);

        // Crowd
        for (int row = 0; row < 4; row++) {
            for (int i = 0; i < 120; i++) {
                int x = i * 9 + (row % 2) * 4;
                int y = 122 + row * 20;
                int shade = (i * 17 + row * 31) % 5;
                Color[] crowd = {
                    new Color(235, 80, 70), new Color(255, 190, 60),
                    new Color(75, 175, 255), Color.WHITE,
                    new Color(90, 220, 145)
                };
                a.setColor(crowd[shade]);
                a.fillOval(x, y, 5, 6);
            }
        }

        // Stadium floodlights
        for (int x : new int[]{55, 935}) {
            a.setColor(new Color(255, 247, 205, 45));
            a.fillOval(x - 45, 20, 90, 120);
            a.setColor(new Color(255, 245, 205));
            a.fillRoundRect(x - 25, 30, 50, 12, 5, 5);
            for (int j = 0; j < 5; j++) {
                a.fillOval(x - 20 + j * 10, 33, 5, 5);
            }
        }

        // Ground
        a.setPaint(new GradientPaint(0, 205, new Color(57, 155, 78),
                                     0, h, new Color(18, 92, 48)));
        a.fillRect(0, 205, w, h - 205);

        // Mowed grass stripes
        for (int i = 0; i < 10; i++) {
            a.setColor(new Color(255, 255, 255, 12));
            a.fillRect(i * 110, 210, 55, h - 210);
        }

        // Boundary
        a.setColor(new Color(235, 230, 190));
        a.setStroke(new BasicStroke(3));
        a.drawOval(45, 220, 910, 390);

        // Pitch shadow and pitch
        a.setColor(new Color(0, 0, 0, 40));
        a.fillRoundRect(340, 270, 320, 300, 20, 20);
        a.setPaint(new GradientPaint(340, 0, cream,
                                     660, 0, new Color(195, 155, 95)));
        a.fillRoundRect(350, 270, 300, 285, 8, 8);

        // Pitch texture
        a.setColor(new Color(130, 95, 56, 35));
        for (int y = 285; y < 550; y += 15)
            a.drawLine(365, y, 635, y);

        // Creases
        a.setColor(Color.WHITE);
        a.setStroke(new BasicStroke(3));
        a.drawLine(360, 380, 640, 380);
        a.drawLine(360, 500, 640, 500);

        // Bowler
        drawPlayer(a, 400, 320, new Color(38, 83, 210),
                   false, time);

        // Batsman
        drawPlayer(a, 615, 415, new Color(235, 40, 55),
                   true, time);

        // Stumps
        drawStumps(a, 610, 472);
        drawStumps(a, 375, 352);

        // Ball
        if (state == 1 || state == 2) {
            a.setColor(new Color(0, 0, 0, 50));
            a.fillOval(ballX + 4, ballY + 7, 15, 7);
            a.setColor(new Color(205, 20, 35));
            a.fillOval(ballX, ballY, 14, 14);
            a.setColor(Color.WHITE);
            a.drawArc(ballX + 4, ballY + 2, 5, 10, 70, 210);
        }

        // Scoreboard
        a.setColor(new Color(8, 20, 36, 235));
        a.fillRoundRect(18, 18, 340, 82, 15, 15);
        a.setColor(new Color(255, 198, 55));
        a.fillRoundRect(18, 18, 7, 82, 5, 5);

        a.setColor(Color.WHITE);
        a.setFont(new Font("Arial", Font.BOLD, 16));
        a.drawString("CRICKET PREMIER LEAGUE", 37, 42);
        a.setFont(new Font("Arial", Font.BOLD, 27));
        a.drawString(runs + "/" + wickets, 37, 75);
        a.setFont(new Font("Arial", Font.PLAIN, 14));
        a.drawString("OVERS " + (balls / 6) + "." + (balls % 6)
                     + "     TARGET " + target, 155, 72);

        // Game message
        a.setColor(new Color(0, 0, 0, 145));
        a.fillRoundRect(300, 110, 400, 45, 12, 12);
        a.setColor(new Color(255, 210, 65));
        a.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = a.getFontMetrics();
        a.drawString(message, 500 - fm.stringWidth(message) / 2, 139);

        // Shot celebration
        if (celebration > 0) {
            a.setColor(new Color(255, 225, 65));
            a.setFont(new Font("Arial", Font.BOLD, 40));
            a.drawString(lastShot, 420, 225);
        }

        // Bottom controls
        a.setColor(new Color(7, 18, 31, 220));
        a.fillRoundRect(18, h - 46, w - 36, 30, 10, 10);
        a.setColor(Color.WHITE);
        a.setFont(new Font("Arial", Font.PLAIN, 13));
        a.drawString("SPACE  HIT THE BALL", 35, h - 26);
        a.drawString("R  RESTART", 240, h - 26);
        a.drawString("CHASE " + target + " RUNS TO WIN", 760, h - 26);

        if (gameOver) {
            a.setColor(new Color(0, 0, 0, 190));
            a.fillRect(0, 0, w, h);
            a.setColor(new Color(255, 207, 55));
            a.setFont(new Font("Arial", Font.BOLD, 48));
            String title = runs >= target ? "YOU WIN!" : "MATCH OVER";
            a.drawString(title, 500 - a.getFontMetrics().stringWidth(title)/2,
                         285);
            a.setColor(Color.WHITE);
            a.setFont(new Font("Arial", Font.BOLD, 23));
            a.drawString("FINAL SCORE  " + runs + "/" + wickets,
                         365, 330);
            a.setFont(new Font("Arial", Font.PLAIN, 18));
            a.drawString("Press R to play again", 390, 370);
        }

        a.dispose();
    }

    void drawStumps(Graphics2D a, int x, int y) {
        a.setColor(new Color(235, 225, 205));
        for (int i = 0; i < 3; i++)
            a.fillRoundRect(x + i * 7, y, 4, 30, 3, 3);
        a.fillRoundRect(x - 2, y - 2, 25, 4, 3, 3);
    }

    void drawPlayer(Graphics2D a, int x, int y, Color shirt,
                    boolean batsman, double t) {
        int swing = batsman ? batSwing : 0;

        // Legs
        a.setColor(new Color(30, 38, 53));
        a.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND,
                                    BasicStroke.JOIN_ROUND));
        a.drawLine(x + 8, y + 38, x + 2, y + 65);
        a.drawLine(x + 17, y + 38, x + 26, y + 64);

        // Shoes
        a.setColor(Color.WHITE);
        a.fillRoundRect(x - 5, y + 60, 16, 7, 4, 4);
        a.fillRoundRect(x + 22, y + 60, 16, 7, 4, 4);

        // Body and head
        a.setColor(shirt);
        a.fillRoundRect(x - 1, y + 4, 30, 40, 9, 9);
        a.setColor(new Color(210, 155, 110));
        a.fillOval(x + 4, y - 18, 22, 25);

        // Helmet
        a.setColor(new Color(25, 35, 50));
        a.fillArc(x + 1, y - 23, 29, 26, 0, 180);

        // Arms
        a.setColor(new Color(210, 155, 110));
        a.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND,
                                    BasicStroke.JOIN_ROUND));
        a.drawLine(x + 3, y + 12, x - 5, y + 32);
        a.drawLine(x + 24, y + 12, x + 34, y + 26);

        // Bat held by batsman
        if (batsman) {
            a.setColor(new Color(120, 68, 31));
            a.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND,
                                        BasicStroke.JOIN_ROUND));
            int endX = x + 45 - swing;
            int endY = y + 50 - swing / 2;
            a.drawLine(x + 29, y + 27, endX, endY);
            a.setColor(new Color(215, 165, 95));
            a.setStroke(new BasicStroke(13, BasicStroke.CAP_ROUND,
                                        BasicStroke.JOIN_ROUND));
            a.drawLine(endX, endY, endX + 8, endY - 18);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time += 0.08;
        if (celebration > 0) celebration--;

        if (state == 1) {
            ballX += (int) ballSpeed;
            ballY += 1;

            if (ballX >= 605) {
                finishBall(true);
            }
        } else if (state == 2) {
            ballX += (int) shotVX;
            ballY += (int) shotVY;
            shotVY += 0.12;

            if (ballX > getWidth() - 25 || ballY > 570
                    || ballY < 180) {
                finishBall(false);
            }
        } else if (state == 3) {
            messageTimer--;
            if (messageTimer <= 0 && !gameOver) newBall();
        }

        if (batSwing > 0) batSwing -= 3;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            restart();
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE
                && !gameOver && state == 0) {
            state = 1;
            message = "WATCH THE BALL!";
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE
                && !gameOver && state == 1) {
            if (ballX >= 475 && ballX < 605) {
                int timing = 605 - ballX;
                shotRuns = timing < 25 ? 6 :
                           timing < 55 ? 4 :
                           timing < 85 ? 2 : 1;

                shotVX = 8 + (6 - shotRuns) * 0.4;
                shotVY = shotRuns == 6 ? -9 :
                         shotRuns == 4 ? -6 : -3;
                ballY = Math.max(235, ballY - 15);
                state = 2;
                batSwing = 35;
                message = "SHOT!";
            }
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cricket Premier League");
            CricketGame game = new CricketGame();
            frame.add(game);
            frame.pack();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            game.requestFocusInWindow();
        });
    }
}
