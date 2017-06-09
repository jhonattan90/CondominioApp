package br.com.opetab.condominioapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.opetab.condominioapp.Domain.Comentario;
import br.com.opetab.condominioapp.Domain.Constant;
import br.com.opetab.condominioapp.R;

public class ComentarioAdapter extends RecyclerView.Adapter {

    public Context context;
    public List<Comentario> comentarios;
    RemoverOnClickListener removerOnClickListener;

    public ComentarioAdapter(Context context, List<Comentario> comentarios, RemoverOnClickListener removerOnClickListener) {
        this.context = context;
        this.comentarios = comentarios;
        this.removerOnClickListener = removerOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_comentario, parent, false);
        ComentarioViewHolder holder = new ComentarioViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Comentario c = comentarios.get(position);
        ComentarioViewHolder view = (ComentarioViewHolder) holder;
        view.nome.setText(c.usuario.nome);
        view.comentario.setText(c.comentario);

        if(c.usuario.id == Constant.usuarioLogado.id){
            view.btnRemover.setVisibility(View.VISIBLE);
        }else{
            view.btnRemover.setVisibility(View.INVISIBLE);
        }

        view.btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerOnClickListener.onClickRemover(c);
            }
        });

        Picasso.with(context).load(c.usuario.urlFoto).fit().into(view.imgUsuario);
    }

    public interface RemoverOnClickListener {
        public void onClickRemover(Comentario comentario);
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class ComentarioViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgUsuario;
        public TextView nome;
        public TextView comentario;
        public Button btnRemover;

        public ComentarioViewHolder(View itemView) {
            super(itemView);
            btnRemover = (Button) itemView.findViewById(R.id.btnRemover);
            imgUsuario = (ImageView) itemView.findViewById(R.id.img_comentario);
            nome = (TextView) itemView.findViewById(R.id.nome_usuario);
            comentario = (TextView) itemView.findViewById(R.id.comentario);
        }
    }
}
