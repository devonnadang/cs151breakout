package breakout.view;

/**
 * This class opens a new save score window when initialized.
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import breakout.controller.Message;
import breakout.controller.SaveScoreMessage;

public class SaveScoreView extends JFrame {

    private BlockingQueue<Message> queue;

    private JLabel scoreLabel;
    private JLabel theScore;
    private JLabel usernameLabel;
    private JTextField enterUsername;
    private JButton save;

    public SaveScoreView(BlockingQueue<Message> queue, int score) {
        this.queue = queue;

        this.scoreLabel = new JLabel("Score: ");
        this.theScore = new JLabel(String.valueOf(score) + "."); // displays current score

        this.usernameLabel = new JLabel("Enter username: ");
        this.enterUsername = new JTextField(10);

        this.save = new JButton("Save Username");
        save.addActionListener(e -> {
            try {
                SaveScoreMessage ssm = new SaveScoreMessage(score, enterUsername.getText());
                queue.put(ssm);
                this.dispose();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        });

        this.add(scoreLabel);
        this.add(theScore);
        this.add(usernameLabel);
        this.add(enterUsername);
        this.add(save);

        this.setPreferredSize(new Dimension(500, 600));
        this.setLayout(new FlowLayout());
        this.setResizable(true);
        this.setTitle("Save Score");

        // closes the window but does not exit the entire program
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

}
