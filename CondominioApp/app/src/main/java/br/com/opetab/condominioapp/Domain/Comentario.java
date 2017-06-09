package br.com.opetab.condominioapp.Domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Comentario implements Serializable {

    public Long id;
    public Usuario usuario;
    public String comentario;

    public Comentario() {

    }

    public Comentario(JSONObject object) throws JSONException {
        id = object.getLong("id");
        usuario = new Usuario(object.getJSONObject("usuario"));
        comentario = object.getString("comentario");
    }


    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                "usuario=" + usuario +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
