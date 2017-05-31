package br.com.opetab.condominioapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.opetab.condominioapp.Activitys.DetalhesActivity;
import br.com.opetab.condominioapp.Adapter.ChamadoAdapter;
import br.com.opetab.condominioapp.Domain.Chamado;
import br.com.opetab.condominioapp.Domain.ChamadoService;
import br.com.opetab.condominioapp.R;

import static br.com.opetab.condominioapp.Domain.ChamadoService.getChamadosWS;


public class ChamadosFragment extends Fragment implements ChamadoAdapter.ChamadoOnClickListener {

    public RecyclerView recyclerView;
    public List<Chamado> chamados;
    public ProgressDialog progressDialog;
    public ChamadosTask chamadosTask;

    public ChamadosFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chamados, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chamadosTask = new ChamadosTask(getContext(), this);
        startChamadosTask();
    }

    public void startChamadosTask(){
        if (chamadosTask != null){
            chamadosTask.execute();
        }
    }

//    public void load(){
//        Bundle args = getArguments();
//
//        if (args.getInt("tipo") == 0) {
//            getChamados();
//        }else{
//            getMeusChamados();
//        }
//    }

//    public void getChamados(){
//        this.chamados = ChamadoService.getChamados();
//        recyclerView.setAdapter(new ChamadoAdapter(getContext(),this.chamados, this));
//    }
//
//    public void getMeusChamados(){
//        this.chamados = ChamadoService.getMeusChamados();
//        recyclerView.setAdapter(new ChamadoAdapter(getContext(),this.chamados, this));
//    }


    @Override
    public void onClickChamado(Chamado c) {
        Intent intent = new Intent(getContext(), DetalhesActivity.class);
        intent.putExtra(Chamado.KEY, c);

        startActivity(intent);
    }

    private class ChamadosTask extends AsyncTask<Void, Void, List<Chamado>> {

        private Context context;
        private ChamadoAdapter.ChamadoOnClickListener chamadoOnClickListener;

        public  ChamadosTask(Context context, ChamadoAdapter.ChamadoOnClickListener chamadoOnClickListener){
            this.context = context;
            this.chamadoOnClickListener = chamadoOnClickListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(),"Por favor aguarde,,,","Recuperando chamados...");
        }

        @Override
        protected List<Chamado> doInBackground(Void... voids) {
            Bundle args = getArguments();

            if (args.getInt("tipo") == 0) {
                return ChamadoService.getChamadosWS();
            }else{
                return ChamadoService.getChamadosWS();
            }

        }

        @Override
        protected void onPostExecute(List<Chamado> c) {
            super.onPostExecute(c);
            chamados = c;
            recyclerView.setAdapter(new ChamadoAdapter(context, chamados, chamadoOnClickListener));
            progressDialog.dismiss();
        }
    }
}
