package br.com.opetab.condominioapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.opetab.condominioapp.Fragments.ChamadosFragment;
import br.com.opetab.condominioapp.R;


public class HomeTabsAdapter extends FragmentPagerAdapter {

    private Context context;

    public HomeTabsAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if (position == 0) {
            f = new ChamadosFragment();
        }else{
            f = new ChamadosFragment();
        }
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
