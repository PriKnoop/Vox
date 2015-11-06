package com.tcc.apptcc.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
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
import com.rey.material.widget.FloatingActionButton;
import com.tcc.apptcc.adapters.ListViewDemoAdapter;
import com.tcc.apptcc.adapters.ListViewItem;
import com.tcc.apptcc.adapters.PlaceAutocompleteAdapter;
import com.tcc.apptcc.adapters.PlaceJSONParser;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.places.Places.*;

public class PessoasAdicionadasFragment extends android.support.v4.app.Fragment {
    private List<ListViewItem> mItems;        // ListView items list
    private ListView lista;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Activity activity;
    OnHeadlineSelectedListener mCallback;

    private String mParam1;
    private String mParam2;
    private FloatingActionButton btnIrParaAdicionarPessoas;

    private OnFragmentInteractionListener mListener;

    public static PessoasAdicionadasFragment newInstance(String param1, String param2) {
        PessoasAdicionadasFragment fragment = new PessoasAdicionadasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PessoasAdicionadasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // initialize the items list
        //mItems = new ArrayList<ListViewItem>();
        //Resources resources = getResources();

       // mItems.add(new ListViewItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_account_circle_black_24dp), "João Silva da Costa", "Morador de rua"));
        //mItems.add(new ListViewItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_account_circle_black_24dp), "Maria de Jesus", "Abrigada"));
        //mItems.add(new ListViewItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_account_circle_black_24dp), "Pedro dos Santos", "Desaparecido"));

        //ListViewDemoAdapter mAdapter = new ListViewDemoAdapter(getActivity(), mItems);
       // mItems.setAdapter

        // initialize and set the list adapter
        //setListAdapter(new ListViewDemoAdapter(getContext(), mItems));

        //RecyclerView.Adapter m_adapter = new MobileNETDistinctChatInfoAdapter(getActivity(), R.layout.chatlist_list_item, m_parts);

        //list.setListAdapter(m_adapter); # HERE

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_pessoas_adicionadas,container,false);
        inicializaComponentes(view);
       // getListView().setDivider(null);
        this.btnIrParaAdicionarPessoas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AdicionarPessoasFragment());
            }
        });

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
        ListViewItem item = mItems.get(position);

        // do something
        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
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
        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        //lista = (ListView) view.findViewById(R.id.list);

    }}



