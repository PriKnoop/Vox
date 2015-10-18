package com.tcc.apptcc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.tcc.apptcc.*;

public class SplashActivity extends AppCompatActivity implements Runnable {

    ImageView imageView1;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";
    private SharedPreferences spPreferencias;
    private SharedPreferences.Editor editarPreferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.inicializaComponentes();

        spPreferencias = getApplicationContext().getSharedPreferences(NOME_PREFERENCIA, MODE_APPEND);
        editarPreferencias = spPreferencias.edit();

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);
    }

    private boolean verificaSeUsuarioJaLogou() {
        boolean logou = false;
        Long preferencesId = spPreferencias.getLong("id", 0);
        if (preferencesId != null) {
            logou = true;
        }
        return logou;
    }




    public void run(){
        if (verificaSeUsuarioJaLogou()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();

        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void inicializaComponentes() {

        imageView1=(ImageView)findViewById(R.id.iv_logo);
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
}
