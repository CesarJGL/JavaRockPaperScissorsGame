import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RockPaperScissors extends JFrame {
    private int playerScore = 0;
    private int computerScore = 0;
    private int ties = 0;
    private final JLabel resultLabel;
    private final JLabel scoreLabel;
    private final DrawPanel playerPanel;
    private final DrawPanel computerPanel;

    public RockPaperScissors() {
        setTitle("Rock Paper Scissors");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout());

        // Create buttons for player moves
        JButton rockButton = new JButton("Rock");
        JButton paperButton = new JButton("Paper");
        JButton scissorsButton = new JButton("Scissors");
        JButton rulesButton = new JButton("Rules");

        // Create labels for displaying results and scores
        resultLabel = new JLabel("Choose your move!");
        scoreLabel = new JLabel("Score - You: 0 | Computer: 0 | Ties: 0");

        // Set fonts for labels
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Create panels for displaying player and computer choices
        playerPanel = new DrawPanel();
        computerPanel = new DrawPanel();
        playerPanel.setPreferredSize(new Dimension(100, 100));
        computerPanel.setPreferredSize(new Dimension(100, 100));

        // Add action listeners to buttons
        rockButton.addActionListener(e -> playGame("r"));
        paperButton.addActionListener(e -> playGame("p"));
        scissorsButton.addActionListener(e -> playGame("s"));
        rulesButton.addActionListener(e -> showRules());

        // Add components to the frame
        add(rockButton);
        add(paperButton);
        add(scissorsButton);
        add(rulesButton);
        add(resultLabel);
        add(scoreLabel);
        add(playerPanel);
        add(computerPanel);

        setVisible(true);
    }

    // Method to play the game
    private void playGame(String playerMove) {
        String computerMove = getComputerMove();
        String result = determineWinner(playerMove, computerMove);

        // Update scores
        if (result.equals("You win!")) playerScore++;
        else if (result.equals("You lose!")) computerScore++;
        else ties++;

        // Update labels
        resultLabel.setText("Computer played: " + moveToString(computerMove) + " | " + result);
        scoreLabel.setText("Score - You: " + playerScore + " | Computer: " + computerScore + " | Ties: " + ties);

        // Repaint the panels to show player and computer choices
        playerPanel.setMove(playerMove);
        computerPanel.setMove(computerMove);
        playerPanel.repaint();
        computerPanel.repaint();
    }

    // Method to get the computer's move
    private String getComputerMove() {
        String[] moves = {"r", "p", "s"};
        return moves[new Random().nextInt(moves.length)];
    }

    // Method to determine the winner
    private String determineWinner(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove)) {
            return "It's a tie!";
        } else if ((playerMove.equals("r") && computerMove.equals("s")) ||
                (playerMove.equals("p") && computerMove.equals("r")) ||
                (playerMove.equals("s") && computerMove.equals("p"))) {
            return "You win!";
        } else {
            return "You lose!";
        }
    }

    // Method to convert moves to strings for display
    private String moveToString(String move) {
        return switch (move) {
            case "r" -> "Rock";
            case "p" -> "Paper";
            case "s" -> "Scissors";
            default -> "";
        };
    }

    // Method to show rules
    private void showRules() {
        JOptionPane.showMessageDialog(this,
                """
                        Game Rules:
                        1. Rock beats Scissors.
                        2. Scissors beats Paper.
                        3. Paper beats Rock.
                        4. If both players choose the same move, it's a tie.""");
    }

    // Inner class for drawing the player's and computer's moves
    private static class DrawPanel extends JPanel {
        private String move = ""; // Move to draw

        public void setMove(String move) {
            this.move = move; // Set the move to draw
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            switch (move) {
                case "r" -> {
                    // Draw Rock as a filled oval
                    g.setColor(Color.GRAY);
                    g.fillOval(10, 10, 80, 80);
                }
                case "p" -> {
                    // Draw Paper as a rectangle
                    g.setColor(Color.WHITE);
                    g.fillRect(10, 10, 80, 80);
                    g.setColor(Color.BLACK);
                    g.drawRect(10, 10, 80, 80); // Draw border for Paper
                }
                case "s" -> {
                    // Draw Scissors as intersecting lines
                    g.setColor(Color.RED);
                    g.drawLine(10, 10, 90, 90); // First line of scissors

                    g.drawLine(90, 10, 10, 90); // Second line of scissors
                }
            }
        }
    }

    public static void main(String[] args) {
        new RockPaperScissors();
    }
}