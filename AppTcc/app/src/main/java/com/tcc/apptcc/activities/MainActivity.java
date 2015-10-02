package com.tcc.apptcc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tcc.apptcc.*;
import com.tcc.apptcc.daos.UsuarioDAO;
import com.tcc.apptcc.pojos.Usuario;
import com.vstechlab.easyfonts.EasyFonts;

public class MainActivity extends AppCompatActivity {

    private Button btLogin;
    private Button btCadastro;
    private TextView tvWelcome;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializaComponentes();

        if (verificaSeUsuarioJaLogou()) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btLogin.setTypeface(EasyFonts.robotoRegular(this));
        btCadastro.setTypeface(EasyFonts.robotoRegular(this));
        tvWelcome.setTypeface(EasyFonts.robotoRegular(this));

        this.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itTelaLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(itTelaLogin);
            }
        });
        this.btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itTelaCadastro = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(itTelaCadastro);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        btLogin=(Button)findViewById(R.id.btLogin);
        btCadastro=(Button)findViewById(R.id.btCadastro);
        tvWelcome=(TextView)findViewById(R.id.tv_welcome);


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
