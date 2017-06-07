package br.com.opetab.condominioapp.Domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.opetab.condominioapp.DTO.UsuarioDTO;
import br.com.opetab.condominioapp.R;

import static br.com.opetab.condominioapp.R.string.chamados;

public class UsuarioService {

    public static Usuario autenticar(Usuario usuario){

        try {
            URL apiURL = new URL("http://192.168.25.56:8080/Chamado/WS/usuario/Autenticar");

            JSONObject params = new JSONObject();

            params.put("nome", usuario.nome);
            params.put("email", usuario.email);
            params.put("urlFoto", usuario.urlFoto);

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

            UsuarioDTO c = new UsuarioDTO(json);

            if(c.usuario != null){
                return c.usuario;
            }


        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
