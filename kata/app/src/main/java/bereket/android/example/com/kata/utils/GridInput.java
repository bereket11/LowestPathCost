package bereket.android.example.com.kata.utils;

import bereket.android.example.com.kata.grid_model.Grid;

/**
 * Created by berekethaile on 5/31/18.
 */

public class GridInput {
    public static final Grid SAMPLE_1 = new Grid(new int[][]{
            { 3, 4, 1, 2, 8, 6 },
            { 6, 1, 8, 2, 7, 4 },
            { 5, 9, 3, 9, 9, 5 },
            { 8, 4, 1, 3, 2, 6 },
            { 3, 7, 2, 8, 6, 4 }
    });
    public static final Grid SAMPLE_2 = new Grid(new int[][]{
            { 3, 4, 1, 2, 8, 6 },
            { 6, 1, 8, 2, 7, 4 },
            { 5, 9, 3, 9, 9, 5 },
            { 8, 4, 1, 3, 2, 6 },
            { 3, 7, 2, 1, 2, 3 }
    });
    public static final Grid SAMPLE_3 = new Grid(new int[][]{
            { 19, 10, 19, 10, 19 },
            { 21, 23, 20, 19, 12 },
            { 20, 12, 20, 11, 10 }
    });

    public static final Grid SAMPLE_4 = new Grid(new int[][]{
            { 5, 8, 5, 3, 5 },
    });

    public static int[][] gridArrayFromString(String input) {
        if (input != null) {
            String[] rows = input.split("\n");
            String[] firstColumns = rows[0].split("\\s+");
            int[][] output = new int[rows.length][firstColumns.length];

            try {
                for (int row = 0; row < rows.length; row++) {
                    String[] columns = rows[row].split("\\s+");
                    for (int column = 0; column < columns.length; column++) {
                        if (column < output[0].length) {
                            output[row][column] = Integer.valueOf(columns[column]);
                        }
                    }
                }

                return output;
            } catch (NumberFormatException nfe) {
                return new int[0][0];
            }
        } else {
            return new int[0][0];
        }
    }

}
