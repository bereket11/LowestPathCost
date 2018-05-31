package bereket.android.example.com.kata.grid_controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by berekethaile on 5/31/18.
 */

public class CurrentPathStatus {
    public static int MAXIMUM_COST = 50;

    private List<Integer> rowsTraversed = new ArrayList<>();
    private int totalCost = 0;
    private int expectedLength = 0;

    public CurrentPathStatus(int expectedLength) {
        this.expectedLength = expectedLength;
    }

    CurrentPathStatus(CurrentPathStatus anotherPathState) {
        this.totalCost = anotherPathState.totalCost;
        this.expectedLength = anotherPathState.expectedLength;
        for (int rowTraversed : anotherPathState.rowsTraversed) {
            this.rowsTraversed.add(rowTraversed);
        }
    }

    public List<Integer> getRowsTraversed() {
        return rowsTraversed;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getPathLength() {
        return rowsTraversed.size();
    }

    public void addRowWithCost(int row, int cost) {
        rowsTraversed.add(row);
        totalCost += cost;
    }

    public boolean isComplete() {
        return rowsTraversed.size() == expectedLength;
    }

    public boolean isSuccessful() {
        return isComplete() && !isOverCost();
    }

    public boolean isOverCost() {
        return totalCost > MAXIMUM_COST;
    }
}
