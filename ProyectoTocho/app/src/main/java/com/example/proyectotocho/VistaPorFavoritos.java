package com.example.proyectotocho;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VistaPorFavoritos extends AppCompatActivity {

    private static DbHelper dbHelper;
    private String id_usuario;
    private RecyclerView recyclerView;
    public static ProductoAdapter adapter;

private Button btnComprar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_por_categoria);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        dbHelper = new DbHelper(this);

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        id_usuario = String.valueOf(1);
        adapter = new ProductoAdapter(this, obtenerFavoritos(id_usuario));
        recyclerView.setAdapter(adapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.actualizarLista(obtenerFavoritos(id_usuario));
    }


    public static List<Producto> obtenerFavoritos(String id_usuario) {
        List<Producto> productos = new ArrayList<>();

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Utilizamos el método rawQuery para realizar la consulta SQL
        Cursor cursor = db.rawQuery("SELECT piezas.id, piezas.nombre, piezas.descripcion, piezas.precio, piezas.stock, piezas.imagen_url FROM favoritos INNER JOIN piezas ON favoritos.id_producto = piezas.id WHERE favoritos.id_usuario = ?;", new String[]{id_usuario});

        // Verificamos si el cursor no es nulo y si hay al menos una columna
        if (cursor != null && cursor.getCount() > 0) {
            // Movemos el cursor a la primera fila
            cursor.moveToFirst();

            // Obtenemos los índices de las columnas
            int indexId = cursor.getColumnIndex("id");
            int indexNombre = cursor.getColumnIndex("nombre");
            int indexDescripcion = cursor.getColumnIndex("descripcion");
            int indexPrecio = cursor.getColumnIndex("precio");
            int indexStock = cursor.getColumnIndex("stock");
            int indexImagenUrl = cursor.getColumnIndex("imagen_url");

            // Recorremos el cursor y agregamos productos a la lista
            do {
                int id = cursor.getInt(indexId);
                String nombre = cursor.getString(indexNombre);
                String descripcion = cursor.getString(indexDescripcion);
                String precio = cursor.getString(indexPrecio);
                int stock = cursor.getInt(indexStock);
                String imagenUrl = cursor.getString(indexImagenUrl);

                Producto producto = new Producto(id, nombre, descripcion, precio, stock, imagenUrl);
                productos.add(producto);
            } while (cursor.moveToNext());

            for (Producto producto : productos) {
                Log.e("e", producto.getNombre()+productos.indexOf(producto));
            }

            // Cerramos el cursor
            cursor.close();
        }

        // Cerramos la base de datos
        db.close();

        return productos;

        } catch(NullPointerException e) {

        }

        return productos;
    }

}
