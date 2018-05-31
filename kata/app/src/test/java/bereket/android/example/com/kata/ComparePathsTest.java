package bereket.android.example.com.kata;

/**
 * Created by berekethaile on 5/31/18.
 */

import org.junit.Before;
import org.junit.Test;

import bereket.android.example.com.kata.grid_controller.CurrentPathStatus;
import bereket.android.example.com.kata.grid_controller.ComparePaths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ComparePathsTest {

    private ComparePaths subject;

    private CurrentPathStatus lowerCostPath;
    private CurrentPathStatus higherCostPath;

    private CurrentPathStatus shorterPath;
    private CurrentPathStatus longerPath;

    @Before
    public void setUp() {
        subject = new ComparePaths();

        lowerCostPath = new CurrentPathStatus(1);
        lowerCostPath.addRowWithCost(1, 1);

        higherCostPath = new CurrentPathStatus(1);
        higherCostPath.addRowWithCost(1, 10);

        shorterPath = new CurrentPathStatus(3);
        shorterPath.addRowWithCost(1, 9);

        longerPath = new CurrentPathStatus(3);
        longerPath.addRowWithCost(1, 5);
        longerPath.addRowWithCost(1, 5);
    }

    @Test
    public void returnsNegativeOneIfFirstPathIsLongerThanSecond() {
        assertThat(subject.compare(longerPath, shorterPath), equalTo(-1));
    }

    @Test
    public void returnsPositiveOneIfFirstPathIsShorterThanSecond() {
        assertThat(subject.compare(shorterPath, longerPath), equalTo(1));
    }

    @Test
    public void returnsZeroIfPathsHaveSameLengthAndCost() {
        assertThat(subject.compare(shorterPath, shorterPath), equalTo(0));
    }

    @Test
    public void returnsNegativeOneIfFirstPathHasLowerCostThanSecondWithTheSameLength() {
        assertThat(subject.compare(lowerCostPath, higherCostPath), equalTo(-1));
    }

    @Test
    public void returnsPositiveOneIfFirstPathHasLowerCostThanSecondWithTheSameLength() {
        assertThat(subject.compare(higherCostPath, lowerCostPath), equalTo(1));
    }

    @Test
    public void returnsPositiveOneIfFirstPathIsNull() {
        assertThat(subject.compare(null, lowerCostPath), equalTo(1));
    }

    @Test
    public void returnsNegativeOneIfLastPathIsNull() {
        assertThat(subject.compare(lowerCostPath, null), equalTo(-1));
    }

    @Test
    public void returnsZeroIfBothPathsAreNull() {
        assertThat(subject.compare(null, null), equalTo(0));
    }
}
