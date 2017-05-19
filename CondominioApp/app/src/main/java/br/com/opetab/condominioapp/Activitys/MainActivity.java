package br.com.opetab.condominioapp.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import br.com.opetab.condominioapp.Adapter.HomeTabsAdapter;
import br.com.opetab.condominioapp.Fragments.ChamadosFragment;
import br.com.opetab.condominioapp.R;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public  List<ChamadosFragment> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupPages();
        setupNavDrawer();

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

    public void setupNavDrawer(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24px);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if(navigationView != null && drawerLayout != null) {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setChecked(true);
                    drawerLayout.closeDrawers();
                    onNavDrawerItemSelected(item);
                    return false;
                }
            });
        }
    }

    private void onNavDrawerItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_home:
                Toast.makeText(this,"home",Toast.LENGTH_SHORT);
                break;
            case R.id.item_sobre:

                Intent intent = new Intent(this, SobreActivity.class);
                startActivity(intent);

                break;
            case R.id.item_logout:
                LoginManager.getInstance().logOut();
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerLayout != null){
            drawerLayout.openDrawer(GravityCompat.START);
            return  true;
        }
        return super.onOptionsItemSelected(item);
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

//        int cor = ContextCompat.getColor(this, R.color.white);
//        tabLayout.setTabTextColors(cor, cor);
    }
}
