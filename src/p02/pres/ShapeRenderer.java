package p02.pres;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ShapeRenderer extends JPanel implements TableCellRenderer {

    private int value;

    public ShapeRenderer() {
        setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.value = (value instanceof Integer) ? (int) value : 0;
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (value) {
            case 1 -> {
                g.setColor(Color.BLUE);
                g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
            }
            case 2 -> {
                g.setColor(Color.GREEN);
                g.fillRect(5, 5, getWidth() - 10, getHeight() - 10);
            }
            case 3 -> {
                g.setColor(Color.CYAN);
                int[] x = {getWidth() / 2, 5, getWidth() - 5};
                int[] y = {5, getHeight() - 5, getHeight() - 5};
                g.fillPolygon(x, y, 3);
            }
        }
    }
}
