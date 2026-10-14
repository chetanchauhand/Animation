
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class DurgaDivineLight extends JPanel {

    private final List<Path2D.Double> sketch = new ArrayList<>();
    private  Timer timer;
    private double progress = 0;

    public DurgaDivineLight() {
        setPreferredSize(new Dimension(900, 700));
        setBackground(new Color(250, 246, 236));

        createSketch();

        timer = new Timer(35, e -> {
            progress += 0.018;
            if (progress > sketch.size()) {
                progress = sketch.size();
                timer.stop();
            }
            repaint();
        });
        timer.start();
    }

    private void add(Path2D.Double p) {
        sketch.add(p);
    }

    private Path2D.Double path(double... pts) {
        Path2D.Double p = new Path2D.Double();
        p.moveTo(pts[0], pts[1]);

        for (int i = 2; i < pts.length; i += 2) {
            p.lineTo(pts[i], pts[i + 1]);
        }

        return p;
    }

    private void createSketch() {
        // Crown outline
        add(path(365, 225, 345, 155, 375, 175, 405, 100,
                 435, 145, 455, 80, 480, 145, 515, 105,
                 530, 180, 560, 155, 545, 230));

        // Crown decorations
        add(path(380, 180, 405, 195, 430, 160, 455, 185,
                 480, 125, 500, 175, 520, 155, 540, 190));
        add(path(390, 205, 420, 215, 450, 205, 480, 215,
                 510, 205, 535, 210));
        add(path(405, 180, 420, 195, 435, 180));
        add(path(465, 175, 480, 195, 495, 175));

        // Hair outline
        add(path(365, 225, 325, 245, 310, 295, 320, 345,
                 300, 390, 325, 445, 355, 465));
        add(path(545, 225, 585, 245, 600, 295, 590, 345,
                 610, 390, 585, 445, 555, 465));

        // Face outline
        add(path(365, 225, 355, 270, 360, 320, 380, 370,
                 415, 400, 450, 410));
        add(path(545, 225, 555, 270, 550, 320, 530, 370,
                 495, 400, 450, 410));

        // Eyebrows
        add(path(375, 285, 395, 270, 425, 278));
        add(path(475, 278, 505, 270, 525, 285));

        // Eyes
        add(path(375, 300, 395, 288, 420, 300, 430, 310,
                 410, 320, 390, 315, 375, 300));
        add(path(470, 310, 480, 300, 505, 288, 525, 300,
                 510, 315, 490, 320, 470, 310));

        // Pupils
        add(path(398, 300, 400, 310));
        add(path(500, 300, 500, 310));

        // Nose
        add(path(450, 305, 440, 335, 430, 350, 450, 355));
        add(path(450, 355, 465, 350));

        // Lips
        add(path(420, 370, 435, 363, 450, 368,
                 465, 363, 480, 370));
        add(path(420, 370, 438, 382, 450, 384,
                 462, 382, 480, 370));

        // Neck and shoulders
        add(path(405, 395, 405, 430, 380, 445));
        add(path(495, 395, 495, 430, 520, 445));
        add(path(380, 445, 345, 465, 330, 510));
        add(path(520, 445, 555, 465, 570, 510));

        // Necklace
        add(path(400, 425, 420, 445, 450, 450,
                 480, 445, 500, 425));
        add(path(420, 445, 435, 465, 450, 475,
                 465, 465, 480, 445));

        // Trishul
        add(path(640, 400, 640, 135));
        add(path(640, 135, 620, 165, 625, 205));
        add(path(640, 135, 660, 165, 655, 205));
        add(path(640, 135, 640, 90));
        add(path(620, 165, 640, 180, 660, 165));
        add(path(610, 400, 670, 400));

        // Lotus-inspired base
        add(path(350, 550, 380, 525, 410, 550,
                 450, 520, 490, 550, 520, 525, 550, 550));
        add(path(350, 550, 390, 575, 450, 585,
                 510, 575, 550, 550));

        // Sketch shading strokes
        for (int i = 0; i < 7; i++) {
            add(path(330 + i * 8, 280, 345 + i * 7, 320));
            add(path(550 + i * 5, 280, 540 + i * 7, 320));
        }

        // Extra crown and clothing details
        add(path(375, 230, 400, 240, 425, 230,
                 450, 240, 475, 230, 500, 240, 535, 230));
        add(path(345, 465, 375, 480, 405, 490));
        add(path(555, 465, 525, 480, 495, 490));
    }

    private void drawPartial(Graphics2D g, Shape shape, double amount) {
        if (amount <= 0) return;

        if (amount >= 1) {
            g.draw(shape);
            return;
        }

        PathIterator it = new FlatteningPathIterator(
                shape.getPathIterator(null), 2.0);

        double[] c = new double[6];
        List<Point2D.Double> points = new ArrayList<>();

        while (!it.isDone()) {
            int type = it.currentSegment(c);

            if (type == PathIterator.SEG_MOVETO ||
                type == PathIterator.SEG_LINETO) {
                points.add(new Point2D.Double(c[0], c[1]));
            }
            it.next();
        }

        if (points.size() < 2) return;

        double total = 0;
        for (int i = 1; i < points.size(); i++) {
            total += points.get(i - 1).distance(points.get(i));
        }

        double target = total * amount;
        double travelled = 0;

        Path2D.Double partial = new Path2D.Double();
        partial.moveTo(points.get(0).x, points.get(0).y);

        for (int i = 1; i < points.size(); i++) {
            Point2D.Double a = points.get(i - 1);
            Point2D.Double b = points.get(i);
            double length = a.distance(b);

            if (travelled + length <= target) {
                partial.lineTo(b.x, b.y);
                travelled += length;
            } else {
                double remaining = target - travelled;
                if (length > 0) {
                    double t = remaining / length;
                    partial.lineTo(
                        a.x + (b.x - a.x) * t,
                        a.y + (b.y - a.y) * t
                    );
                }
                break;
            }
        }

        g.draw(partial);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        // Paper texture and warm background
        g.setColor(new Color(250, 246, 236));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Soft golden aura
        int alpha = 20 + (int)(10 * Math.sin(progress * 0.15));
        g.setColor(new Color(218, 166, 66, alpha));
        g.fillOval(270, 55, 370, 500);

        // Decorative border
        g.setColor(new Color(190, 151, 90, 120));
        g.setStroke(new BasicStroke(1.2f));
        g.drawRoundRect(18, 18, getWidth() - 36,
                        getHeight() - 36, 22, 22);

        // Pencil sketch appearance
        g.setColor(new Color(75, 62, 53));
        g.setStroke(new BasicStroke(
            1.7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int complete = (int) progress;
        double current = progress - complete;

        for (int i = 0; i < sketch.size(); i++) {
            if (i < complete) {
                drawPartial(g, sketch.get(i), 1);
            } else if (i == complete) {
                drawPartial(g, sketch.get(i), current);
                break;
            } else {
                break;
            }
        }

        // Heading appears after the sketch is nearly complete
        if (progress > sketch.size() * 0.75) {
            g.setFont(new Font("Serif", Font.BOLD, 27));
            g.setColor(new Color(135, 65, 42));
            g.drawString("JAI MATA DI", 350, 630);

            g.setFont(new Font("Serif", Font.ITALIC, 15));
            g.setColor(new Color(115, 94, 70));
            g.drawString("A Divine Pencil Sketch", 365, 655);
        }

        g.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maa Durga - Sketch Animation");
            DurgaDivineLight panel = new DurgaDivineLight();

            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
