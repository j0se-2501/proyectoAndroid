package com.example.proyectotocho;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class VistaPorCategoria extends AppCompatActivity {

    private DbHelper dbHelper;
    private String categoriaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_por_categoria);

        dbHelper = new DbHelper(this);

        // Obtener la categoría seleccionada del intent
        Intent intent = getIntent();
        categoriaSeleccionada = intent.getStringExtra("CATEGORIA");

        // Mostrar la categoría en el título de la actividad
        setTitle("Piezas de " + categoriaSeleccionada);

        // Obtener las piezas de la categoría seleccionada
        List<String> piezas = obtenerPiezasPorCategoria(categoriaSeleccionada);

        // Configurar el adaptador para la ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, piezas);

        // Configurar la ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    private List<String> obtenerPiezasPorCategoria(String categoria) {
        List<String> piezas = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Utilizamos el método rawQuery para realizar la consulta SQL
        Cursor cursor = db.rawQuery("SELECT nombre FROM piezas WHERE categoria_id = (SELECT id FROM categorias WHERE nombre = ?)", new String[]{categoria});

        // Verificamos si el cursor no es nulo y si hay al menos una columna
        if (cursor != null && cursor.getCount() > 0) {
            // Movemos el cursor a la primera fila
            cursor.moveToFirst();

            // Obtenemos el índice de la columna "nombre"
            int columnIndex = cursor.getColumnIndex("nombre");

            // Recorremos el cursor y agregamos nombres de piezas a la lista
            do {
                String nombrePieza = cursor.getString(columnIndex);
                piezas.add(nombrePieza);
            } while (cursor.moveToNext());

            // Cerramos el cursor
            cursor.close();
        }

        // Cerramos la base de datos
        db.close();

        return piezas;
    }

}
