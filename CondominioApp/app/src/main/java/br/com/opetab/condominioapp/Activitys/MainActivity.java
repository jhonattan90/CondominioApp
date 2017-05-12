package br.com.opetab.condominioapp.Activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.opetab.condominioapp.Model.HomeTabsAdapter;
import br.com.opetab.condominioapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPages();
    }

    private void setupPages(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new HomeTabsAdapter(this, getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.homeTabLayout);
        tabLayout.setupWithViewPager(viewPager);


        int cor = ContextCompat.getColor(this, R.color.white);
//        tabLayout.setTabTextColors(cor, cor);
    }
}
