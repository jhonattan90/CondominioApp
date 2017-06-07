package br.com.opetab.condominioapp.DTO;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.opetab.condominioapp.Domain.Comentario;
import br.com.opetab.condominioapp.Domain.Usuario;

import static android.R.attr.id;
import static br.com.opetab.condominioapp.R.string.comentarios;

public class UsuarioDTO {

    public Boolean ok;
    public String mensagem;
    public List<Usuario> usuarios;
    public Usuario usuario;


    public UsuarioDTO(JSONObject object) throws JSONException {
        ok = object.getBoolean("ok");
        mensagem = object.getString("mensagem");

        if(!object.isNull("lista")){
            JSONArray usuariosJSON = object.getJSONArray("lista");
            List<Usuario> us = new ArrayList<>();

            if(usuariosJSON != null) {
                for (int i = 0; i < usuariosJSON.length(); i++) {
                    us.add(new Usuario(usuariosJSON.getJSONObject(i)));
                }
            }

            usuarios = usuarios;
        }

        if(!object.isNull("usuario")) {
            usuario = new Usuario(object.getJSONObject("usuario"));
        }
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "ok=" + ok +
                ", mensagem='" + mensagem + '\'' +
                ", usuarios=" + usuarios +
                ", usuario=" + usuario +
                '}';
    }
}

