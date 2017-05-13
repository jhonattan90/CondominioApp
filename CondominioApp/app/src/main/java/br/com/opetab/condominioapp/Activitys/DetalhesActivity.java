package br.com.opetab.condominioapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.opetab.condominioapp.Domain.Chamado;
import br.com.opetab.condominioapp.R;

public class DetalhesActivity extends AppCompatActivity {

    public TextView nome;
    public TextView situacao;
    public TextView descricao;
    public TextView usuario;
    public ImageView image;

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

        Chamado c = (Chamado) getIntent().getSerializableExtra(Chamado.KEY);
        setupView(c);
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
    }
}
