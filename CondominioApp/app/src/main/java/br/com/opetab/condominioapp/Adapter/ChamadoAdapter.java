package br.com.opetab.condominioapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.opetab.condominioapp.Domain.Chamado;
import br.com.opetab.condominioapp.R;


public class ChamadoAdapter extends RecyclerView.Adapter {

    private List<Chamado> chamados;
    private Context context;

    public ChamadoAdapter(Context context, List<Chamado> chamados) {
        this.chamados = chamados;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return this.chamados.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_chamado, parent, false);
        ChamadosViewHolder holder = new ChamadosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChamadosViewHolder vHolder = (ChamadosViewHolder) holder;
        Chamado c = chamados.get(position);
        vHolder.nome.setText(c.titulo);
        String situacao = "Situação: " + c.situacao;
        vHolder.situacao.setText(situacao);
        vHolder.descricao.setText(c.descricao);
        vHolder.usuario.setText(c.usuario.nome);
        Picasso.with(this.context).load(c.usuario.urlFoto).fit().into(vHolder.image);
    }

//    @Override
//    public void onBindViewHolder(ChamadosViewHolder holder, int position) {
//
//    }

    public static class ChamadosViewHolder extends RecyclerView.ViewHolder {

        public TextView nome;
        public TextView situacao;
        public TextView descricao;
        public TextView usuario;
        public ImageView image;

        public ChamadosViewHolder(View view){
            super(view);

            nome = (TextView) view.findViewById(R.id.nome_chamado);
            situacao = (TextView) view.findViewById(R.id.situacao_chamado);
            usuario = (TextView) view.findViewById(R.id.solicitante_chamado);
            descricao = (TextView) view.findViewById(R.id.descricao_chamado);
            image = (ImageView) view.findViewById(R.id.img_chamado);

        }
    }


}




