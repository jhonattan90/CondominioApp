package br.com.opetab.condominioapp.Domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Chamado implements Serializable {

    public static final String KEY = "chamado";
    public long id;
    public String titulo;
    public String descricao;
    public String situacao;
    public Usuario usuario;
    public List<Comentario> comentarios;

    public Chamado() {

    }

    public Chamado(JSONObject object) throws JSONException {
        id = object.getLong("id");
        titulo = object.getString("titulo");
        descricao = object.getString("descricao");
        situacao = object.getString("situacao");
        usuario = new Usuario(object.getJSONObject("usuario"));

        JSONArray comentariosJSON = object.getJSONArray("comentarios");
        List<Comentario> cs = new ArrayList<>();

        for (int i = 0; i < comentariosJSON.length() ;i++){
            cs.add(new Comentario(comentariosJSON.getJSONObject(i)));
        }

        comentarios = cs;
    }

    @Override
    public String toString() {
        return "Chamado{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", situacao='" + situacao + '\'' +
                ", usuario=" + usuario +
                ", comentarios=" + comentarios +
                '}';
    }
}
