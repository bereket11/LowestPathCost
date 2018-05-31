package bereket.android.example.com.kata.grid_controller;

import java.util.List;

import bereket.android.example.com.kata.grid_model.Grid;

/**
 * Created by berekethaile on 5/31/18.
 */

public class RowTraverseColumns {

    private int row;
    private Grid grid;
    private PathStateCollector pathCollector;

    public RowTraverseColumns(int startRow, Grid grid, PathStateCollector collector) {
        if (grid == null) {
            throw new IllegalArgumentException("A visitor requires a grid");
        } else if (collector == null) {
            throw new IllegalArgumentException("A visitor requires a collector");
        } else if (startRow <= 0 || startRow > grid.getRowCount()) {
            throw new IllegalArgumentException("Cannot visit a row outside of grid boundaries");
        }

        this.row = startRow;
        this.grid = grid;
        this.pathCollector = collector;
    }

    public CurrentPathStatus getBestPathForRow() {
        CurrentPathStatus initialPath = new CurrentPathStatus(grid.getColumnCount());

        visitPathsForRow(row, initialPath);

        return pathCollector.getBestPath();
    }

    private void visitPathsForRow(int row, CurrentPathStatus path) {
        if (canVisitRowOnPath(row, path)) {
            visitRowOnPath(row, path);
        }

        List<Integer> adjacentRows = grid.getRowsAdjacentTo(row);
        boolean currentPathAdded = false;

        for (int adjacentRow : adjacentRows) {
            if (canVisitRowOnPath(adjacentRow, path)) {
                CurrentPathStatus pathCopy = new CurrentPathStatus(path);
                visitPathsForRow(adjacentRow, pathCopy);
            } else if (!currentPathAdded) {
                pathCollector.addPath(path);
                currentPathAdded = true;
            }
        }
    }

    private boolean canVisitRowOnPath(int row, CurrentPathStatus path) {
        return !path.isComplete() && !nextVisitOnPathWouldExceedMaximumCost(row, path);
    }

    private void visitRowOnPath(int row, CurrentPathStatus path) {
        int columnToVisit = path.getPathLength() + 1;
        path.addRowWithCost(row, grid.getValueForRowAndColumn(row, columnToVisit));
    }

    private boolean nextVisitOnPathWouldExceedMaximumCost(int row, CurrentPathStatus path) {
        int nextColumn = path.getPathLength() + 1;
        return (path.getTotalCost() + grid.getValueForRowAndColumn(row, nextColumn)) > CurrentPathStatus.MAXIMUM_COST;
    }
}

