package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MostarrClienteActivity extends AppCompatActivity {

    private ListView listViewClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostarr_cliente);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        listViewClientes = findViewById(R.id.listViewClientes);

        mostrarClientes();
    }

    private void mostrarClientes() {
        // Accede a la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Realiza una consulta para obtener todos los productos
        Cursor cursor = db.query("usuarios", null, null, null, null, null, null);

// Verifica si hay productos en la base de datos
        if (cursor.getCount() > 0) {
            // Procesa los resultados de la consulta
            int idIndex = cursor.getColumnIndex("id");
            int correoIndex = cursor.getColumnIndex("correo");

            ArrayList<String> clientesList = new ArrayList<>();
            while (cursor.moveToNext()) {
                // Verifica si las columnas existen en el conjunto de resultados
                if (idIndex >= 0 && correoIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String correo = cursor.getString(correoIndex);

                    // Agrega la información del producto a la lista
                   clientesList.add("ID: " + id + ", Correo: " + correo);
                } else {
                    // Muestra un mensaje de error si alguna columna no existe
                    Toast.makeText(this, "Error al obtener datos de la base de datos", Toast.LENGTH_SHORT).show();
                }
            }

            // Muestra la lista de clientes en el ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientesList);
            listViewClientes.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No hay clientes en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
}