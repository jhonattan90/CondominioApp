package br.com.opetab.condominioapp.DTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.opetab.condominioapp.Domain.Chamado;
import br.com.opetab.condominioapp.Domain.Usuario;

public class ChamadoDTO {
    public Boolean ok;
    public String mensagem;
    public List<Chamado> chamados;
    public Chamado chamado;


    public ChamadoDTO(JSONObject object) throws JSONException {
        ok = object.getBoolean("ok");
        mensagem = object.getString("mensagem");

        if(!object.isNull("lista")){
            JSONArray chamadosJSON = object.getJSONArray("lista");
            List<Chamado> ch = new ArrayList<>();

            if(chamadosJSON != null) {
                for (int i = 0; i < chamadosJSON.length(); i++) {
                    ch.add(new Chamado(chamadosJSON.getJSONObject(i)));
                }
            }

            chamados = ch;
        }

        if(!object.isNull("chamado")) {
            chamado= new Chamado(object.getJSONObject("chamado"));
        }
    }

    @Override
    public String toString() {
        return "ChamadoDTO{" +
                "ok=" + ok +
                ", mensagem='" + mensagem + '\'' +
                ", chamados=" + chamados +
                ", chamado=" + chamado +
                '}';
    }
}
