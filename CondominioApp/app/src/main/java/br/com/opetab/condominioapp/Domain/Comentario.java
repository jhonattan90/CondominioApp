package br.com.opetab.condominioapp.Domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Comentario implements Serializable {

    public Usuario usuario;
    public String comentario;

    public Comentario() {

    }

    public Comentario(JSONObject object) throws JSONException {
        usuario = new Usuario(object.getJSONObject("usuario"));
        comentario = object.getString("comentario");
    }


    @Override
    public String toString() {
        return "Comentario{" +
                "usuario=" + usuario +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
