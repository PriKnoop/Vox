package com.tcc.apptcc.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.listeners.OnDismissCallback;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.tcc.apptcc.adapters.ListViewDemoAdapter;
import com.tcc.apptcc.adapters.ListViewItem;
import com.tcc.apptcc.adapters.PlaceAutocompleteAdapter;
import com.tcc.apptcc.adapters.PlaceJSONParser;
import com.tcc.apptcc.daos.AvistamentoDAO;
import com.tcc.apptcc.daos.PessoaProcuradaDAO;
import com.tcc.apptcc.pojos.Avistamento;
import com.tcc.apptcc.pojos.PessoaProcurada;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

import static com.google.android.gms.location.places.Places.*;

public class PessoasAdicionadasFragment extends android.support.v4.app.Fragment {
    private MaterialListView mListView;
    private TextView tvPessoasAdicionas;
    private PessoaProcuradaDAO pessoaDAO;
    private AvistamentoDAO avistamentoDAO;
    int contadorDeCards;
    private Long idUsuario;
    private List<Avistamento> listaAvistamentosPessoasExibidas;
    private List<PessoaProcurada> listaPessoasCadastradasPorUsuario;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";


    Activity activity;
    OnHeadlineSelectedListener mCallback;

    private FloatingActionButton btnIrParaAdicionarPessoas;

    private OnFragmentInteractionListener mListener;

    public static PessoasAdicionadasFragment newInstance(String param1, String param2) {
        PessoasAdicionadasFragment fragment = new PessoasAdicionadasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PessoasAdicionadasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_pessoas_adicionadas,container,false);
        inicializaComponentes(view);
        verificaSeUsuarioEstaLogado();
       // getListView().setDivider(null);
        this.btnIrParaAdicionarPessoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AdicionarPessoasFragment());
            }
        });

        mListView.setItemAnimator(new SlideInLeftAnimator());
        mListView.getItemAnimator().setAddDuration(300);
        mListView.getItemAnimator().setRemoveDuration(300);

        /*final ImageView emptyView = new ImageView(getContext());
        emptyView.setScaleType(ImageView.ScaleType.FIT_START);
        mListView.setEmptyView(emptyView);
        Picasso.with(getContext())
                .load(R.drawable.profile)
                .resize(50, 50)
                .centerInside()
                .into(emptyView);*/

        // new HttpRequestTaskExibirPessoasQueUsuarioCadastrou().execute(idUsuario);

        mListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  replaceFragment(new PerfilPessoaProcuradaFragment());
            }
        });


        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(@NonNull Card card, int position) {
                // Show a toast
                Toast.makeText(getContext(), "Você dispensou o cartão " + card.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card cardItem, int position) {
                PessoaProcurada data = (PessoaProcurada) cardItem.getTag();
                String name = data.getNome();
                Toast.makeText(getContext(), "Clique normal " + name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(@NonNull Card cardItem, int position) {
                PessoaProcurada data = (PessoaProcurada) cardItem.getTag();
                String name = data.getNome();
                Toast.makeText(getContext(), "Clique longo " + name, Toast.LENGTH_SHORT).show();
            }
        });

        new HttpRequestTaskMostrarPessoasCadastradasPorUsuario().execute(idUsuario);


        return view;
    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activity = getActivity();
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new ClassCastException(activity.toString()

                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
       // ListViewItem item = mItems.get(position);

        // do something
       // Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private void replaceFragment(Fragment frag){
        getFragmentManager().beginTransaction().replace(R.id.nav_drawer_container, frag,"TAG").commit();
    }

    private void inicializaComponentes(View view) {
        btnIrParaAdicionarPessoas = (FloatingActionButton) view.findViewById(R.id.btn_levar_para_adicionar_pessoas);
        mListView = (MaterialListView) view.findViewById(R.id.listView_avistamentos_recentes);
        tvPessoasAdicionas = (TextView) view.findViewById(R.id.tv_adicionadas);
        listaAvistamentosPessoasExibidas = new ArrayList<Avistamento>();
        pessoaDAO = new PessoaProcuradaDAO();
        avistamentoDAO = new AvistamentoDAO();

    }
    private void verificaSeUsuarioEstaLogado() {
        SharedPreferences spPreferencias = getContext().getApplicationContext().getSharedPreferences(NOME_PREFERENCIA, Context.MODE_APPEND);
        Long preferencesId = spPreferencias.getLong("id", 0);
        if (preferencesId != 0) {
            idUsuario = preferencesId;
        } else {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    private void adicionarCards() {
        List<Card> cards = new ArrayList<>();
        mListView.getAdapter().clear();
        contadorDeCards = 0;
        if(listaPessoasCadastradasPorUsuario.isEmpty() || listaPessoasCadastradasPorUsuario == null || listaAvistamentosPessoasExibidas.isEmpty() || listaAvistamentosPessoasExibidas == null){
            tvPessoasAdicionas.setText("Você ainda não cadastrou ninguém! Insira novas pessoas apertando o botão +");
        } else{
            for (int i = 0; i < listaPessoasCadastradasPorUsuario.size()-1; i++) {
                if(listaPessoasCadastradasPorUsuario.get(i).getIdPessoaProcurada() == listaAvistamentosPessoasExibidas.get(i).getPessoaProcurada().getIdPessoaProcurada()){
                    cards.add(adicionarCardPessoa(listaPessoasCadastradasPorUsuario.get(i), listaAvistamentosPessoasExibidas.get(i)));
                } else {
                    tvPessoasAdicionas.setText("Ocorreu um erro! Tente novamente!");
                }
            }
            mListView.getAdapter().addAll(cards);
        }
    }

    // private Card adicionarCardPessoa(final int position) {
    private Card adicionarCardPessoa(final PessoaProcurada pessoaProcurada, final Avistamento avistamento) {
        // String title = "Pessoa " + (position + 1);
        // String description = "Desaparecido";
        String title = pessoaProcurada.getNome();
        String description = "Visto por último pelo usuário João da Silva Oliveira";
        contadorDeCards = contadorDeCards + 1;
        final CardProvider provider = new Card.Builder(getContext())
                .setTag(pessoaProcurada)
                .setDismissible()
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_card_avistamentos_recentes)
                .setTitle(title)
                .setDescription(description)
                .setDrawable(R.drawable.profile)
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull final RequestCreator requestCreator) {
                        requestCreator.fit();
                    }
                });

        if (contadorDeCards % 2 == 0) {
            provider.setDividerVisible(true);
        }

        return provider.endConfig().build();
    }

    private class HttpRequestTaskMostrarPessoasCadastradasPorUsuario extends AsyncTask<Long, List<PessoaProcurada>, List<PessoaProcurada>> {
        @Override
        protected List<PessoaProcurada> doInBackground(Long... params) {
            Log.i("DEBUG", params[0].toString());
            List<PessoaProcurada> lista = pessoaDAO.chamaMetodoPesquisarPessoasPorUsuario(params[0]);
            return lista;

        }

        protected void onProgressUpdate() {
            com.rey.material.app.Dialog.Builder builder = null;
            builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
            };

            ((SimpleDialog.Builder) builder).message("Aguarde...");
            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getFragmentManager(), null);
        }

        protected void onPostExecute(List<PessoaProcurada> listaPessoas) {
            if (listaPessoas != null || !listaPessoas.isEmpty()) {
                listaPessoasCadastradasPorUsuario = listaPessoas;
                Toast.makeText(getContext(), "Pessoas encontradas!!", Toast.LENGTH_LONG).show();
                for (int i=0; i<listaPessoas.size(); i++){
                    new HttpRequestTaskEncontrarUltimoAvistamento().execute(listaPessoas.get(i).getIdPessoaProcurada());
                }
            } else {
                Toast.makeText(getContext(), "Pessoas não encontradas!", Toast.LENGTH_LONG).show();
                tvPessoasAdicionas.setText("Você ainda não cadastrou ninguém! Insira novas pessoas apertando o botão +");

            }
        }
    }

    private class HttpRequestTaskEncontrarUltimoAvistamento extends AsyncTask<Long, Avistamento, Avistamento> {
        @Override
        protected Avistamento doInBackground(Long... params) {
            Log.i("DEBUG", params[0].toString());
            Avistamento avistamentoRetornado = avistamentoDAO.chamaMetodoPesquisarUltimoAvistamentoPorPessoa(params[0]);
            return avistamentoRetornado;

        }

        protected void onProgressUpdate(Avistamento... avistamento) {
            /*com.rey.material.app.Dialog.Builder builder = null;
            builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
            };

            ((SimpleDialog.Builder) builder).message("Aguarde...");
            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getFragmentManager(), null);*/
            super.onProgressUpdate();
        }

        protected void onPostExecute(Avistamento avistamento) {
            if (avistamento != null) {
                Toast.makeText(getContext(), "Avistamento encontrado!!", Toast.LENGTH_LONG).show();
                listaAvistamentosPessoasExibidas.add(avistamento);
                adicionarCards();
            } else {
                Toast.makeText(getContext(), "Avistamento encontrado!", Toast.LENGTH_LONG).show();
            }
        }
    }

}



