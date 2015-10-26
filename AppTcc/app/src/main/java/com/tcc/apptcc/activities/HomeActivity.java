package com.tcc.apptcc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.tcc.apptcc.adapters.SlidingTabLayout;
import com.tcc.apptcc.adapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity implements PessoasAdicionadasFragment.OnHeadlineSelectedListener, InicioFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener, AdicionarPessoasFragment.OnFragmentInteractionListener {

    // Declaring Your View and Variables

    private Drawer navegationDrawerLeft;
    private AccountHeader headerNavegationLeft;
    Toolbar toolbar;
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

        createHeaderToNavegationDrawer();
        createNavegationDrawerLeft(savedInstanceState);
        addItemsToNavegationDrawer();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        /*// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
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
*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
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

    private void createHeaderToNavegationDrawer() {
        // HEADER OF THE NAVEGATION DRAWER

        // Create the AccountHeader
        headerNavegationLeft = new AccountHeaderBuilder()
                .withActivity(this)
                        //diminue o tamanho do header, mostra compactado - > .withCompactStyle(true)
                .withHeaderBackground(R.color.ColorPrimaryDark)
                .addProfiles(
                        new ProfileDrawerItem().withName("Priscila Knoop").withEmail("priscilaknoop@outlook.com").withIcon(R.drawable.profile)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {

                        //AQUI VAI A AÇÃO DE QUANDO ELE CLICA NO PROFILE QUE SERIA A IMAGEM DO LOGO, QUANDO ELE CLICAR LÁ
                        // ADICIONAMOS O EVENTO DO CLIQUE AQUI
                        // IR PARA CONFIGURAÇÕES DA CONTA OU PERFIL
                        // ABRIR ACTIVITY COM O NEGOCIO DE VOLTAR PARA TELA ANTERIOR , MAIS FACIL DE MEXER

                        return false;
                    }
                })
                .build();
    }

    private void addItemsToNavegationDrawer() {
        //ADICIONANDO ITENS A NAVEGATION DRAWER
        // criar icones com cor diferenciada para quando estiver selecionada ex.: account_selected
        navegationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Inicio").withIcon(R.drawable.ic_home_black_24dp).withIdentifier(1));
        navegationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Minha conta").withIcon(R.drawable.ic_account_circle_black_24dp).withIdentifier(2));
        //navegationDrawerLeft.addItem(new DividerDrawerItem());
        navegationDrawerLeft.addItem(new SectionDrawerItem().withName("Ações"));
        navegationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Adicionar pessoas").withIcon(R.drawable.ic_add_circle_black_24dp).withIdentifier(3));
        navegationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Pesquisar").withIcon(R.drawable.ic_search_black_24dp).withIdentifier(4));
       // navegationDrawerLeft.addItem(new DividerDrawerItem());
        navegationDrawerLeft.addItem(new SectionDrawerItem().withName("Informações"));
        navegationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sobre").withIcon(R.drawable.ic_build_black_24dp).withIdentifier(5));
        navegationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Contato").withIcon(R.drawable.ic_build_black_24dp).withIdentifier(6));
        navegationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Configurações").withIcon(R.drawable.ic_build_black_24dp).withIdentifier(7));
        navegationDrawerLeft.addItem(new DividerDrawerItem());
        navegationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Logout").withIcon(R.drawable.ic_arrow_back_black_24dp).withIdentifier(8));



        // Exemplo switchDrawerItem  -> navegationDrawerLeft.addItem(new SwitchDrawerItem().withName("Notificação").withChecked(true).withOnCheckedChangeListener(mOnOnCheckedChangeListener));
        //Exemplo ToggleDrawerItem - > navegationDrawerLeft.addItem(new ToggleDrawerItem().withName("News").withChecked(true).withOnCheckedChangeListener(mOnOnCheckedChangeListener));

    }

    public void onFragmentInteractionListener(){
        //you can leave it empty
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    private void createNavegationDrawerLeft(Bundle savedInstanceState) {
        // NAVEGATION DRAWER LEFT
        navegationDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerNavegationLeft)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = null;
                        if (drawerItem != null) {

                            if (drawerItem.getIdentifier() == 1) { //Inicio
                                //startSupportActionMode(new ActionBarCallBack());
                                //findViewById(R.id.action_mode_bar).setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(HomeActivity.this, R.attr.colorPrimary, R.color.material_drawer_primary));
                            } else if (drawerItem.getIdentifier() == 3) {
                                //intent = new Intent(HomeActivity.this, ActionBarActivity.class);
                                replaceFragment(new PessoasAdicionadasFragment());

                        } else if (drawerItem != null && drawerItem.getIdentifier() == 8) { //Logout
                            //startSupportActionMode(new ActionBarCallBack());
                            //findViewById(R.id.action_mode_bar).setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(HomeActivity.this, R.attr.colorPrimary, R.color.material_drawer_primary));
                            SharedPreferences spPreferencias = getApplicationContext().getSharedPreferences(SplashActivity.NOME_PREFERENCIA, MODE_APPEND);
                            SharedPreferences.Editor editarPreferencias = spPreferencias.edit();
                            editarPreferencias.clear();
                            editarPreferencias.commit();
                            intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else if (drawerItem instanceof Nameable) {
                            toolbar.setTitle(((Nameable) drawerItem).getName().getText(HomeActivity.this));
                        }
                    }

                    return false;
                }
    })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int i, IDrawerItem iDrawerItem) {
                        return false;
                    }
                })
                .build();

    }

    private void replaceFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_drawer_container, frag,"TAG").commit();

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