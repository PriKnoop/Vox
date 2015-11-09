package com.tcc.apptcc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.FloatingActionButton;
import com.tcc.apptcc.adapters.Mascara;
import com.tcc.apptcc.adapters.PlaceAutocompleteAdapter;
import com.tcc.apptcc.daos.AvistamentoDAO;
import com.tcc.apptcc.daos.CircunstanciaDAO;
import com.tcc.apptcc.daos.LocalizacaoDAO;
import com.tcc.apptcc.daos.PessoaProcuradaDAO;
import com.tcc.apptcc.enuns.CabeloCor;
import com.tcc.apptcc.enuns.CabeloTipo;
import com.tcc.apptcc.enuns.Etnia;
import com.tcc.apptcc.enuns.Genero;
import com.tcc.apptcc.enuns.Olhos;
import com.tcc.apptcc.enuns.TipoFisico;
import com.tcc.apptcc.enuns.TipoPessoaProcurada;
import com.tcc.apptcc.pojos.Avistamento;
import com.tcc.apptcc.pojos.Circunstancia;
import com.tcc.apptcc.pojos.Localizacao;
import com.tcc.apptcc.pojos.PessoaProcurada;
import com.tcc.apptcc.pojos.Usuario;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.google.android.gms.location.places.Places.GEO_DATA_API;
import static com.google.android.gms.location.places.Places.PLACE_DETECTION_API;

public class PesquisaFragment extends android.support.v4.app.Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private OnFragmentInteractionListener mListener;
    private MaterialEditText validationEt;
    private TextWatcher mascaraAltura;

    PessoaProcuradaDAO pessoaProcuradaDAO;
    AvistamentoDAO avistamentoDAO;
    CircunstanciaDAO circunstanciaDAO;
    LocalizacaoDAO localizacaoDAO;

    SharedPreferences preferences;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";

    private MaterialEditText etPessoaNome, etPessoaTipo, etPessoaGenero, etDataNascimento, etEtnia, etOlhos, etFisico, etCabeloCor, etCabeloTipo, etAltura, etVistoUltimo, etDetalhesAvistamento;
    private String pessoaNome, detalhesAvistamento, localizacaoAvistamentoDetalhes, localizacaoAvistamentoCidade, localizacaoAvistamentoEstado;
    private AutoCompleteTextView acLocalizacaoAvistamento;
    private TipoPessoaProcurada tipoPessoaProcurada;
    private Genero pessoaGenero;
    private Etnia pessoaEtnia;
    private Olhos pessoaOlhos;
    private TipoFisico pessoaFisico;
    private CabeloCor pessoaCabeloCor;
    private CabeloTipo pessoaCabeloTipo;
    private double pessoaAltura;
    private float localizacaoAvistamentoLatitude, localizacaoAvistamentoLongitude;
    private String pessoaDataNascimento, pessoaDataAvistamento;
    private Long idUsuario;
    View viewCustom;
    // private Button btEscolherDataAvistamento;
    private FloatingActionButton btEnviarPessoa;

    private GoogleApiClient googleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds LIMITES_BRASIL = new LatLngBounds(
            new LatLng(-33.750833, -73.992222), new LatLng(5.272222, 34.791667));

    public static AdicionarPessoasFragment newInstance(String param1, String param2) {
        AdicionarPessoasFragment fragment = new AdicionarPessoasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PesquisaFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient
                .Builder(getContext())
                .enableAutoManage(getActivity(), 0 /* clientId */, this)
                .addApi(GEO_DATA_API)
                .addApi(PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adicionar_pessoas, container, false);
        viewCustom = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_avistamento, container, false);

        inicializaComponentes(view);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mAdapter = new PlaceAutocompleteAdapter(getContext(), googleApiClient, LIMITES_BRASIL,
                null);

        // View viewCustom = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_avistamento, null);
        //btEscolherDataAvistamento = (Button) viewCustom.findViewById(R.id.btn_escolher_data_avistamento);

        //acLocalizacaoAvistamento = (AutoCompleteTextView) builder.getDialog().findViewById(R.id.autocomplete_places);
        acLocalizacaoAvistamento.setOnItemClickListener(mAutocompleteClickListener);
        acLocalizacaoAvistamento.setAdapter(mAdapter);

        etPessoaTipo.setOnClickListener(this);
        etPessoaGenero.setOnClickListener(this);
        etDataNascimento.setOnClickListener(this);
        etFisico.setOnClickListener(this);
        etEtnia.setOnClickListener(this);
        etOlhos.setOnClickListener(this);
        etCabeloCor.setOnClickListener(this);
        etCabeloTipo.setOnClickListener(this);
        etVistoUltimo.setOnClickListener(this);
        btEnviarPessoa.setOnClickListener(enviarPessoaProcurada);


        //btEscolherDataAvistamento.setOnClickListener(this);


        etAltura.addValidator(new RegexpValidator("Digite apenas números!", "\\d+"));

        return view;
    }


    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        OnHeadlineSelectedListener mCallback;
        activity = getActivity();
        try {
            activity = getActivity();
            // mCallback = (OnHeadlineSelectedListener) activity;
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Log.i("DEBUG", "O botão funciona!");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void replaceFragment(Fragment frag) {
        getFragmentManager().beginTransaction().replace(R.id.nav_drawer_container, frag, "TAG").commit();
    }

    private void inicializaComponentes(View view) {
        etPessoaNome = (MaterialEditText) view.findViewById(R.id.et_pessoa_nome);
        etPessoaTipo = (MaterialEditText) view.findViewById(R.id.et_pessoa_tipo);
        etPessoaGenero = (MaterialEditText) view.findViewById(R.id.et_pessoa_genero);
        etDataNascimento = (MaterialEditText) view.findViewById(R.id.et_pessoa_datanascimento);
        etEtnia = (MaterialEditText) view.findViewById(R.id.et_pessoa_etnia);
        etOlhos = (MaterialEditText) view.findViewById(R.id.et_pessoa_olhos);
        etFisico = (MaterialEditText) view.findViewById(R.id.et_pessoa_tipofisico);
        etCabeloCor = (MaterialEditText) view.findViewById(R.id.et_pessoa_cabelocor);
        etCabeloTipo = (MaterialEditText) view.findViewById(R.id.et_pessoa_cabelotipo);
        etAltura = (MaterialEditText) view.findViewById(R.id.et_pessoa_altura);
        etVistoUltimo = (MaterialEditText) view.findViewById(R.id.et_visto_ultimo);
        btEnviarPessoa = (FloatingActionButton) view.findViewById(R.id.button_bt_float_color);

        acLocalizacaoAvistamento = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_places);


        mascaraAltura = Mascara.insert("#,##", etAltura);

        pessoaProcuradaDAO = new PessoaProcuradaDAO();
        avistamentoDAO = new AvistamentoDAO();
        circunstanciaDAO = new CircunstanciaDAO();
        localizacaoDAO = new LocalizacaoDAO();

    }

    public void onClick(View v) {
        com.rey.material.app.Dialog.Builder builder = null;

        switch (v.getId()) {
            case R.id.et_pessoa_tipo:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        int index = getSelectedIndex();
                        System.out.println(TipoPessoaProcurada.values()[index]);
                        tipoPessoaProcurada = TipoPessoaProcurada.values()[index];
                        etPessoaTipo.setText(tipoPessoaProcurada.toString());
                        fragment.dismiss();
                    }
                 /*       for (TipoPessoaProcurada tipo : TipoPessoaProcurada.values()){
                            if(getSelectedValue().toString().equals(tipo.descricao)){
                                tipoPessoaProcurada = tipo;
                            }

                        }*/

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Desaparecido", "Morador de rua", "Abrigado", "Outro"}, 0)
                        .title("Situação")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
            case R.id.et_pessoa_genero:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        int index = getSelectedIndex();
                        System.out.println(Genero.values()[index]);
                        pessoaGenero = Genero.values()[index];
                        etPessoaGenero.setText(pessoaGenero.toString());
                        fragment.dismiss();

                       /* pessoaGenero = getSelectedValue().toString();
                        etPessoaGenero.setText(pessoaGenero);
                        fragment.dismiss();*/
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Masculino", "Feminino", "Outro"}, 0)
                        .title("Gênero")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
            case R.id.et_pessoa_etnia:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        int index = getSelectedIndex();
                        System.out.println(Etnia.values()[index]);
                        pessoaEtnia = Etnia.values()[index];
                        etEtnia.setText(pessoaEtnia.toString());
                        fragment.dismiss();
                        /*pessoaEtnia = getSelectedValue().toString();
                        etEtnia.setText(pessoaEtnia);
                        fragment.dismiss();*/
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Caucasiana", "Negra", "Indigena", "Parda", "Outra"}, 0)
                        .title("Etnia")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
            case R.id.et_pessoa_olhos:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        int index = getSelectedIndex();
                        System.out.println(Olhos.values()[index]);
                        pessoaOlhos = Olhos.values()[index];
                        etOlhos.setText(pessoaOlhos.toString());
                        fragment.dismiss();
                       /* pessoaOlhos = getSelectedValue().toString();
                        etOlhos.setText(pessoaOlhos);
                        fragment.dismiss();*/
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Castanhos", "Azuis", "Verdes", "Pretos", "Outra"}, 0)
                        .title("Cor dos olhos")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
            case R.id.et_pessoa_cabelocor:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        int index = getSelectedIndex();
                        System.out.println(CabeloCor.values()[index]);
                        pessoaCabeloCor = CabeloCor.values()[index];
                        etCabeloCor.setText(pessoaCabeloCor.toString());
                        fragment.dismiss();
                       /* pessoaCabeloCor = getSelectedValue().toString();
                        etCabeloCor.setText(pessoaCabeloCor);
                        fragment.dismiss();*/
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Loiro", "Preto", "Castanho", "Ruivo", "Grisalho", "Branco", "Outro"}, 0)
                        .title("Cor dos cabelos")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
            case R.id.et_pessoa_cabelotipo:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        int index = getSelectedIndex();
                        System.out.println(CabeloTipo.values()[index]);
                        pessoaCabeloTipo = CabeloTipo.values()[index];
                        etCabeloTipo.setText(pessoaCabeloTipo.toString());
                        fragment.dismiss();
                       /* pessoaCabeloTipo = getSelectedValue().toString();
                        etCabeloTipo.setText(pessoaCabeloTipo);
                        fragment.dismiss();*/
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Lisos", "Ondulados", "Cacheados", "Crespos", "Outro"}, 0)
                        .title("Tipo de cabelo")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
            case R.id.et_pessoa_tipofisico:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        int index = getSelectedIndex();
                        System.out.println(TipoFisico.values()[index]);
                        pessoaFisico = TipoFisico.values()[index];
                        etFisico.setText(pessoaFisico.toString());
                        fragment.dismiss();
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Magro", "Intermediário", "Sobrepeso", "Obeso", "Outro"}, 0)
                        .title("Tipo físico")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;

            case R.id.et_visto_ultimo:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    @Override
                    protected void onBuildDone(com.rey.material.app.Dialog dialog) {
                        dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Calendar cal = Calendar.getInstance();
                        int mDay = cal.get(Calendar.DAY_OF_MONTH);
                        int mMonth = cal.get(Calendar.MONTH);
                        int mYear = cal.get(Calendar.YEAR);
                        int mMinDay = mDay;
                        int mMinMonth = mMonth;
                        int mMinYear = mYear - 20;
                        int mMaxDay = mDay;
                        int mMaxMonth = mMonth;
                        int mMaxYear = mYear;
                        com.rey.material.app.Dialog.Builder builderData = null;

                        builderData = new DatePickerDialog.Builder(mMinDay, mMinMonth, mMinYear, mMaxDay, mMaxMonth, mMaxYear, mDay, mMonth, mYear) {

                            @Override
                            public void onPositiveActionClicked(DialogFragment fragment) {
                                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                                String dataString = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                                //DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                                //java.util.Date utilDate = formatador.parse(dataString);
                                //java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                                etVistoUltimo.setText(dataString);
                                Toast.makeText(getActivity(), "Data selecionada foi " + dataString, Toast.LENGTH_SHORT).show();
                                try {
                                    //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                    //java.util.Date utilDate = format.parse(dataString);
                                    //java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                                    pessoaDataAvistamento = dataString;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                fragment.dismiss();
                            }

                            @Override
                            public void onNegativeActionClicked(DialogFragment fragment) {
                                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                                super.onNegativeActionClicked(fragment);
                            }
                        };
                        builderData
                                .positiveAction("OK")
                                .negativeAction("CANCELAR");

                        DialogFragment fragment = DialogFragment.newInstance(builderData);
                        fragment.show(getFragmentManager(), null);
                    }

                    public void onPositiveActionClicked(DialogFragment fragment) {
                        etDetalhesAvistamento = (MaterialEditText) fragment.getDialog().findViewById(R.id.et_detalhes_avistamento);
                        Toast.makeText(getActivity(), "Detalhes" + etDetalhesAvistamento.getText().toString(), Toast.LENGTH_SHORT).show();
                        //MaterialEditText et_localizacao_avistamento = (MaterialEditText)fragment.getDialog().findViewById(R.id.et_localizacao_avistamento);
                        detalhesAvistamento = etDetalhesAvistamento.getText().toString();
                        fragment.dismiss();
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                        // super.onNegativeActionClicked(fragment);
                    }
                };

                builder.title("Detalhes de avistamento")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR")
                        .contentView(R.layout.dialog_avistamento);

                break;

            case R.id.et_pessoa_datanascimento:
                // builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light) {
                Calendar cal = Calendar.getInstance();
                int mDay = cal.get(Calendar.DAY_OF_MONTH);
                int mMonth = cal.get(Calendar.MONTH);
                int mYear = cal.get(Calendar.YEAR);
                int mMinDay = mDay;
                int mMinMonth = mMonth;
                int mMinYear = mYear - 100;
                int mMaxDay = mDay;
                int mMaxMonth = mMonth;
                int mMaxYear = mYear;
                builder = new DatePickerDialog.Builder(mMinDay, mMinMonth, mMinYear, mMaxDay, mMaxMonth, mMaxYear, mDay, mMonth, mYear) {

                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        // android.app.DatePickerDialog dialog =  new android.app.DatePickerDialog();

                        // dialog.getDatePicker();

                        // dialog.dateRange(1, 1, 1900, Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR);
                        String dataString = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        Toast.makeText(getActivity(), "Data selecionada foi " + dataString, Toast.LENGTH_SHORT).show();
                        //DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                        etDataNascimento.setText(dataString);
                        try {
                            //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            //java.util.Date utiDate = format.parse(dataString);
                            //java.sql.Date sqlDate = new java.sql.Date(utiDate.getTime());
                            pessoaDataNascimento = dataString;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        fragment.dismiss();
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
        }
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }


    View.OnClickListener enviarPessoaProcurada = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v instanceof FloatingActionButton) {
                FloatingActionButton bt = (FloatingActionButton) v;
                bt.setLineMorphingState((bt.getLineMorphingState() + 1) % 2, true);
                Toast.makeText(getActivity(), "Cliquei em enviar", Toast.LENGTH_SHORT).show();
                verificaSeUsuarioEstaLogado();
                validarCampos();

                if (validarCampos()) {
                    pessoaNome = etPessoaNome.getText().toString();
                    pessoaAltura = Double.parseDouble(etAltura.getText().toString());

                    //Definindo usuário que cadastrou pessoa
                    Usuario usuarioCadastrou = new Usuario();
                    usuarioCadastrou.setIdUsuario(idUsuario);

                    //Definindo pessoa e atributos
                    PessoaProcurada pessoaEnviada = new PessoaProcurada();
                    pessoaEnviada.setUsuario(usuarioCadastrou);
                    pessoaEnviada.setNome(pessoaNome);
                    pessoaEnviada.setTipoPessoaProcurada(tipoPessoaProcurada);
                    pessoaEnviada.setGenero(pessoaGenero);
                    pessoaEnviada.setEtnia(pessoaEtnia);
                    pessoaEnviada.setOlhos(pessoaOlhos);
                    pessoaEnviada.setTipoFisico(pessoaFisico);
                    pessoaEnviada.setCabeloCor(pessoaCabeloCor);
                    pessoaEnviada.setCabeloTipo(pessoaCabeloTipo);
                    pessoaEnviada.setAltura(pessoaAltura);
                    pessoaEnviada.setDataNascimento(pessoaDataNascimento);

                    new HttpRequestTaskPessoaProcurada().execute(pessoaEnviada);
                    //Definindo Avistamento dentro da asyncPessoa

                    //Definindo circunstancia dentro da asyncAvistamento


                    //Definindo localizacao dentro da asyncCircunstancia


                }
            }
        }
    };

    private boolean validarCampos() {
        if (!etPessoaNome.getText().toString().matches("[a-zA-Z ]*") || etPessoaNome.length() < 2) {
            etPessoaNome.setError("Nome Inválido!");
            return false;
        }

        if (TextUtils.isEmpty(etPessoaGenero.getText().toString())) {
            etPessoaGenero.setError("Escolha o gênero!");
            return false;
        }

        if (TextUtils.isEmpty(etAltura.getText().toString()) || etAltura.length() < 3) {
            etAltura.setError("Altura inválida! (Digite em cm)");
            return false;
        } else {
            return true;
        }
      /*  etAltura.validate();
       etDataNascimento.validate();
        etPessoaGenero.validate();
        etCabeloTipo.validate();
        etPessoaTipo.validate();
        etPessoaNome.validate();
        etCabeloCor.validate();
        etEtnia.validate();
        etFisico.validate();
        etOlhos.validate();*/

    }

    private class HttpRequestTaskPessoaProcurada extends AsyncTask<PessoaProcurada, String, PessoaProcurada> {
        @Override
        protected PessoaProcurada doInBackground(PessoaProcurada... params) {
            Log.i("DEBUG", params[0].getNome().toString());
            PessoaProcurada pessoaRetornada = pessoaProcuradaDAO.chamaMetodoAdicionarPessoaProcurada(params[0]);
            return pessoaRetornada;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            com.rey.material.app.Dialog.Builder builder;
            builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
            };
            ((SimpleDialog.Builder) builder).message("Aguarde...");
            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getFragmentManager(), null);
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(PessoaProcurada pessoa) {
            if (pessoa != null) {
                Toast.makeText(getContext(), "Pessoa Cadastrada!!", Toast.LENGTH_LONG).show();
                //Definindo avistamento
                Avistamento avistamentoEnviado = new Avistamento();
                avistamentoEnviado.setPessoaProcurada(pessoa);
                avistamentoEnviado.setUsuario(pessoa.getUsuario());
                //replaceFragment(new InicioFragment());
                new HttpRequestTaskAvistamento().execute(avistamentoEnviado);

            } else {
                Toast.makeText(getContext(), "Pessoa não cadastrada!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class HttpRequestTaskAvistamento extends AsyncTask<Avistamento, String, Avistamento> {
        @Override
        protected Avistamento doInBackground(Avistamento... params) {
            Log.i("DEBUG", params[0].getPessoaProcurada().getNome().toString());
            Avistamento avistamentoRetornado = avistamentoDAO.chamaMetodoAdicionarAvistamento(params[0]);
            return avistamentoRetornado;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            com.rey.material.app.Dialog.Builder builder;
            builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
            };
            ((SimpleDialog.Builder) builder).message("Aguarde...");
            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getFragmentManager(), null);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Avistamento avistamento) {
            if (avistamento != null) {
                Toast.makeText(getContext(), "Avistamento Cadastrado!!", Toast.LENGTH_LONG).show();
                //Definindo Circunstancia
                Circunstancia circunstanciaEnviada = new Circunstancia();
                circunstanciaEnviada.setAvistamento(avistamento);
                circunstanciaEnviada.setData(pessoaDataAvistamento);
                circunstanciaEnviada.setDetalhes(detalhesAvistamento);
                new HttpRequestTaskCircunstancia().execute(circunstanciaEnviada);

            } else {
                Toast.makeText(getContext(), "Avistamento não cadastrado!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class HttpRequestTaskCircunstancia extends AsyncTask<Circunstancia, String, Circunstancia> {
        @Override
        protected Circunstancia doInBackground(Circunstancia... params) {
            Log.i("DEBUG", params[0].getDetalhes().toString());
            Circunstancia circunstanciaRetornada = circunstanciaDAO.chamaMetodoAdicionarCircunstancia(params[0]);
            return circunstanciaRetornada;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            com.rey.material.app.Dialog.Builder builder;
            builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
            };
            ((SimpleDialog.Builder) builder).message("Aguarde...");
            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getFragmentManager(), null);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Circunstancia circunstancia) {
            if (circunstancia != null) {
                Toast.makeText(getContext(), "Circunstancia Cadastrada!!", Toast.LENGTH_LONG).show();
                //Definindo Localizacao
                Localizacao localizacaoEnviada = new Localizacao();
                localizacaoEnviada.setCircunstancia(circunstancia);
                localizacaoEnviada.setDescricao(localizacaoAvistamentoDetalhes);
                localizacaoEnviada.setCidade(localizacaoAvistamentoCidade);
                localizacaoEnviada.setUf(localizacaoAvistamentoEstado);
                localizacaoEnviada.setLatitude(localizacaoAvistamentoLatitude);
                localizacaoEnviada.setLongitude(localizacaoAvistamentoLongitude);

                new HttpRequestTaskLocalizacao().execute(localizacaoEnviada);
            } else {
                Toast.makeText(getContext(), "Circunstancia não cadastrada!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class HttpRequestTaskLocalizacao extends AsyncTask<Localizacao, String, Localizacao> {
        @Override
        protected Localizacao doInBackground(Localizacao... params) {
            Log.i("DEBUG", params[0].getDescricao().toString());
            Localizacao localizacaoRetornada = localizacaoDAO.chamaMetodoAdicionarLocalizacao(params[0]);
            return localizacaoRetornada;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            com.rey.material.app.Dialog.Builder builder;
            builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
            };
            ((SimpleDialog.Builder) builder).message("Aguarde...");
            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getFragmentManager(), null);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Localizacao localizacao) {
            if (localizacao != null) {
                Toast.makeText(getContext(), "Localizacao Cadastrada!!", Toast.LENGTH_LONG).show();

                replaceFragment(new InicioFragment());

            } else {
                Toast.makeText(getContext(), "Localizacao não cadastrada!", Toast.LENGTH_LONG).show();
            }
        }
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

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {

    }


    @Override
    public void onConnectionSuspended(int i) {

    }


    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i("DEBUG", "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(googleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getContext(), "Nome Local: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.i("Debug", "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e("Debug", "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            Geocoder geoCoder = new Geocoder(getContext());

            // Format details of the place for display and show it in a TextView.
            //PEGANDO DETALHES DE UM LOCAL
            localizacaoAvistamentoDetalhes = place.getAddress().toString();
            localizacaoAvistamentoLatitude = (float) place.getLatLng().latitude;
            localizacaoAvistamentoLongitude = (float) place.getLatLng().longitude;
            try {
                List<Address> geoCoderEndereco = geoCoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                //geoCoderEndereco.get(0).getAdminArea();

                localizacaoAvistamentoCidade = geoCoderEndereco.get(0).getLocality();
                localizacaoAvistamentoEstado = geoCoderEndereco.get(0).getAdminArea();

                Toast.makeText(getContext(),
                        "Endereço place: " + place.getAddress().toString(),
                        Toast.LENGTH_LONG).show();

                Toast.makeText(getContext(),
                        "Cidade: " + geoCoderEndereco.get(0).getLocality(),
                        Toast.LENGTH_LONG).show();


                Toast.makeText(getContext(),
                        "Estado: " + geoCoderEndereco.get(0).getAdminArea(),
                        Toast.LENGTH_LONG).show();

            } catch (java.io.IOException e){
                e.printStackTrace();
            }
            /*mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));*/

            Log.i("DEBUG", "Place details received: " + place.getName());

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e("DEBUG", res.getString(R.string.hello_world, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.hello_world, name, id, address, phoneNumber,
                websiteUri));

    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e("DEBUG", "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(getContext(),
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }
}
