package br.com.opetab.condominioapp.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.opetab.condominioapp.Domain.Chamado;
import br.com.opetab.condominioapp.Domain.ChamadoService;
import br.com.opetab.condominioapp.Domain.Comentario;
import br.com.opetab.condominioapp.Domain.Constant;
import br.com.opetab.condominioapp.Domain.Usuario;
import br.com.opetab.condominioapp.Domain.UsuarioService;
import br.com.opetab.condominioapp.R;


public class NovoChamadoActivity extends AppCompatActivity {
    EditText tTitulo;
    EditText tDescricao;
    Button btnSalvar;

    public ProgressDialog progressDialog;
    public CadastrarTask cadastrarTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_chamado);
        setupToolbar();

        tTitulo = (EditText) findViewById(R.id.tTitulo);
        tDescricao = (EditText) findViewById(R.id.tDescricao);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chamado chamado = new Chamado();
                chamado.titulo = tTitulo.getText().toString();
                chamado.descricao = tDescricao.getText().toString();
                chamado.situacao = "Aberto";
                chamado.usuario = Constant.usuarioLogado;
                chamado.comentarios = new ArrayList<Comentario>();

                Activity mActivity = NovoChamadoActivity.this;
                cadastrarTask = new CadastrarTask(chamado, mActivity);
                cadastrarTask.execute();


            }
        });

    }


    public void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Novo");
        }
    }

    private class CadastrarTask extends AsyncTask<Void, Void, Chamado> {


        private Chamado chamado;
        private Activity activity;

        public  CadastrarTask(Chamado chamado, Activity activity){
            this.chamado = chamado;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(activity,"Por favor aguarde...","Criando chamado...");
        }

        @Override
        protected Chamado doInBackground(Void... voids) {
            return ChamadoService.cadastrar(chamado);
        }

        @Override
        protected void onPostExecute(Chamado c) {
            super.onPostExecute(c);

            if(c != null){
                Toast.makeText(activity, "Chamado criado com Sucesso", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                activity.finish();
                Log.d("NovoChamado", "CadastrarTask.onPostExecute");
            }

        }
    }

}

