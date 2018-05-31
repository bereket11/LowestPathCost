package bereket.android.example.com.kata.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bereket.android.example.com.kata.fragments.HardCodedDataFragment;
import bereket.android.example.com.kata.fragments.UserDataFragment;

/**
 * Created by berekethaile on 5/31/18.
 */

public class LowestPathAdapter extends FragmentPagerAdapter {
    public LowestPathAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sample Grid";
            case 1:
                return "User Grid";
            default:
                return null;
        }
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HardCodedDataFragment();
            case 1:
                return new UserDataFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
