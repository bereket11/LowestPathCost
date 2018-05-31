package bereket.android.example.com.kata;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;

import bereket.android.example.com.kata.adapter.LowestPathAdapter;

public class MainActivity extends AppCompatActivity {

    private LowestPathAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter = new LowestPathAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabs =  findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}
