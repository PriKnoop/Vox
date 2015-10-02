package com.tcc.apptcc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tcc.apptcc.*;
import com.tcc.apptcc.daos.UsuarioDAO;
import com.tcc.apptcc.pojos.Usuario;
import com.vstechlab.easyfonts.EasyFonts;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class LoginActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etSenha;
    private ImageButton btLogin;
    private UsuarioDAO usuarioDAO;
//    private Usuario usuario;
    private String loginDigitado;
    private String senhaDigitada;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializaComponentes();

        if (verificaSeUsuarioJaLogou()) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etLogin.setTypeface(EasyFonts.robotoRegular(this));

        this.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDigitado = etLogin.getText().toString();
                senhaDigitada = etSenha.getText().toString();
                if (TextUtils.isEmpty(loginDigitado) || TextUtils.isEmpty(senhaDigitada)) {
                    if (TextUtils.isEmpty(loginDigitado)) {
                        etLogin.setError("Por favor, insira seu login!");
                    }
                    if (TextUtils.isEmpty(senhaDigitada)) {
                        etSenha.setError("Insira sua senha!");
                    }
                } else {

                    new HttpRequestTask().execute(loginDigitado, senhaDigitada);

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void inicializaComponentes() {

        etLogin = (EditText) findViewById(R.id.et_login_cadastro);
        etSenha = (EditText) findViewById(R.id.et_senha_cadastro);
        btLogin = (ImageButton) findViewById(R.id.ib_login);
        usuarioDAO = new UsuarioDAO();

    }

    private class HttpRequestTask extends AsyncTask<String, String, Usuario> {


        @Override
        protected Usuario doInBackground(String... params) {


            //Usuario usuario = usuarioDAO.chamaMetodoAutenticarLoginSenha(params[0].toString(), params[1].toString());
            Usuario usuario = usuarioDAO.chamaMetodoAutenticarLoginSenha(params);

            return usuario;

        }

        protected void onPostExecute(Usuario usuario) {

        if (usuario != null) {

                SharedPreferences preferences = getApplicationContext().getSharedPreferences(SplashActivity.NOME_PREFERENCIA, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("login", usuario.getLogin());
                editor.commit();

                Toast.makeText(LoginActivity.this, "Usuário autenticado!", Toast.LENGTH_LONG).show();
                Bundle parametros = new Bundle();

                parametros.putSerializable("usuario", usuario);
                Intent itTelaPrincipal = new Intent(LoginActivity.this, HomeActivity.class);
                itTelaPrincipal.putExtras(parametros);
                startActivity(itTelaPrincipal);
            } else {

                Toast.makeText(LoginActivity.this, "Usuário não autenticado!", Toast.LENGTH_LONG).show();
                etSenha.setError("Login e senha incorretos!");

            }
        }
    }
    private boolean verificaSeUsuarioJaLogou() {
        SharedPreferences spPreferencias = getApplicationContext().getSharedPreferences(NOME_PREFERENCIA, MODE_APPEND);
        boolean logou = false;
        String strLogin = spPreferencias.getString("login", null);
        if (strLogin != null) {
            logou = true;
        }
        return logou;
    }
}
