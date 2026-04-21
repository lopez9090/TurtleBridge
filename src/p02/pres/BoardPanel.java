package p02.pres;

import p02.game.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BoardPanel extends JPanel {

    private final SevenSegmentDigit ones;
    private final SevenSegmentDigit tens;
    private final SevenSegmentDigit hundreds;
    private final Board board;

    private final Image backdrop;
    private final Image background;
    private final Image gradient;
    private final Image fix;
    private final Image unit;


    public BoardPanel(Board board) {
        this.board = board;

        try {
            backdrop = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Backdrop.png")));
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Background.png")));
            fix = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Fix.png")));
            gradient = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Gradient.png")));
            unit = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Unit.png")));
        } catch (IOException e) {
            throw new RuntimeException("Nie można wczytać tła", e);
        }

        setLayout(null);

        ones = new SevenSegmentDigit();
        tens = new SevenSegmentDigit();
        hundreds = new SevenSegmentDigit();

        ones.setBounds(120, 60, 40, 60);
        tens.setBounds(70, 60, 40, 60);
        hundreds.setBounds(20, 60, 40, 60);

        add(hundreds);
        add(tens);
        add(ones);

        board.setScore(ones, tens, hundreds);

        addKeyListener(board);
        setFocusable(true);
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backdrop, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(background, 635, 330, 652, 424, null);
        g.drawImage(fix, 635, 330, 652, 424, null);
        g.drawImage(gradient, 635, 330, 652, 424, null);
        g.drawImage(unit, 308, 146, 1306, 790, null);

        if (board.isGameOver()) {
            g.setColor(new Color(255, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String message = board.hasWon() ? "Wygrana" : "Koniec gry";
            g.drawString(message, getWidth() / 2 - 150, getHeight() - 400);
        }
    }
}
