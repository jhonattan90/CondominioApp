package br.com.opetab.condominioapp.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import br.com.opetab.condominioapp.Adapter.ChamadoAdapter;
import br.com.opetab.condominioapp.Domain.Chamado;
import br.com.opetab.condominioapp.Domain.ChamadoService;
import br.com.opetab.condominioapp.Domain.Usuario;
import br.com.opetab.condominioapp.Domain.UsuarioService;
import br.com.opetab.condominioapp.R;

import static br.com.opetab.condominioapp.R.id.recyclerView;
import static br.com.opetab.condominioapp.R.string.chamados;


public class LoginActivity extends AppCompatActivity {

    CallbackManager callBackManager;
    public ProgressDialog progressDialog;
    public AutenticarTask autenticarTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(AccessToken.getCurrentAccessToken() != null){
            go();
        }else {
            callBackManager = CallbackManager.Factory.create();
            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

            loginButton.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity",response.toString());

                            try {
                                Usuario usuario = new Usuario();
                                usuario.nome = object.getString("name");
                                usuario.email = object.getString("email");
                                usuario.urlFoto = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                autenticarTask = new AutenticarTask(LoginActivity.this, usuario);
                                autenticarTask.execute();

                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,picture.type(large)");
                    request.setParameters(parameters);
                    request.executeAsync();

                    Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_SHORT).show();
                    Log.i("FB","Sucesso");

//                    go();

                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    Log.w("FB","Cancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    Log.e("FB","Error");
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void go(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    private class AutenticarTask extends AsyncTask<Void, Void, Usuario> {

        private Context context;
        private Usuario usuario;

        public  AutenticarTask(Context context, Usuario usuario){
            this.context = context;
            this.usuario = usuario;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context,"Por favor aguarde...","Autenticando...");
        }

        @Override
        protected Usuario doInBackground(Void... voids) {
            return UsuarioService.autenticar(usuario);
        }

        @Override
        protected void onPostExecute(Usuario u) {
            super.onPostExecute(u);

            if(u != null){
                Toast.makeText(context, "Usuario Autenticado com Sucesso", Toast.LENGTH_SHORT).show();
                go();
            }

            progressDialog.dismiss();
        }
    }
}
