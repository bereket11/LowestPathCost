package bereket.android.example.com.kata.grid_controller;

/**
 * Created by berekethaile on 5/31/18.
 */

public class PathStateCollector {

    private CurrentPathStatus bestPath;
    private ComparePaths comparator;

    public PathStateCollector() {
        comparator = new ComparePaths();
    }

    public CurrentPathStatus getBestPath() {
        return bestPath;
    }

    public void addPath(CurrentPathStatus newPath) {
        if (comparator.compare(newPath, bestPath) < 0) {
            bestPath = newPath;
        }
    }
}
