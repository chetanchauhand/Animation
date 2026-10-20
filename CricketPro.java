
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class CricketPro extends JFrame {

    Random random = new Random();
    Stadium stadium = new Stadium();

    JPanel root = new JPanel(new BorderLayout());
    JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
    JLabel info = new JLabel("Welcome to Cricket Pro", SwingConstants.CENTER);

    JButton[] shotButtons = new JButton[6];

    int oversLimit = 2;
    int innings = 1, balls = 0, score = 0, wickets = 0;
    int target = -1;
    int userScore = 0, computerScore = 0;
    int userWickets = 0, computerWickets = 0;
    int userBalls = 0, computerBalls = 0;

    boolean userBatting = true;
    boolean matchStarted = false;
    boolean deliveryRunning = false;
    boolean matchOver = false;

    int ballX = 360;
    int chosenShot = -1;
    String message = "Press TOSS to begin";
    Timer timer;

    CricketPro() {
        setTitle("Cricket Pro - Java Game");
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        root.setBackground(new Color(10, 25, 38));
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Arial", Font.BOLD, 18));
        info.setBorder(BorderFactory.createEmptyBorder(12, 5, 12, 5));

        root.add(info, BorderLayout.NORTH);
        root.add(stadium, BorderLayout.CENTER);
        root.add(controls, BorderLayout.SOUTH);
        setContentPane(root);

        showSetup();
        setVisible(true);
    }

    void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(27, 110, 87));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    void clearControls() {
        controls.removeAll();
        controls.setBackground(new Color(10, 25, 38));
    }

    void addButton(String text, Runnable action) {
        JButton b = new JButton(text);
        styleButton(b);
        b.addActionListener(e -> action.run());
        controls.add(b);
    }

    void showSetup() {
        matchStarted = false;
        clearControls();

        JLabel heading = new JLabel("SELECT MATCH OVERS");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 15));
        controls.add(heading);

        JComboBox<Integer> overs = new JComboBox<>(
            new Integer[]{2, 5, 10, 20}
        );
        overs.setFont(new Font("Arial", Font.BOLD, 15));
        controls.add(overs);

        addButton("TOSS", () -> {
            oversLimit = (int) overs.getSelectedItem();
            doToss();
        });

        info.setText("CRICKET PRO  |  Choose overs and toss the coin");
        message = "Welcome to the stadium!";
        stadium.repaint();
        controls.revalidate();
        controls.repaint();
    }

    void doToss() {
        clearControls();

        boolean userWonToss = random.nextBoolean();

        if (userWonToss) {
            info.setText("You won the toss! Choose your decision.");
            addButton("BAT FIRST", () -> startMatch(true));
            addButton("BOWL FIRST", () -> startMatch(false));
        } else {
            boolean computerBats = random.nextBoolean();
            info.setText("Computer won the toss and chose to "
                + (computerBats ? "BAT" : "BOWL") + " first.");

            Timer tossTimer = new Timer(1400, e -> {
                ((Timer) e.getSource()).stop();
                startMatch(!computerBats);
            });
            tossTimer.setRepeats(false);
            tossTimer.start();
        }

        message = userWonToss
            ? "Choose batting or bowling"
            : "Computer won the toss!";
        stadium.repaint();
        controls.revalidate();
        controls.repaint();
    }

    void startMatch(boolean batFirst) {
        innings = 1;
        balls = score = wickets = 0;
        target = -1;
        userScore = computerScore = 0;
        userWickets = computerWickets = 0;
        userBalls = computerBalls = 0;
        userBatting = batFirst;
        matchStarted = true;
        matchOver = false;
        message = "First innings begins!";
        showGameControls();
        updateScoreboard();
    }

    void showGameControls() {
        clearControls();

        if (!matchStarted || matchOver) {
            addButton("NEW MATCH", this::showSetup);
        } else if (deliveryRunning) {
            addButton("WAIT FOR BALL...", () -> {});
        } else if (userBatting) {
            String[] names = {"DEFEND", "RUN 1", "RUN 2",
                              "RUN 3", "FOUR", "SIX"};

            for (int i = 0; i < names.length; i++) {
                final int shot = i;
                addButton(names[i], () -> playShot(shot));
            }
        } else {
            addButton("FAST BALL", () -> bowl(0));
            addButton("SWING", () -> bowl(1));
            addButton("SPIN", () -> bowl(2));
        }

        addButton("RESTART", this::showSetup);
        controls.revalidate();
        controls.repaint();
        stadium.repaint();
    }

    void updateScoreboard() {
        String overText = (balls / 6) + "." + (balls % 6);

        String targetText = target < 0 ? ""
            : " | Target: " + target;

        info.setText(
            (innings == 1 ? "1ST INNINGS" : "2ND INNINGS")
            + " | " + (userBatting ? "YOU BAT" : "COMPUTER BATS")
            + " | " + score + "/" + wickets
            + " | Over " + overText + "/" + oversLimit
            + targetText
        );

        stadium.repaint();
    }

    void playShot(int shot) {
        if (!matchStarted || matchOver || deliveryRunning) return;

        chosenShot = shot;
        ballX = 350;
        deliveryRunning = true;
        message = "Ball coming... time your shot!";
        showGameControls();

        timer = new Timer(18, e -> {
            ballX += 5;

            if (ballX >= 585) {
                timer.stop();
                deliveryRunning = false;
                resolveBattingShot();
            }

            stadium.repaint();
        });

        timer.start();
    }

    void resolveBattingShot() {
        int timing = ballX;
        int runs = 0;
        boolean out = false;

        // The shot button is selected before the ball arrives.
        // Timing quality affects the result.
        int quality = Math.abs(timing - 585);

        if (chosenShot == 0) {
            runs = random.nextInt(2);
        } else if (quality > 65) {
            if (random.nextInt(100) < 25) {
                out = true;
            } else {
                runs = random.nextInt(2);
            }
        } else {
            switch (chosenShot) {
                case 1: runs = 1; break;
                case 2: runs = random.nextBoolean() ? 2 : 1; break;
                case 3: runs = random.nextBoolean() ? 3 : 2; break;
                case 4: runs = random.nextInt(100) < 80 ? 4 : 2; break;
                case 5: runs = random.nextInt(100) < 75 ? 6 : 4; break;
            }

            if (random.nextInt(100) < 5) out = true;
        }

        finishBall(runs, out);
    }

    void bowl(int type) {
        if (!matchStarted || matchOver || deliveryRunning) return;

        deliveryRunning = true;
        ballX = 350;
        message = type == 0 ? "Fast delivery!"
            : type == 1 ? "Swing delivery!" : "Spin delivery!";
        showGameControls();

        timer = new Timer(18, e -> {
            ballX += 5;

            if (ballX >= 585) {
                timer.stop();
                deliveryRunning = false;

                int chance = random.nextInt(100);
                int runs = 0;
                boolean out = false;

                if (chance < 12) {
                    out = true;
                } else if (chance < 35) {
                    runs = 0;
                } else if (chance < 58) {
                    runs = 1;
                } else if (chance < 72) {
                    runs = 2;
                } else if (chance < 82) {
                    runs = 3;
                } else if (chance < 94) {
                    runs = 4;
                } else {
                    runs = 6;
                }

                if (type == 2 && runs == 6) runs = 4;

                finishBall(runs, out);
            }

            stadium.repaint();
        });

        timer.start();
    }

    void finishBall(int runs, boolean out) {
        balls++;

        if (out) {
            wickets++;
            message = "WICKET! Great delivery!";
        } else {
            score += runs;
            message = runs == 0 ? "Dot ball!"
                : runs == 4 ? "FOUR!"
                : runs == 6 ? "SIX!"
                : runs + (runs == 1 ? " run!" : " runs!");
        }

        if (innings == 1) {
            if (userBatting) {
                userScore = score;
                userWickets = wickets;
                userBalls = balls;
            } else {
                computerScore = score;
                computerWickets = wickets;
                computerBalls = balls;
            }
        } else {
            if (userBatting) {
                userScore = score;
                userWickets = wickets;
                userBalls = balls;
            } else {
                computerScore = score;
                computerWickets = wickets;
                computerBalls = balls;
            }
        }

        boolean inningsFinished =
            balls >= oversLimit * 6 || wickets >= 10
            || (target > 0 && score >= target);

        if (inningsFinished) {
            if (innings == 1) {
                target = score + 1;
                innings = 2;
                score = 0;
                wickets = 0;
                balls = 0;
                userBatting = !userBatting;
                message = "Second innings! Target: " + target;
                showGameControls();
                updateScoreboard();
                return;
            } else {
                endMatch();
                return;
            }
        }

        updateScoreboard();
        showGameControls();
    }

    void endMatch() {
        matchOver = true;
        matchStarted = false;

        String result;

        if (userScore > computerScore) {
            result = "YOU WIN!";
        } else if (computerScore > userScore) {
            result = "COMPUTER WINS!";
        } else {
            result = "MATCH TIED!";
        }

        message = result;
        info.setText(result + "  |  You: " + userScore
            + "  Computer: " + computerScore);

        showGameControls();
        stadium.repaint();
    }

    class Stadium extends JPanel {
        Stadium() {
            setPreferredSize(new Dimension(900, 500));
            setBackground(new Color(12, 45, 52));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            int w = getWidth();
            int h = getHeight();

            // Night sky
            g2.setPaint(new GradientPaint(
                0, 0, new Color(8, 19, 43),
                0, h, new Color(24, 85, 79)
            ));
            g2.fillRect(0, 0, w, h);

            // Stadium stands
            g2.setColor(new Color(39, 54, 82));
            g2.fillRoundRect(35, 45, w - 70, 200, 45, 45);

            // Crowd
            Color[] crowd = {
                Color.RED, Color.YELLOW, Color.WHITE,
                Color.CYAN, Color.ORANGE, Color.PINK
            };

            for (int i = 0; i < 600; i++) {
                int x = 45 + randomFixed(i, 0, Math.max(1, w - 90));
                int y = 70 + randomFixed(i * 3, 0, 145);
                g2.setColor(crowd[i % crowd.length]);
                g2.fillOval(x, y, 3, 4);
            }

            // Floodlights
            g2.setColor(Color.WHITE);
            g2.fillOval(55, 25, 65, 14);
            g2.fillOval(w - 120, 25, 65, 14);

            // Field
            g2.setColor(new Color(28, 130, 71));
            g2.fillOval(45, 180, w - 90, h - 205);

            g2.setColor(new Color(55, 157, 81));
            g2.setStroke(new BasicStroke(3));

            for (int i = 0; i < 5; i++) {
                g2.drawArc(70 + i * 35, 205 + i * 12,
                    w - 140 - i * 70, h - 245 - i * 24,
                    0, 360);
            }

            // Pitch
            g2.setColor(new Color(191, 151, 99));
            g2.fillRoundRect(w / 2 - 75, 235, 150, 205, 12, 12);

            g2.setColor(new Color(244, 222, 173));
            g2.drawLine(w / 2 - 65, 260, w / 2 + 65, 260);
            g2.drawLine(w / 2 - 65, 415, w / 2 + 65, 415);

            // Players
            drawPlayer(g2, w / 2 - 15, 310, new Color(255, 220, 165));
            drawPlayer(g2, w / 2 + 65, 350, new Color(255, 220, 165));

            // Bat
            g2.setColor(new Color(225, 190, 120));
            g2.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
            g2.drawLine(w / 2 + 65, 352, w / 2 + 95, 320);

            // Stumps
            g2.setColor(Color.WHITE);
            for (int i = 0; i < 3; i++) {
                g2.fillRoundRect(w / 2 + 88 + i * 7, 395, 4, 28, 2, 2);
            }

            // Ball
            if (deliveryRunning) {
                g2.setColor(new Color(235, 36, 45));
                g2.fillOval(ballX, 365, 13, 13);
                g2.setColor(Color.WHITE);
                g2.drawArc(ballX + 3, 367, 6, 8, 40, 240);
            }

            // Message
            g2.setFont(new Font("Arial", Font.BOLD, 23));
            g2.setColor(Color.WHITE);
            drawCentered(g2, message, w / 2, 30);

            if (!matchStarted && !matchOver) {
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                drawCentered(g2, "TOSS • BAT • BOWL • WIN",
                    w / 2, h - 22);
            }

            if (matchOver) {
                g2.setColor(new Color(0, 0, 0, 170));
                g2.fillRoundRect(w / 2 - 190, h / 2 - 45,
                    380, 90, 25, 25);
                g2.setFont(new Font("Arial", Font.BOLD, 30));
                g2.setColor(Color.YELLOW);
                drawCentered(g2, message, w / 2, h / 2 + 10);
            }

            g2.dispose();
        }

        int randomFixed(int seed, int min, int max) {
            return min + Math.floorMod(seed * 7919 + 1237, max);
        }

        void drawCentered(Graphics2D g, String text, int x, int y) {
            FontMetrics fm = g.getFontMetrics();
            g.drawString(text, x - fm.stringWidth(text) / 2, y);
        }

        void drawPlayer(Graphics2D g, int x, int y, Color skin) {
            g.setColor(skin);
            g.fillOval(x, y, 22, 22);

            g.setColor(new Color(30, 70, 210));
            g.fillRoundRect(x - 2, y + 22, 26, 38, 8, 8);

            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(5));
            g.drawLine(x + 5, y + 55, x, y + 78);
            g.drawLine(x + 18, y + 55, x + 25, y + 78);

            g.setColor(skin);
            g.setStroke(new BasicStroke(6));
            g.drawLine(x, y + 28, x - 12, y + 48);
            g.drawLine(x + 22, y + 28, x + 32, y + 45);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CricketPro::new);
    }
}
