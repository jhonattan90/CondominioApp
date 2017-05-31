package br.com.opetab.condominioapp.Domain;



import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static br.com.opetab.condominioapp.R.string.chamados;


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

            List<Comentario> comentarios = new ArrayList<>();

            for (int j = 0 ; j < 15; j++){
                Comentario comentario = new Comentario();
                comentario.usuario = u;
                comentario.comentario = "Um comentário de teste";
                comentarios .add(comentario);
            }

            c.comentarios = comentarios;

            chamados.add(c);
        }

        return chamados;
    }

    public static List<Chamado> getMeusChamados() {
        List<Chamado> chamados = new ArrayList<Chamado>();

        for (int i = 0 ; i < 10; i++){
            Chamado c = new Chamado();
            c.id = i;
            c.titulo = "MEU-Chamado" + i;
            c.descricao = "MEU-Descrição" + i;
            c.situacao = "MEU-Ativo";

            Usuario u = new Usuario();
            u.id = i;
            u.nome = "MEU-Usuario" + i;
            u.email = "MEU-usuario"+i+"@email.com";
            u.urlFoto = "http://www.pieglobal.com/wp-content/uploads/2015/10/placeholder-user.png";

            c.usuario = u;

            List<Comentario> comentarios = new ArrayList<>();

            for (int j = 0 ; j < 15; j++){
                Comentario comentario = new Comentario();
                comentario.usuario = u;
                comentario.comentario = "Um comentário de teste";
                comentarios .add(comentario);
            }

            c.comentarios = comentarios;

            chamados.add(c);
        }

        return  chamados;
    }

    public static List<Chamado> getChamadosWS(){
        List<Chamado> chamados = new ArrayList<>();

        try {
            URL apiURL = new URL("http://192.168.25.12:8080/Chamado/WS/chamado/recuperar");

            HttpURLConnection connection;
            InputStream inputStream;

            connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();

            int codigoResposta = connection.getResponseCode();

            if(codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                inputStream= connection.getInputStream();
            }else{
                inputStream = connection.getErrorStream();
            }

            StringBuffer buffer = new StringBuffer();

            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(inputStream));
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }

            br.close();

            String retorno = buffer.toString();
            inputStream.close();
            connection.disconnect();

            JSONArray json = new JSONArray(retorno);

            for (int i = 0; i < json.length(); i++ ){
                Chamado c = new Chamado(json.getJSONObject(i));
                chamados.add(c);
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chamados;
    }

}
