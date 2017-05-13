package br.com.opetab.condominioapp.Domain;


import java.util.ArrayList;
import java.util.List;

import static android.R.string.no;

public class ChamadoService {

    public static List<Chamado> getChamados(){
        List<Chamado> chamados = new ArrayList<Chamado>();

        for (int i = 0 ; i < 10; i++){
            Chamado c = new Chamado();
            c.id = i;
            c.titulo = "Chamado" + i;
            c.descricao = "Descrição" + i;
            c.situacao = "Ativo";

            Usuario u = new Usuario();
            u.id = i;
            u.nome = "Usuario" + i;
            u.email = "usuario"+i+"@email.com";
            u.urlFoto = "http://www.pieglobal.com/wp-content/uploads/2015/10/placeholder-user.png";

            c.usuario = u;
            chamados.add(c);
        }
        return chamados;
    }
}
