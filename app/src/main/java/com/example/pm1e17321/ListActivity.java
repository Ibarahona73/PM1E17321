package com.example.pm1e17321;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm1e17321.Models.Contactos;
import com.example.pm1e17321.config.SQLiteConexion;
import com.example.pm1e17321.config.transacciones;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listado;
    ArrayList<Contactos> listcontacto;
    ArrayList<String> ArregloPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        try {

            //Establecemos una conexion a base de datos
            conexion = new SQLiteConexion(this, transacciones.namedb,null,1);
            listado = (ListView) findViewById(R.id.lista);
            GetPersons();

            ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                    ArregloPersonas);

            listado.setAdapter(adp);

            listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    String ItemPerson = listcontacto.get(i).getNombres();
                    Toast.makeText(ListActivity.this,"Nombre " + ItemPerson, Toast.LENGTH_LONG);
                }
            });


        }
        catch(Exception e){
            e.toString();
        }

    }


    private void GetPersons() {

        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactos person = null;
        listcontacto = new ArrayList<Contactos>();

        Cursor cursor = db.rawQuery(transacciones.SelectTablePersonas,null);

        while(cursor.moveToNext()){

            person = new Contactos();
            person.setId(cursor.getInt(0));
            // person.setPais(cursor.getString(1));
            person.setNombres(cursor.getString(2));
            person.setTelefono(cursor.getInt(3));
            person.setNota(cursor.getString(4));

            listcontacto.add(person);

        }
        cursor.close();
        fillList();
    }

    private void fillList() {

        ArregloPersonas = new ArrayList<String>();

        for(int i =0;i<listcontacto.size();i++){

            ArregloPersonas.add(listcontacto.get(i).getId() + ".  " +
                    //listcontacto.get(i).getPais()+ " - "+
                    listcontacto.get(i).getNombres()+ " - "+
                    listcontacto.get(i).getTelefono()+ "\n\t"+
                    listcontacto.get(i).getNota());
        }
    }

}