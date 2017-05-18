package br.com.opetab.condominioapp.Activitys;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.opetab.condominioapp.Adapter.HomeTabsAdapter;
import br.com.opetab.condominioapp.Fragments.ChamadosFragment;
import br.com.opetab.condominioapp.R;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    public  List<ChamadosFragment> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupPages();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NovoChamadoActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (ChamadosFragment view: views) {
            view.load();
        }
    }

    public void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    private void setupPages(){
        ChamadosFragment chamados = new ChamadosFragment();
        ChamadosFragment meusChamados = new ChamadosFragment();
        views = new ArrayList<>();
        views.add(chamados);
        views.add(meusChamados);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new HomeTabsAdapter(this, getSupportFragmentManager(), views));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.homeTabLayout);
        tabLayout.setupWithViewPager(viewPager);


        int cor = ContextCompat.getColor(this, R.color.white);
//        tabLayout.setTabTextColors(cor, cor);
    }
}
