package bereket.android.example.com.kata.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import bereket.android.example.com.kata.grid_model.Grid;
import bereket.android.example.com.kata.utils.GridInput;
import bereket.android.example.com.kata.grid_controller.TraverseGrid;
import bereket.android.example.com.kata.grid_controller.CurrentPathStatus;
import bereket.android.example.com.kata.R;

/**
 * Created by berekethaile on 5/31/18.
 */

public class HardCodedDataFragment extends Fragment {
    private Grid mGrid;

    public HardCodedDataFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.hard_code_fragment, container, false);

        Button gridOneButton =  fragmentView.findViewById(R.id.grid_1);
        gridOneButton.setOnClickListener(new GridOnClickListener());
        Button gridTwoButton =  fragmentView.findViewById(R.id.grid_2);
        gridTwoButton.setOnClickListener(new GridOnClickListener());
        Button gridThreeButton = fragmentView.findViewById(R.id.grid_3);
        gridThreeButton.setOnClickListener(new GridOnClickListener());
        Button gridFourButton = fragmentView.findViewById(R.id.grid_4);
        gridFourButton.setOnClickListener(new GridOnClickListener());
        Button kataButton =  fragmentView.findViewById(R.id.kata_button);
        kataButton.setOnClickListener(new GoOnClickListener());

        return fragmentView;
    }

    private Grid getGridForButton(View view) {
        switch (view.getId()) {
            case R.id.grid_1:
                return GridInput.SAMPLE_1;
            case R.id.grid_2:
                return GridInput.SAMPLE_2;
            case R.id.grid_3:
                return GridInput.SAMPLE_3;
            case R.id.grid_4:
                return GridInput.SAMPLE_4;
            default:
                return null;
        }
    }

    private void clearResults() {
        ((TextView) getView().findViewById(R.id.output)).setText("");
        ((TextView) getView().findViewById(R.id.TOTAL_COST)).setText(getResources().getText(R.string.no_results));
        ((TextView) getView().findViewById(R.id.PATH_TAKEN)).setText("");
    }

    private String formatPath(CurrentPathStatus path) {
        StringBuilder builder = new StringBuilder();
        List<Integer> rows = path.getRowsTraversed();

        for (int i = 0; i < rows.size(); i++) {
            builder.append(rows.get(i));
            if (i < rows.size() - 1) {
                builder.append("\t");
            }
        }

        return builder.toString();
    }

    class GridOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Grid selectedGrid = getGridForButton(view);
            if (!selectedGrid.equals(mGrid)) {
                clearResults();
            }

            mGrid = selectedGrid;
            ((TextView) getView().findViewById(R.id.grid_contents)).setText(mGrid.asDelimitedString("\t"));
            getView().findViewById(R.id.kata_button).setEnabled(true);
        }
    }

    class GoOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            TraverseGrid visitor = new TraverseGrid(mGrid);
            CurrentPathStatus bestPath = visitor.getBestPathForGrid();

            if (bestPath.isSuccessful()) {
                ((TextView) getView().findViewById(R.id.output)).setText("Yes");
            } else {
                ((TextView) getView().findViewById(R.id.output)).setText("No");
            }
            ((TextView) getView().findViewById(R.id.TOTAL_COST)).setText(Integer.toString(bestPath.getTotalCost()));
            ((TextView) getView().findViewById(R.id.PATH_TAKEN)).setText(formatPath(bestPath));
        }
    }
}
