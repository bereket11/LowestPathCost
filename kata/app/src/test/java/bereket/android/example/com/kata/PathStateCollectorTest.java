package bereket.android.example.com.kata;

/**
 * Created by berekethaile on 5/31/18.
 */

import org.junit.Before;
import org.junit.Test;

import bereket.android.example.com.kata.grid_controller.CurrentPathStatus;
import bereket.android.example.com.kata.grid_controller.PathStateCollector;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class PathStateCollectorTest {

    private PathStateCollector collector;

    @Before
    public void setUp() {
        collector = new PathStateCollector();
    }

    @Test
    public void collectorReturnsNullIfNoPathStatesHaveBeenAdded() {
        assertThat(collector.getBestPath(), is(nullValue()));
    }

    @Test
    public void collectorReturnsPathAddedIfOnlyOnePathAdded() {
        CurrentPathStatus expectedPath = new CurrentPathStatus(5);
        expectedPath.addRowWithCost(1, 5);

        collector.addPath(expectedPath);

        assertThat(collector.getBestPath(), equalTo(expectedPath));
    }

    @Test
    public void collectorReturnsLongerPathOfTwoPathsAdded() {
        CurrentPathStatus longerPath = new CurrentPathStatus(5);
        longerPath.addRowWithCost(1, 5);
        longerPath.addRowWithCost(1, 5);
        CurrentPathStatus shorterPath = new CurrentPathStatus(5);
        shorterPath.addRowWithCost(1, 10);

        collector.addPath(longerPath);
        collector.addPath(shorterPath);

        assertThat(collector.getBestPath(), equalTo(longerPath));
    }

    @Test
    public void collectorReturnsLowerCostPathOfTwoEqualLengthPathsAdded() {
        CurrentPathStatus lowCostPath = new CurrentPathStatus(5);
        lowCostPath.addRowWithCost(1, 5);
        lowCostPath.addRowWithCost(1, 5);
        CurrentPathStatus highCostPath = new CurrentPathStatus(5);
        highCostPath.addRowWithCost(1, 10);
        highCostPath.addRowWithCost(1, 20);

        collector.addPath(lowCostPath);
        collector.addPath(highCostPath);

        assertThat(collector.getBestPath(), equalTo(lowCostPath));
    }

    @Test
    public void collectorPrefersExistingPathIfPathsAreEqual() {
        CurrentPathStatus firstPath = new CurrentPathStatus(5);
        firstPath.addRowWithCost(1, 5);
        firstPath.addRowWithCost(1, 5);
        CurrentPathStatus secondPath = new CurrentPathStatus(5);
        secondPath.addRowWithCost(1, 5);
        secondPath.addRowWithCost(1, 5);

        collector.addPath(firstPath);
        collector.addPath(secondPath);

        assertThat(collector.getBestPath(), equalTo(firstPath));
    }
}