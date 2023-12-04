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
        // Access the database
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            // Perform a query to get all products
            Cursor cursor = db.query("piezas", null, null, null, null, null, null);

            // Check if there are products in the database
            if (cursor.getCount() > 0) {
                // Process the results of the query
                int idIndex = cursor.getColumnIndex("id");
                int nombreIndex = cursor.getColumnIndex("nombre");
                int precioIndex = cursor.getColumnIndex("precio");
                int categoriaindex =cursor.getColumnIndex("categoria_id");
                ArrayList<String> productosList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    // Verify if the columns exist in the result set
                    if (idIndex >= 0 && nombreIndex >= 0 && precioIndex >= 0) {
                        int id = cursor.getInt(idIndex);
                        String nombre = cursor.getString(nombreIndex);
                        double precio = cursor.getDouble(precioIndex);
                        int categoria=cursor.getInt(categoriaindex);
                        // Add product information to the list
                        productosList.add("ID: " + id + ", Nombre: " + nombre + ", Precio: $" + precio + ", Categoria: "+ categoria);
                    } else {
                        // Show an error message if any column doesn't exist
                        Toast.makeText(this, "Error obtaining data from the database", Toast.LENGTH_SHORT).show();
                    }
                }

                // Show the list of products in the ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosList);
                listViewProductos.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No hay productos en la base de datos", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            // Close the cursor and database
            if (db != null) {
                db.close();
            }
        }
    }
}
