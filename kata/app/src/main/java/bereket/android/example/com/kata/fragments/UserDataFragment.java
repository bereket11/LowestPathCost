package bereket.android.example.com.kata.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class UserDataFragment extends Fragment {
    public UserDataFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.user_data_fragment, container, false);

        Button goButton =  fragmentView.findViewById(R.id.kata_button);
        goButton.setOnClickListener(new GoOnClickListener());

        EditText customGridContents =  fragmentView.findViewById(R.id.user_input_grid);
        customGridContents.addTextChangedListener(new GridContentsWatcher());

        return fragmentView;
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

    private boolean gridContentsAreValid(int[][] contents) {
        if (contents.length < 1 || contents.length > 10 || contents[0].length < 5 || contents[0].length > 100) {
            return false;
        } else {
            return true;
        }
    }

    private void loadGrid(int[][] contents) {
        Grid validGrid = new Grid(contents);
        TraverseGrid visitor = new TraverseGrid(validGrid);
        CurrentPathStatus bestPath = visitor.getBestPathForGrid();

        if (bestPath.isSuccessful()) {
            ((TextView) getView().findViewById(R.id.output)).setText("Yes");
        } else {
            ((TextView) getView().findViewById(R.id.output)).setText("No");
        }
        ((TextView) getView().findViewById(R.id.TOTAL_COST)).setText(Integer.toString(bestPath.getTotalCost()));
        ((TextView) getView().findViewById(R.id.PATH_TAKEN)).setText(formatPath(bestPath));
        ((TextView) getView().findViewById(R.id.grid_contents)).setText(validGrid.asDelimitedString("\t"));
    }

    class GridContentsWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Button goButton = getView().findViewById(R.id.kata_button);
            goButton.setEnabled(!s.toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) { }
    }

    class GoOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String gridString = ((EditText) getView().findViewById(R.id.user_input_grid)).getText().toString();
            int[][] potentialGridContents = GridInput.gridArrayFromString(gridString);
            if (gridContentsAreValid(potentialGridContents)) {
                loadGrid(potentialGridContents);
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Invalid Grid")
                        .setMessage(R.string.invalid_grid_message)
                        .setPositiveButton("OK", null)
                        .show();
                clearResults();
            }
        }
    }

    private void clearResults() {
        ((EditText) getView().findViewById(R.id.user_input_grid)).setText("");

    }
}
