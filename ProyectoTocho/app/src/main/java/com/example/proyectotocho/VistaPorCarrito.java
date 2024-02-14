package com.example.proyectotocho;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class VistaPorCarrito extends AppCompatActivity {

    private static DbHelper dbHelper;
    private String id_usuario;
    private RecyclerView recyclerView;

    public static ProductoAdapterCarrito adapter;

    private static float precioFinalFloat;

    private static TextView precioFinal;
    private Button btnComprar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_por_carrito);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        dbHelper = new DbHelper(this);

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.e("uwu", String.valueOf(MainActivity.userId));
        id_usuario = String.valueOf(MainActivity.userId);
        adapter = new ProductoAdapterCarrito(this, obtenerCarrito(id_usuario));
        recyclerView.setAdapter(adapter);

        //precio final
        precioFinalFloat=0;
        precioFinal = findViewById(R.id.precioFinal);
        btnComprar = findViewById(R.id.botonComprar);

        actualizarPrecioTotal();

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.actualizarLista(obtenerCarrito(id_usuario));
    }


    public static List<Producto> obtenerCarrito(String id_usuario) {
        List<Producto> productos = new ArrayList<>();

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Utilizamos el método rawQuery para realizar la consulta SQL
        Cursor cursor = db.rawQuery("SELECT piezas.id, piezas.nombre, piezas.descripcion, piezas.precio, piezas.stock, piezas.imagen_url FROM carritos INNER JOIN piezas ON carritos.id_producto = piezas.id WHERE carritos.id_usuario = ?;", new String[]{id_usuario});

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

    public static void actualizarPrecioTotal(){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(precio * cantidad_producto) AS precio_total\n" +
                "FROM carritos\n" +
                "JOIN piezas ON carritos.id_producto = piezas.id\n" +
                "WHERE carritos.id_usuario = ?;\n", new String[]{String.valueOf(MainActivity.userId)});
        if (cursor!=null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            precioFinalFloat = cursor.getInt(0);

            precioFinal.setText("Precio total: "+String.valueOf(precioFinalFloat)+"€");
        }
        cursor.close();
        db.close();
    }

}
