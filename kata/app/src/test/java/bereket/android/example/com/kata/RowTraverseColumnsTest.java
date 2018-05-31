package bereket.android.example.com.kata;

/**
 * Created by berekethaile on 5/31/18.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bereket.android.example.com.kata.grid_controller.CurrentPathStatus;
import bereket.android.example.com.kata.grid_controller.PathStateCollector;
import bereket.android.example.com.kata.grid_controller.RowTraverseColumns;
import bereket.android.example.com.kata.grid_model.Grid;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RowTraverseColumnsTest {

    private PathStateCollector realCollector;
    private PathStateCollector mockCollector;
    @Before
    public void setUp() {
        realCollector = new PathStateCollector();
        mockCollector = mock(PathStateCollector.class);

    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithoutAGrid() {
        RowTraverseColumns subject = new RowTraverseColumns(1, null, new PathStateCollector());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithoutACollector() {
        RowTraverseColumns subject = new RowTraverseColumns(1, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithRowNumberLessThanGridRows() {
        RowTraverseColumns subject = new RowTraverseColumns(0, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), new PathStateCollector());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithRowNumberGreaterThanGridRows() {
        RowTraverseColumns subject = new RowTraverseColumns(2, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), new PathStateCollector());
    }

    @Test
    public void getBestPathForRowAccumulatesCostAcrossEntireRow() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(15));
    }

    @Test
    public void getBestPathForRowDoesNotAccumumlateAnyCostIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { CurrentPathStatus.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(0));
    }

    @Test
    public void getBestPathForRowDoesNotAccumulateCostAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { CurrentPathStatus.MAXIMUM_COST, 1, 1, 1, 1 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(50));
    }

    @Test
    public void getBestPathForRowTraversesEntireRow() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, 0 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 1, 1, 1 })
        );

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowDoesNotTraverseAnyRowsIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { CurrentPathStatus.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getRowsTraversed().size(), equalTo(0));
    }

    @Test
    public void getBestPathForRowDoesNotTraverseRowsAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { CurrentPathStatus.MAXIMUM_COST, 1, 1, 1, 1 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1 })
        );

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowIsSuccessfulIfGridIsCompletelyTraversed() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, CurrentPathStatus.MAXIMUM_COST - 4 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfGridIsNotTraversedAtAll() {
        Grid grid = new Grid(new int[][]{ { CurrentPathStatus.MAXIMUM_COST + 1, 0, 0, 0, 0 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfGridIsPartiallyTraversed() {
        Grid grid = new Grid(new int[][]{ { CurrentPathStatus.MAXIMUM_COST, 1, 1, 1, 1 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfLastVisitCausesTotalCostToExceedMaximumCost() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, CurrentPathStatus.MAXIMUM_COST + 1 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowHandlesNegativeNumbers() {
        Grid grid = new Grid(new int[][]{ { -5, -5, -5, -5, -5 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, grid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(-25));
        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowVisitsOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 5, 6, 7, 8, 9 }, { 1, 2, 3, 4, 5 } });
        RowTraverseColumns subject = new RowTraverseColumns(2, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2, 2, 2, 2, 2 })
        );

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(15));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowHandlesMaximumCostForOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { CurrentPathStatus.MAXIMUM_COST - 1, 2, 1, 1, 1 } });
        RowTraverseColumns subject = new RowTraverseColumns(2, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2 })
        );

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(49));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowVisitsAllPathsFromThatRowThroughFullTwoRowGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 1, 1, 1 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, twoRowGrid, mockCollector);

        subject.getBestPathForRow();

        verify(mockCollector, times(16)).addPath(any(CurrentPathStatus.class));
    }

    @Test
    public void getBestPathForRowVisitsFewerPathsThroughGridWithHighCosts() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, CurrentPathStatus.MAXIMUM_COST, 1, 1, 1 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, twoRowGrid, mockCollector);

        subject.getBestPathForRow();

        verify(mockCollector, times(9)).addPath(any(CurrentPathStatus.class));
    }

    @Test
    public void getBestPathForRowReturnsLongerPathsAheadOfShorterPathsWithLowerCost() {
        Grid twoRowGrid = new Grid(new int[][]{
                { 1, 1, CurrentPathStatus.MAXIMUM_COST, CurrentPathStatus.MAXIMUM_COST, 1 },
                { 1, 10, 10, CurrentPathStatus.MAXIMUM_COST, 5 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 2 })
        );

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(12));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowVisitsAllPathsFromThatRowThroughFullThreeRowGrid() {
        Grid threeRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, threeRowGrid, mockCollector);

        subject.getBestPathForRow();

        verify(mockCollector, times(81)).addPath(any(CurrentPathStatus.class));
    }

    @Test
    public void getBestPathForRowCanWrapToFourthRowInFullFourRowGrid() {
        Grid fourRowGrid = new Grid(new int[][]{ { 1, 5, 5, 5, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 }, { 0, 1, 1, 1, 1 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, fourRowGrid, realCollector);

        CurrentPathStatus result = subject.getBestPathForRow();

        assertThat(result.getTotalCost(), equalTo(5));
    }

    @Test
    public void getBestPathForRowVisitsAllPossiblePathsFromThatRowThroughFullFourRowGrid() {
        Grid fourRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 }, { 0, 4, 4, 4, 4 } });
        RowTraverseColumns subject = new RowTraverseColumns(1, fourRowGrid, mockCollector);

        subject.getBestPathForRow();

        verify(mockCollector, times(81)).addPath(any(CurrentPathStatus.class));
    }
}
