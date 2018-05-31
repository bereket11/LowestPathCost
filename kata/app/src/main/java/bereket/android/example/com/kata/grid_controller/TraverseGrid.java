package bereket.android.example.com.kata.grid_controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bereket.android.example.com.kata.grid_model.Grid;

/**
 * Created by berekethaile on 5/31/18.
 */

public class TraverseGrid {
    private Grid mGrid;
    private ComparePaths mComparePaths;

    public TraverseGrid(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("A visitor requires a mGrid");
        }

        this.mGrid = grid;
        mComparePaths = new ComparePaths();
    }

    public CurrentPathStatus getBestPathForGrid() {
        List<CurrentPathStatus> allPaths = new ArrayList<>();
        for (int row = 1; row <= mGrid.getRowCount(); row++) {
            RowTraverseColumns visitor = new RowTraverseColumns(row, mGrid, new PathStateCollector());
            allPaths.add(visitor.getBestPathForRow());
        }

        Collections.sort(allPaths, mComparePaths);

        return allPaths.get(0);
    }
}
