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

import br.com.opetab.condominioapp.Domain.Comentario;
import br.com.opetab.condominioapp.R;

public class ComentarioAdapter extends RecyclerView.Adapter {

    public Context context;
    public List<Comentario> comentarios;

    public ComentarioAdapter(Context context, List<Comentario> comentarios) {
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_comentario, parent, false);
        ComentarioViewHolder holder = new ComentarioViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Comentario c = comentarios.get(position);
        ComentarioViewHolder view = (ComentarioViewHolder) holder;
        view.nome.setText(c.usuario.nome);
        view.comentario.setText(c.comentario);

        Picasso.with(context).load(c.usuario.urlFoto).fit().into(view.imgUsuario);
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class ComentarioViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgUsuario;
        public TextView nome;
        public TextView comentario;

        public ComentarioViewHolder(View itemView) {
            super(itemView);

            imgUsuario = (ImageView) itemView.findViewById(R.id.img_comentario);
            nome = (TextView) itemView.findViewById(R.id.nome_usuario);
            comentario = (TextView) itemView.findViewById(R.id.comentario);
        }
    }
}
