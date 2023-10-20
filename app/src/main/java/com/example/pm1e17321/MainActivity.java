package com.example.pm1e17321;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm1e17321.config.SQLiteConexion;
import com.example.pm1e17321.config.transacciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String Ruta,err;
    ImageView foto;
    EditText nombres,phone,nota;
    Spinner Pais;
    Button salvar,salvados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foto = (ImageView) findViewById(R.id.IvPerfil);
        Pais = (Spinner) findViewById(R.id.SpinPais);
        nombres = (EditText) findViewById(R.id.EtName);
        phone = (EditText) findViewById(R.id.EtPhone);
        nota = (EditText) findViewById(R.id.EtNota);
        salvar = (Button) findViewById(R.id.btnsalvar);
        salvados = (Button) findViewById(R.id.Csalvados);
        permisos();

        Intent intent = new Intent(this, ListActivity.class);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permisos();
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddPerson();
                startActivity(intent);
            }
        });

    }





    private void permisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 101);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void Tomarfoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 102);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Ruta = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getApplicationContext(), "Permiso Denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.PM1E17321.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 102);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == RESULT_OK) {
            // Add the image to the gallery
            galleryAddPic(Ruta);

            try {
                File fotos = new File(Ruta);
                foto.setImageURI(Uri.fromFile(fotos));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void AddPerson() {

        try {
            SQLiteConexion conexion = new SQLiteConexion(this, transacciones.namedb, null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();

            //String selectedItem = Pais.getSelectedItem().toString();

            //valores.put(transacciones.pais, Pais.getSelectedItem().toString());
            valores.put(transacciones.nombres, nombres.getText().toString());
            valores.put(transacciones.telefono, phone.getText().toString());
            valores.put(transacciones.nota, nota.getText().toString());

            Long result = db.insert(transacciones.Tabla, transacciones.id, valores);

            Intent intentcreate = new Intent(getApplicationContext(), MainActivity.class);
            Toast.makeText(this, getString(R.string.res), Toast.LENGTH_SHORT).show();
            db.close();
            startActivity(intentcreate);


        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.err), Toast.LENGTH_SHORT).show();
        }
    }



}
