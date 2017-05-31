package br.com.opetab.condominioapp.Domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static android.R.attr.id;

public class Usuario implements Serializable {

    public  long id;
    public String nome;
    public  String email;
    public String urlFoto;

    public Usuario() {

    }

    public Usuario(JSONObject object) throws JSONException {
        id = object.getLong("id");
        nome = object.getString("nome");
        email = object.getString("email");
        urlFoto  = object.getString("urlFoto");
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", urlFoto='" + urlFoto + '\'' +
                '}';
    }
}
