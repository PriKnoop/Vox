package com.tcc.apptcc.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.listeners.OnDismissCallback;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.squareup.picasso.Picasso;
import com.tcc.apptcc.daos.PessoaProcuradaDAO;
import com.tcc.apptcc.pojos.PessoaProcurada;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends android.support.v4.app.Fragment {
    private MaterialListView mListView;
    private PessoaProcuradaDAO pessoaDAO;
    int contadorDeCards;

    private OnFragmentInteractionListener mListener;

    public static InicioFragment newInstance() {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        inicializaComponentes(view);
        mListView.setItemAnimator(new SlideInLeftAnimator());
        mListView.getItemAnimator().setAddDuration(300);
        mListView.getItemAnimator().setRemoveDuration(300);

        final ImageView emptyView = new ImageView(getContext());
        emptyView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mListView.setEmptyView(emptyView);
        Picasso.with(getContext())
                .load(R.drawable.profile)
                .resize(100, 100)
                .centerInside()
                .into(emptyView);

        // Fill the array withProvider mock content
       // fillArray();
       new HttpRequestTaskMostrarPessoas().execute("Oiiiii");

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
            public void onItemClick(@NonNull Card card, int position) {
                Log.d("CARD_TYPE", "" + card.getTag());
            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {
                Log.d("LONG_CLICK", "" + card.getTag());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public void inicializaComponentes(View view) {
        mListView = (MaterialListView) view.findViewById(R.id.material_listview);
        pessoaDAO = new PessoaProcuradaDAO();
        contadorDeCards = 0;
    }
  /*  public void adicionarCardPessoa(){
        Card card = new Card.Builder(getContext())
                .setTag("BASIC_IMAGE_BUTTONS_CARD")
                .withProvider(BasicImageButtonsCardProvider.class)
                .setTitle("I'm new")
                .setDescription("I've been generated on runtime!")
                .setDrawable(R.drawable.profile)
                .endConfig()
                .build()

        mListView.add(card);
    }*/

  /*  private void fillArray() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            cards.add(adicionarCardPessoa(i));
        }
        mListView.getAdapter().addAll(cards);
    }*/
    private void adicionarCards(List<PessoaProcurada> listaPessoas) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < listaPessoas.size() - 1; i++) {
            cards.add(adicionarCardPessoa(listaPessoas.get(i)));
        }
        mListView.getAdapter().addAll(cards);
    }

   // private Card adicionarCardPessoa(final int position) {
    private Card adicionarCardPessoa(final PessoaProcurada pessoaProcurada) {
       // String title = "Pessoa " + (position + 1);
       // String description = "Desaparecido";
        String title = pessoaProcurada.getNome();
        String description = pessoaProcurada.getTipoPessoaProcurada().toString();
        contadorDeCards = contadorDeCards + 1;
        final CardProvider provider = new Card.Builder(getContext())
                .setTag("BIG_IMAGE_BUTTONS_CARD")
                .setDismissible()
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_card_pessoa)
                .setTitle(title)
                .setDescription(description)
                .setDrawable(R.drawable.profile)
                .addAction(R.id.left_text_button, new TextViewAction(getContext())
                        .setText("Dispensar")
                        .setTextResourceColor(R.color.black_button)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Log.d("Dispensando", "Cartão");

                                //mListView.getAdapter().add(generateNewCard());
                                Toast.makeText(getContext(), "Dispensar", Toast.LENGTH_SHORT).show();
                                card.dismiss();
                            }
                        }))
                .addAction(R.id.right_text_button, new TextViewAction(getContext())
                        .setText("VER PERFIL")
                        .setTextResourceColor(R.color.accent_material_dark)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                replaceFragment(new PerfilPessoaProcuradaFragment().newInstance(pessoaProcurada));
                            }
                        }));

        if (contadorDeCards % 2 == 0) {
            provider.setDividerVisible(true);
        }

        return provider.endConfig().build();
    }

    private Card generateNewCard() {
        return new Card.Builder(getContext())
                .setTag("BASIC_IMAGE_BUTTONS_CARD")
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle("I'm new")
                .setDescription("I've been generated on runtime!")
                .setDrawable(R.drawable.profile)
                .endConfig()
                .build();
    }

    private void addMockCardAtStart() {
        mListView.getAdapter().addAtStart(new Card.Builder(getContext())
                .setTag("BASIC_IMAGE_BUTTONS_CARD")
                .setDismissible()
                .withProvider(new CardProvider())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle("Hi there")
                .setDescription("I've been added on top!")
                .addAction(R.id.left_text_button, new TextViewAction(getContext())
                        .setText("left")
                        .setTextResourceColor(R.color.black_button))
                .addAction(R.id.right_text_button, new TextViewAction(getContext())
                        .setText("right")
                        .setTextResourceColor(R.color.orange_button))
                .setDrawable(R.drawable.profile)
                .endConfig()
                .build());
    }

    private void replaceFragment(Fragment frag) {
        getFragmentManager().beginTransaction().replace(R.id.nav_drawer_container, frag, "TAG").commit();
    }

    private class HttpRequestTaskMostrarPessoas extends AsyncTask<String, List<PessoaProcurada>, List<PessoaProcurada>> {
        @Override
        protected List<PessoaProcurada> doInBackground(String... params) {
            Log.i("DEBUG", params[0]);
            PessoaProcurada[] listaPessoasRetornadas;
            List<PessoaProcurada> lista = pessoaDAO.chamaMetodoPesquisarTodasPessoasCadastradas();
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
            if (listaPessoas.size() != 0) {
            Toast.makeText(getContext(), "Pessoas encontradas!!", Toast.LENGTH_LONG).show();
            adicionarCards(listaPessoas);
        } else {
            Toast.makeText(getContext(), "Pessoas não encontradas!", Toast.LENGTH_LONG).show();
        }
    }
    }
}