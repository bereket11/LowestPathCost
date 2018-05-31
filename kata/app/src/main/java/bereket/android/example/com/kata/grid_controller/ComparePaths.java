package bereket.android.example.com.kata.grid_controller;

import java.util.Comparator;

/**
 * Created by berekethaile on 5/31/18.
 */

public class ComparePaths implements Comparator<CurrentPathStatus> {

    private static int SORT_LEFT_FIRST = -1;
    private static int SORT_RIGHT_FIRST = 1;
    private static int SORT_EQUAL = 0;

    @Override
    public int compare(CurrentPathStatus leftPath, CurrentPathStatus rightPath) {
        int comparedLength = compareLengths(leftPath, rightPath);
        return (comparedLength != 0) ? comparedLength : compareCosts(leftPath, rightPath);
    }

    private int compareLengths(CurrentPathStatus leftPath, CurrentPathStatus rightPath) {
        int leftLength = getLengthFromPath(leftPath);
        int rightLength = getLengthFromPath(rightPath);

        return (leftLength > rightLength) ? SORT_LEFT_FIRST : (leftLength == rightLength) ? SORT_EQUAL : SORT_RIGHT_FIRST;
    }

    private int compareCosts(CurrentPathStatus leftPath, CurrentPathStatus rightPath) {
        int leftCost = getCostFromPath(leftPath);
        int rightCost = getCostFromPath(rightPath);

        return (leftCost < rightCost) ? SORT_LEFT_FIRST : (leftCost == rightCost) ? SORT_EQUAL : SORT_RIGHT_FIRST;
    }

    private int getLengthFromPath(CurrentPathStatus path) {
        if (path != null) {
            return path.getPathLength();
        } else {
            return Integer.MIN_VALUE;
        }
    }

    private int getCostFromPath(CurrentPathStatus path) {
        if (path != null) {
            return path.getTotalCost();
        } else {
            return Integer.MAX_VALUE;
        }
    }
}

