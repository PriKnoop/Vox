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

public class CadastroActivity extends AppCompatActivity {

    EditText etLoginCadastro;
    EditText etSenhaCadastro;
    EditText etSenhaRepetida;
    ImageButton btCadastrar;
    UsuarioDAO usuarioDAO;

    String loginDigitado;
    String senhaDigitada;
    String senhaRepetidaDigitada;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();

        if (verificaSeUsuarioJaLogou()) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        this.btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDigitado = etLoginCadastro.getText().toString();
                senhaDigitada = etSenhaCadastro.getText().toString();
                senhaRepetidaDigitada = etSenhaRepetida.getText().toString();
                if (TextUtils.isEmpty(loginDigitado) || TextUtils.isEmpty(senhaDigitada) || TextUtils.isEmpty(senhaRepetidaDigitada)) {
                    if (TextUtils.isEmpty(loginDigitado)) {
                        etLoginCadastro.setError("Por favor, insira seu login!");
                    }
                    if (TextUtils.isEmpty(senhaDigitada)) {
                        etSenhaCadastro.setError("Insira sua senha!");
                    }
                    if (TextUtils.isEmpty(senhaRepetidaDigitada)) {
                        etSenhaRepetida.setError("Insira sua senha novamente!");
                    }
                } else {
                    System.out.println("Senhas: >>" +senhaRepetidaDigitada + senhaDigitada);
                    if (!senhaRepetidaDigitada.equals(senhaDigitada)) {
                        etSenhaRepetida.setError("As senhas não coincidem!");
                    } else {

                        new HttpRequestTask().execute(loginDigitado, senhaDigitada, senhaRepetidaDigitada);


                    }
                }

        }});
    }


    private void inicializaComponentes() {
        etLoginCadastro = (EditText) findViewById(R.id.et_login_cadastro);
        etSenhaCadastro = (EditText) findViewById(R.id.et_senha_cadastro);
        etSenhaRepetida = (EditText) findViewById(R.id.et_senha_repetida);
        btCadastrar = (ImageButton) findViewById(R.id.ib_cadastrar);
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
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
private class HttpRequestTask extends AsyncTask<String, String, Usuario> {


    @Override
    protected Usuario doInBackground(String... params) {
        Usuario usuarioRetornado = usuarioDAO.chamaMetodoAdicionarUsuario(params[0].toString(), params[1].toString() );
        return usuarioRetornado;

    }

    protected void onPostExecute(Usuario usuario) {
//            Log.i("DEBUG", usuario.getLogin());
//            Log.i("DEBUG", usuario.getSenha());
        if (usuario != null) {

            SharedPreferences preferences = getApplicationContext().getSharedPreferences(SplashActivity.NOME_PREFERENCIA, 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("login", usuario.getLogin());
            editor.commit();

            Toast.makeText(CadastroActivity.this, "Usuário cadastrado!", Toast.LENGTH_LONG).show();
            Bundle parametros = new Bundle();

            parametros.putSerializable("usuario", usuario);
            Intent itTelaPrincipal = new Intent(CadastroActivity.this, HomeActivity.class);
            itTelaPrincipal.putExtras(parametros);
            startActivity(itTelaPrincipal);
        } else {

            Toast.makeText(CadastroActivity.this, "Usuário não cadastrado!", Toast.LENGTH_LONG).show();
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

