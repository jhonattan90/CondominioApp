package br.com.opetab.condominioapp.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.opetab.condominioapp.Adapter.ChamadoAdapter;
import br.com.opetab.condominioapp.Adapter.ComentarioAdapter;
import br.com.opetab.condominioapp.Domain.Chamado;
import br.com.opetab.condominioapp.Domain.ChamadoService;
import br.com.opetab.condominioapp.Domain.Comentario;
import br.com.opetab.condominioapp.Domain.Constant;
import br.com.opetab.condominioapp.R;

import static android.R.id.input;
import static br.com.opetab.condominioapp.R.id.recyclerView;

public class DetalhesActivity extends AppCompatActivity implements ComentarioAdapter.RemoverOnClickListener {

    public TextView nome;
    public TextView situacao;
    public TextView descricao;
    public TextView usuario;
    public ImageView image;

    public ProgressDialog progressDialog;
    public ComentarTask comentarTask;
    public DeletarTask deletarTask;
    public Chamado chamado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(R.string.detalhes);

        nome = (TextView) findViewById(R.id.nome_chamado);
        situacao = (TextView) findViewById(R.id.situacao_chamado);
        usuario = (TextView) findViewById(R.id.solicitante_chamado);
        descricao = (TextView) findViewById(R.id.descricao_chamado);
        image = (ImageView) findViewById(R.id.img_chamado);

        chamado = (Chamado) getIntent().getSerializableExtra(Chamado.KEY);
        setupView(chamado);

        Button btnEmail = (Button) findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","wag9797@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Chamado");
                startActivity(Intent.createChooser(emailIntent, "Enviando email..."));
            }
        });

        Button btnComentar = (Button) findViewById(R.id.btnComentar);
        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesActivity.this);
                builder.setTitle("Comentário");


                final EditText input = new EditText(DetalhesActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comentarioText = input.getText().toString();
                        Comentario comentario = new Comentario();
                        comentario.comentario = comentarioText;
                        comentario.usuario = Constant.usuarioLogado;

                        comentarTask = new ComentarTask(comentario, chamado, DetalhesActivity.this);
                        comentarTask.execute();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public void onClickRemover(final Comentario comentario) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesActivity.this);
        builder.setTitle("Remover");
        builder.setMessage("Deseja mesmo remover este comentário?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deletarTask = new DeletarTask(comentario, chamado, DetalhesActivity.this);
                deletarTask .execute();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    public void setupView(Chamado c){
        nome.setText(c.titulo);
        String situacaoString = "Situação: " + c.situacao;
        situacao.setText(situacaoString);
        descricao.setText(c.descricao);
        usuario.setText(c.usuario.nome);
        Picasso.with(this).load(c.usuario.urlFoto).fit().into(image);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ComentarioAdapter(this, c.comentarios, this));

    }

    private class ComentarTask extends AsyncTask<Void, Void, Chamado> {


        private Chamado chamado;
        private Comentario comentario;
        private Activity activity;

        public  ComentarTask(Comentario comentario, Chamado chamado, Activity activity){
            this.comentario = comentario;
            this.chamado = chamado;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(activity,"Por favor aguarde...","Criando comentário...");
        }

        @Override
        protected Chamado doInBackground(Void... voids) {
            return ChamadoService.comentar(comentario,chamado.id);
        }

        @Override
        protected void onPostExecute(Chamado c) {
            super.onPostExecute(c);

            if(c != null){
                Toast.makeText(activity, "Comentário criado com Sucesso", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                setupView(c);
            }

        }
    }

    private class DeletarTask extends AsyncTask<Void, Void, Chamado> {

        private Chamado chamado;
        private Comentario comentario;
        private Activity activity;

        public  DeletarTask(Comentario comentario, Chamado chamado, Activity activity){
            this.comentario = comentario;
            this.chamado = chamado;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(activity,"Por favor aguarde...","Removendo comentário...");
        }

        @Override
        protected Chamado doInBackground(Void... voids) {
            return ChamadoService.remover(comentario,chamado.id);
        }

        @Override
        protected void onPostExecute(Chamado c) {
            super.onPostExecute(c);

            if(c != null){
                Toast.makeText(activity, "Comentário removido com Sucesso", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                setupView(c);
            }

        }

    }
}
