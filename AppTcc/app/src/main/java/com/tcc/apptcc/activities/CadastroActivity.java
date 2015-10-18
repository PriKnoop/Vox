package com.tcc.apptcc.activities;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tcc.apptcc.adapters.images.CropOption;
import com.tcc.apptcc.adapters.images.CropOptionAdapter;
import com.tcc.apptcc.daos.UsuarioDAO;
import com.tcc.apptcc.pojos.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    EditText etLoginCadastro;
    EditText etSenhaCadastro;
    EditText etSenhaRepetida;
    Button btCadastrar;

    ImageButton ibSelecionarFotoUsuario;
    UsuarioDAO usuarioDAO;

    String loginDigitado;
    String senhaDigitada;
    String senhaRepetidaDigitada;
    public final static String NOME_PREFERENCIA = "preferencias_usuario";

    private Uri mImageCaptureUri;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
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
                        etLoginCadastro.setError("Login indisponÃ­vel");

                    } else if (!senhaRepetidaDigitada.equals(senhaDigitada)) {
                        etSenhaRepetida.setError("As senhas nÃ£o coincidem!");
                    } else {

                        new HttpRequestTask().execute(loginDigitado, senhaDigitada, senhaRepetidaDigitada);


                    }
                }

            }
        });

        final String [] items			= new String [] {"Tirar foto", "Escolher da galeria"};
        ArrayAdapter<String> adapter	= new ArrayAdapter<String> (CadastroActivity.this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder		= new AlertDialog.Builder(CadastroActivity.this);

        builder.setTitle("Selecionar foto");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { //pick from camera
                if (item == 0) {
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
            }});
    }

    private void pickFromGallery(){
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        int targetW = ibSelecionarFotoUsuario.getWidth();
        int targetH = ibSelecionarFotoUsuario.getHeight();

            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 0);
            intent.putExtra("aspectY", 0);
            intent.putExtra("outputX", targetW);
            intent.putExtra("outputY", targetH);
            startActivityForResult(intent, PICK_FROM_CAMERA);


        try {

            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent, "Complete usando"), PICK_FROM_FILE);


        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void pickFromCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Log.i("DEBUG", "<--------> URL 1:" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        Log.i("DEBUG", "<--------> URL 2:" + Environment.getDataDirectory().getPath().toString());
        Log.i("DEBUG", "<--------> URL 3:" + MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }

        int targetW = ibSelecionarFotoUsuario.getWidth();
        int targetH = ibSelecionarFotoUsuario.getHeight();

        // Continue only if the File was successfully created
        if (photoFile != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photoFile));
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 0);
            intent.putExtra("aspectY", 0);
            intent.putExtra("outputX", targetW);
            intent.putExtra("outputY", targetH);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }

        //URL UTILIZADA PARA SALVAR IMAGENS
        //mImageCaptureUri = Uri.fromFile(new File(Environment.getDataDirectory(),
          //      "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

        //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

        // ******** code for crop image
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "VoxApp");

        // Create the storage directory if it does not exist
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

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        galleryAddPic();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        Log.i("DEBUG", "<--------> URL OIIIIIIII:" + contentUri.toString());
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = ibSelecionarFotoUsuario.getWidth();
        int targetH = ibSelecionarFotoUsuario.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		/* Associate the Bitmap to the ImageView */
        ibSelecionarFotoUsuario.setImageBitmap(bitmap);
    }



    private void inicializaComponentes() {
        etLoginCadastro = (EditText) findViewById(R.id.et_login_cadastro);
        etSenhaCadastro = (EditText) findViewById(R.id.et_senha_cadastro);
        etSenhaRepetida = (EditText) findViewById(R.id.et_senha_repetida);
        btCadastrar = (Button) findViewById(R.id.ib_cadastrar);
        ibSelecionarFotoUsuario = (ImageButton) findViewById(R.id.ib_foto_usuario);
        usuarioDAO = new UsuarioDAO();
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
    private class HttpRequestTask extends AsyncTask<String, String, Usuario> {


        @Override
        protected Usuario doInBackground(String... params) {
            Usuario usuarioRetornado = usuarioDAO.chamaMetodoAdicionarUsuario(params[0].toString(), params[1].toString() );
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
                // doCrop();
                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                if(data == null){
                    pickFromGallery();

                } else {

                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        ibSelecionarFotoUsuario.setImageBitmap(photo);
                    }
                }

            case PICK_FROM_FILE:
                if (data != null) {
                    Bundle extras2 = data.getExtras();
                    Bitmap photo = extras2.getParcelable("data");
                    ibSelecionarFotoUsuario.setImageBitmap(photo);

                }
                //doCrop();

                break;

            case CROP_FROM_CAMERA:

                //mImageCaptureUri = data.getData();

              //  doCrop();
                 Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");

                    ibSelecionarFotoUsuario.setImageBitmap(photo);

                    //mImageView.setImageBitmap(photo);
                }

                File f = new File(mImageCaptureUri.getPath());

                if (f.exists()) f.delete();

                break;

        }
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );

        int size = list.size();

        if (size == 0) {
            Toast.makeText(this, "Aplicativo de recorte nÃ£o encontrado", Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i 		= new Intent(intent);
                ResolveInfo res	= list.get(0);

                i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent= new Intent(intent);

                    co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Escolher aplicativo para recorte");
                builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int item ) {
                        startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel( DialogInterface dialog ) {

                        if (mImageCaptureUri != null ) {
                            getContentResolver().delete(mImageCaptureUri, null, null );
                            mImageCaptureUri = null;
                        }
                    }
                } );

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }
}




