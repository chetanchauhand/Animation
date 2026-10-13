
import javax.swing.*;
import java.awt.*;

public class CyberSecuritySimulator extends JFrame {
    private final JProgressBar progress = new JProgressBar(0, 100);
    private final JLabel status = new JLabel("Ready to scan");
    private final JTextArea log = new JTextArea();
    private final JButton start = new JButton("Start Security Scan");
    private Timer timer;
    private int value = 0;

    public CyberSecuritySimulator() {
        setTitle("Cyber Security Simulator");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(15, 25, 40));
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("  CYBER DEFENSE CENTER");
        heading.setFont(new Font("Monospaced", Font.BOLD, 23));
        heading.setForeground(new Color(50, 255, 150));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 5));
        add(heading, BorderLayout.NORTH);

        log.setEditable(false);
        log.setBackground(new Color(5, 15, 25));
        log.setForeground(new Color(50, 255, 150));
        log.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(log), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(3, 1, 5, 5));
        bottom.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        bottom.setBackground(new Color(15, 25, 40));

        progress.setStringPainted(true);
        progress.setForeground(new Color(50, 200, 120));
        status.setForeground(Color.WHITE);
        start.addActionListener(e -> beginScan());

        bottom.add(status);
        bottom.add(progress);
        bottom.add(start);
        add(bottom, BorderLayout.SOUTH);

        timer = new Timer(100, e -> {
            value += 2;
            progress.setValue(value);

            if (value % 10 == 0) {
                log.append("Checking simulated module "
                        + (value / 10) + " ... OK\n");
            }

            if (value >= 100) {
                timer.stop();
                status.setText("Scan completed: No simulated threats found");
                log.append("\nSCAN COMPLETE\n");
                log.append("Demo result: System status looks safe.\n");
                start.setEnabled(true);
            }
        });
    }

    private void beginScan() {
        value = 0;
        progress.setValue(0);
        log.setText("Initializing simulated security scan...\n");
        status.setText("Scanning demo modules...");
        start.setEnabled(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new CyberSecuritySimulator().setVisible(true));
    }
}
