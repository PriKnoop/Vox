package com.tcc.apptcc.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.FloatingActionButton;
import com.tcc.apptcc.adapters.Mascara;
import com.tcc.apptcc.daos.PessoaProcuradaDAO;
import com.tcc.apptcc.enuns.TipoPessoaProcurada;
import com.tcc.apptcc.pojos.PessoaProcurada;
import com.tcc.apptcc.pojos.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdicionarPessoasFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    EditText singleLineEllipsisEt;
    private OnFragmentInteractionListener mListener;
    private EditText bottomTextEt;
    private Button setErrorBt, setError2Bt, setError3Bt, validateBt;
    private MaterialEditText validationEt;
    private TextWatcher mascaraAltura;
    FloatingActionButton btEnviarPessoa;

    PessoaProcuradaDAO pessoaProcuradaDAO;

    SharedPreferences preferences;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";

    private MaterialEditText etPessoaNome, etPessoaTipo, etPessoaGenero, etDataNascimento, etEtnia, etOlhos, etFisico, etCabeloCor, etCabeloTipo, etAltura;
    private String pessoaNome, pessoaTipo, pessoaGenero, pessoaEtnia, pessoaOlhos, pessoaFisico, pessoaCabeloCor, pessoaCabeloTipo;
    private TipoPessoaProcurada tipoPessoaProcurada;
    private double pessoaAltura;
    private Date pessoaDataNascimento;
    private Long idUsuario;

    com.rey.material.app.Dialog.Builder builderSituacao, builderGenero, builderDataNascimento, builderEtnia, builderOlhos, builderFisico, builderCabeloCor, builderCabeloTipo, builderAltura;

    public static AdicionarPessoasFragment newInstance(String param1, String param2) {
        AdicionarPessoasFragment fragment = new AdicionarPessoasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AdicionarPessoasFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adicionar_pessoas, container, false);
        inicializaComponentes(view);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etPessoaTipo.setOnClickListener(this);
        etPessoaGenero.setOnClickListener(this);
        etDataNascimento.setOnClickListener(this);
        etFisico.setOnClickListener(this);
        etEtnia.setOnClickListener(this);
        etOlhos.setOnClickListener(this);
        etCabeloCor.setOnClickListener(this);
        etCabeloTipo.setOnClickListener(this);
        btEnviarPessoa.setOnClickListener(listener_delay);
        etAltura.addValidator(new RegexpValidator("Digite apenas números!", "\\d+"));



        initSingleLineEllipsisEt();
        initSetErrorEt();
        initValidationEt();
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
        btEnviarPessoa = (FloatingActionButton)view.findViewById(R.id.button_bt_float_color);

        mascaraAltura = Mascara.insert("#,##", etAltura);

        pessoaProcuradaDAO = new PessoaProcuradaDAO();

        singleLineEllipsisEt = (EditText) view.findViewById(R.id.singleLineEllipsisEt);
        bottomTextEt = (EditText) view.findViewById(R.id.bottomTextEt);
        setErrorBt = (Button) view.findViewById(R.id.setErrorBt);
        setError2Bt = (Button) view.findViewById(R.id.setError2Bt);
        setError3Bt = (Button) view.findViewById(R.id.setError3Bt);
        validationEt = (MaterialEditText) view.findViewById(R.id.validationEt);
        validateBt = (Button) view.findViewById(R.id.validateBt);
    }

    private void initSingleLineEllipsisEt() {
        singleLineEllipsisEt.setSelection(singleLineEllipsisEt.getText().length());
    }

    private void initSetErrorEt() {
        setErrorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomTextEt.setError("1-line Error!");
            }
        });
        setError2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomTextEt.setError("2-line\nError!");
            }
        });
        setError3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomTextEt.setError("So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors! So Many Errors!");
            }
        });
    }

    private void initValidationEt() {
        validationEt.addValidator(new RegexpValidator("Only Integer Valid!", "\\d+"));
        validateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate
                validationEt.validate();
            }
        });
    }

    public void onClick(View v) {
        com.rey.material.app.Dialog.Builder builder = null;

        switch (v.getId()) {
            case R.id.et_pessoa_tipo:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        for (TipoPessoaProcurada tipo : TipoPessoaProcurada.values()){
                            if(getSelectedValue().toString().equals(tipo.descricao)){
                                tipoPessoaProcurada = tipo;
                            }

                        }
                        etPessoaTipo.setText(getSelectedValue().toString());
                        fragment.dismiss();
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Desaparecido", "Morador de rua", "Abrigado"}, 0)
                        .title("Situação")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
            case R.id.et_pessoa_genero:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        pessoaGenero = getSelectedValue().toString();
                        etPessoaGenero.setText(pessoaGenero);
                        fragment.dismiss();
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
                        pessoaEtnia = getSelectedValue().toString();
                        etEtnia.setText(pessoaEtnia);
                        fragment.dismiss();
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
                        pessoaOlhos = getSelectedValue().toString();
                        etOlhos.setText(pessoaOlhos);
                        fragment.dismiss();
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
                        pessoaCabeloCor = getSelectedValue().toString();
                        etCabeloCor.setText(pessoaCabeloCor);
                        fragment.dismiss();
                    }

                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                        fragment.dismiss();
                    }
                };
                ((SimpleDialog.Builder) builder).items(new String[]{"Loiro", "Preto", "Castanho", "Ruivo", "Grisalho", "Outro"}, 0)
                        .title("Cor dos cabelos")
                        .positiveAction("OK")
                        .negativeAction("CANCELAR");
                break;
            case R.id.et_pessoa_cabelotipo:
                builder = new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        pessoaCabeloTipo = getSelectedValue().toString();
                        etCabeloTipo.setText(pessoaCabeloTipo);
                        fragment.dismiss();
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
                builder= new SimpleDialog.Builder(R.style.SimpleDialog) {
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        pessoaFisico = getSelectedValue().toString();
                        etFisico.setText(pessoaFisico);
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
                        DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                        etDataNascimento.setText(dataString);
                        try {
                            pessoaDataNascimento = formatador.parse(dataString);
                        }catch(Exception e){
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


    View.OnClickListener listener_delay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v instanceof FloatingActionButton){
                FloatingActionButton bt = (FloatingActionButton)v;
                bt.setLineMorphingState((bt.getLineMorphingState() + 1) % 2, true);
                Toast.makeText(getActivity(), "Cliquei em enviar", Toast.LENGTH_SHORT).show();
                verificaSeUsuarioEstaLogado();
                validarCampos();

                if(validarCampos()){
                    pessoaNome = etPessoaNome.getText().toString();
                    pessoaAltura = Double.parseDouble(etAltura.getText().toString());


                    Usuario usuarioCadastrou = new Usuario();
                    usuarioCadastrou.setIdUsuario(idUsuario);

                    PessoaProcurada pessoaEnviada = new PessoaProcurada();
                    pessoaEnviada.setUsuario(usuarioCadastrou);
                    pessoaEnviada.setNome(pessoaNome);
                    pessoaEnviada.setTipoPessoaProcurada(tipoPessoaProcurada);
                    pessoaEnviada.setAltura(pessoaAltura);

                    new HttpRequestTaskPessoaProcurada().execute(pessoaEnviada);



                }


            }

//                System.out.println(v + " " + ((RippleDrawable)v.getBackground()).getDelayClickType());
        }
    };

    private boolean validarCampos(){
        boolean valido = false;
        if (!etPessoaNome.getText().toString().matches("[a-zA-Z ]*") || etPessoaNome.length() < 2) {
            etPessoaNome.setError("Nome Inválido!");
            return false; }

            if (TextUtils.isEmpty(pessoaGenero)) {
                etPessoaGenero.setError("Escolha o gênero!");
                return false;
            }

        if(TextUtils.isEmpty(etAltura.getText().toString()) ||etAltura.length() < 3){
            etAltura.setError("Altura inválida! (Digite em cm)");
            return false;
        }
        else {
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

        protected void onProgressUpdate() {
            com.rey.material.app.Dialog.Builder builder = null;
            builder = new SimpleDialog.Builder(R.style.SimpleDialog){



            };

            ((SimpleDialog.Builder)builder).message("Aguarde...");
            DialogFragment fragment = DialogFragment.newInstance(builder);
            fragment.show(getFragmentManager(), null);
        }

        protected void onPostExecute(PessoaProcurada pessoa) {
            if (pessoa != null) {
                Toast.makeText(getContext(), "Pessoa Cadastrada!!", Toast.LENGTH_LONG).show();
                replaceFragment(new InicioFragment());
            } else {
                Toast.makeText(getContext(), "Pessoa não cadastrada!", Toast.LENGTH_LONG).show();
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

}
