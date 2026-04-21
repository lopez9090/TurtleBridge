package p02.pres;

import javax.swing.table.AbstractTableModel;

public class BoardTableModel extends AbstractTableModel {

    private final int[][] grid;

    private final int[][] cachedGrid = new int[12][5];

    public BoardTableModel(int[][] grid) {
        this.grid = grid;
    }

    @Override
    public int getRowCount() {
        return grid[0].length;
    }

    @Override
    public int getColumnCount() {
        return grid.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return grid[col][row];
    }


    public void updateCell(int x, int y) {
        int currentValue = grid[x][y];
        if (cachedGrid[x][y] != currentValue) {
            cachedGrid[x][y] = currentValue;
            fireTableCellUpdated(y, x);
        }
    }

}
