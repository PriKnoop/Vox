package com.tcc.apptcc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tcc.apptcc.*;
import com.tcc.apptcc.adapters.SlidingTabLayout;
import com.tcc.apptcc.adapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity implements InsertFragment.OnFragmentInteractionListener, MainFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener {

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Adicionar","Principal", "Pesquisar"};
    int Numboftabs =3;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);// Creating The Toolbar and setting it as the Toolbar for the activity

        if (!verificaSeUsuarioJaLogou()) {
            startActivity(new Intent(this, LoginActivity.class));
        }

            toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
        } else {
            if (id == R.id.action_logout) {
                SharedPreferences spPreferencias = getApplicationContext().getSharedPreferences(SplashActivity.NOME_PREFERENCIA, MODE_APPEND);
                SharedPreferences.Editor editarPreferencias = spPreferencias.edit();
                editarPreferencias.clear();
                editarPreferencias.commit();
                Intent itPrimeiraTela = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(itPrimeiraTela);
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private boolean verificaSeUsuarioJaLogou() {
        SharedPreferences spPreferencias = getApplicationContext().getSharedPreferences(NOME_PREFERENCIA, MODE_APPEND);
        boolean logou = false;
        Long preferencesId = spPreferencias.getLong("id", 0);
        if (preferencesId != 0) {
            logou = true;
        }
        return logou;
    }
}