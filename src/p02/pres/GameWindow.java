package p02.pres;

import p02.game.Board;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle("Turtle Bridge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setLayout(null);

        Board board = new Board();
        BoardPanel boardPanel = new BoardPanel(board);
        BoardTableModel model = new BoardTableModel(board.getGrid());
        JTable table = new JTable(model);

        board.setPanel(boardPanel);
        board.setTableModel(model);

        boardPanel.setBounds(0, 0, 1920, 1080);
        boardPanel.setLayout(null);
        boardPanel.add(table);

        add(boardPanel);

        table.setDefaultRenderer(Object.class, new ShapeRenderer());
        table.setRowHeight(300 / 5);
        table.setBounds(650, 430, 600, 300);
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);

        addKeyListener(board);
        setFocusable(true);
        requestFocusInWindow();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
