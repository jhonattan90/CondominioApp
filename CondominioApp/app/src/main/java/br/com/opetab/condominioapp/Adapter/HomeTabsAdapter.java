package br.com.opetab.condominioapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import br.com.opetab.condominioapp.Fragments.ChamadosFragment;
import br.com.opetab.condominioapp.R;


public class HomeTabsAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<ChamadosFragment> views;

    public HomeTabsAdapter(Context context,FragmentManager fm, List<ChamadosFragment> views) {
        super(fm);
        this.context = context;
        this.views = views;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        Bundle args = new Bundle();

        if (position == 0) {
            args.putInt("tipo", 0);
        }else{
            args.putInt("tipo", 1);
        }

        f = views.get(position);
        f.setArguments(args);
        return f;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return context.getString(R.string.chamados);
        }else{
            return context.getString(R.string.meus_chamados);
        }
    }
}
