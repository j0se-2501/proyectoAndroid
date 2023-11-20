// MostrarProductosActivity.java

package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MostarrProductosActivity extends AppCompatActivity {
    private ListView listViewProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostarr_productos);

        listViewProductos = findViewById(R.id.listViewProductos);

        mostrarProductos();
    }

    private void mostrarProductos() {
        // Accede a la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Realiza una consulta para obtener todos los productos
        Cursor cursor = db.query("piezas", null, null, null, null, null, null);

// Verifica si hay productos en la base de datos
        if (cursor.getCount() > 0) {
            // Procesa los resultados de la consulta
            int idIndex = cursor.getColumnIndex("id");
            int nombreIndex = cursor.getColumnIndex("nombre");
            int precioIndex = cursor.getColumnIndex("precio");

            ArrayList<String> productosList = new ArrayList<>();
            while (cursor.moveToNext()) {
                // Verifica si las columnas existen en el conjunto de resultados
                if (idIndex >= 0 && nombreIndex >= 0 && precioIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String nombre = cursor.getString(nombreIndex);
                    double precio = cursor.getDouble(precioIndex);

                    // Agrega la información del producto a la lista
                    productosList.add("ID: " + id + ", Nombre: " + nombre + ", Precio: $" + precio);
                } else {
                    // Muestra un mensaje de error si alguna columna no existe
                    Toast.makeText(this, "Error al obtener datos de la base de datos", Toast.LENGTH_SHORT).show();
                }
            }

            // Muestra la lista de productos en el ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosList);
            listViewProductos.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No hay productos en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
}