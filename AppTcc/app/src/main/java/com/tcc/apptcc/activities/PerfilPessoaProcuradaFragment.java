package com.tcc.apptcc.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.FloatingActionButton;
import com.tcc.apptcc.activities.R;
import com.tcc.apptcc.adapters.Mascara;
import com.tcc.apptcc.daos.AvistamentoDAO;
import com.tcc.apptcc.daos.CircunstanciaDAO;
import com.tcc.apptcc.daos.LocalizacaoDAO;
import com.tcc.apptcc.daos.PessoaProcuradaDAO;
import com.tcc.apptcc.pojos.PessoaProcurada;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilPessoaProcuradaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilPessoaProcuradaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilPessoaProcuradaFragment extends android.support.v4.app.Fragment {

    View viewCustom;
    private static PessoaProcurada pessoaSelecionada;
    private OnFragmentInteractionListener mListener;

    PessoaProcuradaDAO pessoaProcuradaDAO;
    AvistamentoDAO avistamentoDAO;
    CircunstanciaDAO circunstanciaDAO;
    LocalizacaoDAO localizacaoDAO;

    private MaterialEditText etPessoaNome, etPessoaTipo, etPessoaGenero, etDataNascimento, etEtnia, etOlhos, etFisico, etCabeloCor, etCabeloTipo, etAltura;
    private FloatingActionButton btnAdicionarAvistamento;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PerfilPessoaProcuradaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilPessoaProcuradaFragment newInstance(PessoaProcurada pessoa) {
        PerfilPessoaProcuradaFragment fragment = new PerfilPessoaProcuradaFragment();
        pessoaSelecionada = pessoa;
        return fragment;
    }

    public PerfilPessoaProcuradaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil_pessoa_procurada, container, false);
        viewCustom = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_avistamento, container, false);

        inicializaComponentes(view);
        etAltura.setText(pessoaSelecionada.getAltura().toString());
        etCabeloCor.setText(pessoaSelecionada.getCabeloCor().toString());
        etCabeloTipo.setText(pessoaSelecionada.getCabeloTipo().toString());
        etDataNascimento.setText(pessoaSelecionada.getDataNascimento().toString());
        etEtnia.setText(pessoaSelecionada.getEtnia().toString());
        etFisico.setText(pessoaSelecionada.getTipoFisico().toString());
        etOlhos.setText(pessoaSelecionada.getOlhos().toString());
        etPessoaGenero.setText(pessoaSelecionada.getGenero().toString());
        etPessoaNome.setText(pessoaSelecionada.getNome().toString());
        etPessoaTipo.setText(pessoaSelecionada.getTipoPessoaProcurada().toString());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void inicializaComponentes(View view) {
        etPessoaNome = (MaterialEditText) view.findViewById(R.id.et_perfil_nome);
        etPessoaTipo = (MaterialEditText) view.findViewById(R.id.et_perfil_tipo);
        etPessoaGenero = (MaterialEditText) view.findViewById(R.id.et_perfil_genero);
        etDataNascimento = (MaterialEditText) view.findViewById(R.id.et_perfil_datanascimento);
        etEtnia = (MaterialEditText) view.findViewById(R.id.et_perfil_etnia);
        etOlhos = (MaterialEditText) view.findViewById(R.id.et_perfil_olhos);
        etFisico = (MaterialEditText) view.findViewById(R.id.et_perfil_tipofisico);
        etCabeloCor = (MaterialEditText) view.findViewById(R.id.et_perfil_cabelocor);
        etCabeloTipo = (MaterialEditText) view.findViewById(R.id.et_perfil_cabelotipo);
        etAltura = (MaterialEditText) view.findViewById(R.id.et_perfil_altura);
        btnAdicionarAvistamento = (FloatingActionButton) view.findViewById(R.id.btn_adicionar_avistamento);

        pessoaProcuradaDAO = new PessoaProcuradaDAO();
        avistamentoDAO = new AvistamentoDAO();
        circunstanciaDAO = new CircunstanciaDAO();
        localizacaoDAO = new LocalizacaoDAO();
    }

}
