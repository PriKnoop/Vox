package com.tcc.apptcc.activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.tcc.apptcc.daos.UsuarioDAO;
import com.tcc.apptcc.pojos.Usuario;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {

    EditText etLoginCadastro;
    EditText etSenhaCadastro;
    EditText etSenhaRepetida;
    Button btCadastrar;
    ImageButton ibSelecionarFotoUsuario;

    String loginDigitado;
    String senhaDigitada;
    String senhaRepetidaDigitada;

    UsuarioDAO usuarioDAO;
    Usuario usuarioEnviado;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";

    private Bitmap currentBitmap;
    String mCurrentPhotoPath;
    private String fotoPessoalEncoded;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        this.btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDigitado = etLoginCadastro.getText().toString();
                senhaDigitada = etSenhaCadastro.getText().toString();
                senhaRepetidaDigitada = etSenhaRepetida.getText().toString();
                if (validarCampos() == true){
                    usuarioEnviado.setLogin(loginDigitado);
                    usuarioEnviado.setSenha(senhaDigitada);
                    //usuarioEnviado.setFotoPessoalEncoded(fotoPessoalEncoded);
                    new HttpRequestTask().execute(usuarioEnviado);
                }
            }
        });

        final String [] items			= new String [] {"Tirar foto", "Escolher da galeria"};
        ArrayAdapter<String> adapter	= new ArrayAdapter<String> (CadastroActivity.this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder		= new AlertDialog.Builder(CadastroActivity.this);

        builder.setTitle("Selecionar foto");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) { //pick from camera
                    pickFromCamera();
                } else { //pick from file
                    pickFromGallery();
                }
            }
        });

        final AlertDialog dialog = builder.create();
        this.ibSelecionarFotoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private boolean validarCampos(){
        boolean retorno = false;
        if (TextUtils.isEmpty(loginDigitado) || TextUtils.isEmpty(senhaDigitada) || TextUtils.isEmpty(senhaRepetidaDigitada)) {
            if (TextUtils.isEmpty(loginDigitado)) {
                etLoginCadastro.setError("Por favor, insira seu login!");
            }
            if (TextUtils.isEmpty(senhaDigitada)) {
                etSenhaCadastro.setError("Insira sua senha!");
            }
            if (TextUtils.isEmpty(senhaRepetidaDigitada)) {
                etSenhaRepetida.setError("Insira sua senha novamente!");
            }
        } else {
            Boolean loginDisponivel = usuarioDAO.chamaMetodoVerificarLogin(loginDigitado);
            System.out.println("Senhas: >>" + senhaRepetidaDigitada + senhaDigitada);
            if (loginDisponivel == false) {
                etLoginCadastro.setError("Login indisponível");

            } else if (!senhaRepetidaDigitada.equals(senhaDigitada)) {
                etSenhaRepetida.setError("As senhas não coincidem!");
            } else {
                retorno = true;
            }
        }
        return retorno;
    }

    private void inicializaComponentes() {
        etLoginCadastro = (EditText) findViewById(R.id.et_login_cadastro);
        etSenhaCadastro = (EditText) findViewById(R.id.et_senha_cadastro);
        etSenhaRepetida = (EditText) findViewById(R.id.et_senha_repetida);
        btCadastrar = (Button) findViewById(R.id.ib_cadastrar);
        ibSelecionarFotoUsuario = (ImageButton) findViewById(R.id.ib_foto_usuario);
        usuarioDAO = new UsuarioDAO();
        usuarioEnviado = new Usuario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
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
    private class HttpRequestTask extends AsyncTask<Usuario, String, Usuario> {


        @Override
        protected Usuario doInBackground(Usuario... params) {
            Log.i("DEBUG", params[0].getLogin().toString());
            Usuario usuarioRetornado = usuarioDAO.chamaMetodoAdicionarUsuario(params[0]);
            return usuarioRetornado;

        }

        protected void onPostExecute(Usuario usuario) {
//            Log.i("DEBUG", usuario.getLogin());
//            Log.i("DEBUG", usuario.getSenha());
            if (usuario != null) {

                SharedPreferences preferences = getApplicationContext().getSharedPreferences(SplashActivity.NOME_PREFERENCIA, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong("id", usuario.getIdUsuario());
                editor.commit();

                Toast.makeText(CadastroActivity.this, "UsuÃ¡rio cadastrado!", Toast.LENGTH_LONG).show();
                Bundle parametros = new Bundle();

                parametros.putSerializable("usuario", usuario);
                Intent itTelaPrincipal = new Intent(CadastroActivity.this, HomeActivity.class);
                itTelaPrincipal.putExtras(parametros);
                startActivity(itTelaPrincipal);
            } else {

                Toast.makeText(CadastroActivity.this, "UsuÃ¡rio nÃ£o cadastrado!", Toast.LENGTH_LONG).show();
            }
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if(data == null){
                    pickFromGallery();
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        currentBitmap = photo;
                        ibSelecionarFotoUsuario.setImageBitmap(currentBitmap);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        currentBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] imageBytes = stream.toByteArray();
                        fotoPessoalEncoded = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    }
                }
            case PICK_FROM_FILE:
              //  doCrop();
                if (data != null) {
                    Bundle extras2 = data.getExtras();
                    Bitmap photo = extras2.getParcelable("data");
                    currentBitmap = photo;
                    ibSelecionarFotoUsuario.setImageBitmap(currentBitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    currentBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageBytes = stream.toByteArray();
                    fotoPessoalEncoded = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                }
                break;
        }
    }

    private void pickFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("scale", true);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent, "Complete usando"), PICK_FROM_FILE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void pickFromCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
        }
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        if (photoFile != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photoFile));

            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "VoxApp");
        if (! storageDir.exists()){
            if (! storageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".JPEG",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        galleryAddPic();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        Log.i("DEBUG", contentUri.toString());
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        int targetW = ibSelecionarFotoUsuario.getWidth();
        int targetH = ibSelecionarFotoUsuario.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ibSelecionarFotoUsuario.setImageBitmap(bitmap);
    }

  /*  public boolean salvarBitmapEmPasta(String path, Bitmap bitmap) throws IOException  {
        boolean res = false;
        String imageFileName = "JPEG_1";
        File pastaUsuario = new File (path);
        File file = new File(path, "filename.txt");
        Log.i("DEBUG", file.getAbsolutePath());

        if (! pastaUsuario.exists()){
            if (! pastaUsuario.mkdirs()){
                Log.d("pastaUsuario", "failed to create directory");
                return res;
            }
        }
        File image = File.createTempFile(
                imageFileName,
                ".JPEG",
                pastaUsuario
        );
        if (!image.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(image);
                res = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception e) { }
        }
        return res;
    } */
}




