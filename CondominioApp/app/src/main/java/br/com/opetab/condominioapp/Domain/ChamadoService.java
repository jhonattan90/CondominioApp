package br.com.opetab.condominioapp.Domain;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.opetab.condominioapp.DTO.ChamadoDTO;
import br.com.opetab.condominioapp.DTO.UsuarioDTO;
import br.com.opetab.condominioapp.R;

import static br.com.opetab.condominioapp.R.string.chamados;


public class ChamadoService {

    public static List<Chamado> getChamadosFAKE(){
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

    public static List<Chamado> getChamados(){
        List<Chamado> chamadosList = new ArrayList<>();

        try {
            URL apiURL = new URL(Constant.BASE_URL + "/Chamado/WS/chamado/recuperar");

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

            JSONObject json = new JSONObject(retorno);

            ChamadoDTO c = new ChamadoDTO(json);

            if(c.chamados != null){
                chamadosList = c.chamados;
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chamadosList;
    }

    public static Chamado cadastrar(Chamado chamado){

        try {
            URL apiURL = new URL(Constant.BASE_URL + "/Chamado/WS/chamado/Cadastrar");

            JSONObject params = new JSONObject();

            params.put("titulo", chamado.titulo);
            params.put("descricao", chamado.descricao);
            params.put("situacao", chamado.situacao);

            JSONObject usuario = new JSONObject();
            usuario.put("id", chamado.usuario.id);
            usuario.put("nome", chamado.usuario.nome);
            usuario.put("email", chamado.usuario.email);
            usuario.put("urlFoto", chamado.usuario.urlFoto);

            params.put("usuario", usuario);


            HttpURLConnection connection;
            InputStream inputStream;

            connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            OutputStream os = connection.getOutputStream();
            os.write(params.toString().getBytes("UTF-8"));
            os.close();

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

            JSONObject json = new JSONObject(retorno);

            ChamadoDTO c = new ChamadoDTO(json);

            if(c.chamado != null){
                return c.chamado;
            }


        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Chamado comentar(Comentario comentario, Long chamadoId){

        try {

            URL apiURL = new URL(Constant.BASE_URL + "/Chamado/WS/comentario/" + chamadoId.toString() + "/Cadastrar");

            JSONObject params = new JSONObject();

            params.put("comentario", comentario.comentario);

            JSONObject usuario = new JSONObject();
            usuario.put("id", comentario.usuario.id);
            usuario.put("nome", comentario.usuario.nome);
            usuario.put("email", comentario.usuario.email);
            usuario.put("urlFoto", comentario.usuario.urlFoto);

            params.put("usuario", usuario);


            HttpURLConnection connection;
            InputStream inputStream;

            connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            OutputStream os = connection.getOutputStream();
            os.write(params.toString().getBytes("UTF-8"));
            os.close();

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

            JSONObject json = new JSONObject(retorno);

            ChamadoDTO c = new ChamadoDTO(json);

            if(c.chamado != null){
                return c.chamado;
            }


        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Chamado remover(Comentario comentario, Long chamadoId) {

        try {

            URL apiURL = new URL(Constant.BASE_URL + "/Chamado/WS/comentario/" + chamadoId.toString() + "/remover/" + comentario.id.toString());

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

            JSONObject json = new JSONObject(retorno);

            ChamadoDTO c = new ChamadoDTO(json);

            if(c.chamado != null){
                return c.chamado;
            }


        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
