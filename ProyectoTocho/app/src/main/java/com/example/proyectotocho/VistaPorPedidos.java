package com.example.proyectotocho;

import static com.example.proyectotocho.MainActivity.userId;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class VistaPorPedidos extends AppCompatActivity {

    private static DbHelper dbHelper;

    private RecyclerView recyclerView;
    public static PedidosAdapter adapter;
    private DrawerLayout drawerLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_por_pedidos);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        dbHelper = new DbHelper(this);

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PedidosAdapter(this, obtenerPedidosPorIdDeUsuario(MainActivity.userId));
        recyclerView.setAdapter(adapter);

        drawerLayout = findViewById(R.id.drawer_layout);

        ImageButton drawerButton = findViewById(R.id.buttonAbrirDrawer);

        drawerButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

    }

    public void onProfileClick(MenuItem menuItem) {
        Intent intent = new Intent(VistaPorPedidos.this, ActivityPerfil.class);
        intent.putExtra("USER_ID", String.valueOf(userId)); // Replace userId with the actual user ID variable
        startActivity(intent);
    }

    public void onFavoritesClick(MenuItem menuItem) {
        Intent intentfavoritos = new Intent(VistaPorPedidos.this, VistaPorFavoritos.class);
        startActivity(intentfavoritos);
    }

    public void onTiendaClick(MenuItem menuItem) {
        Intent intenttienda = new Intent(VistaPorPedidos.this, UserActivity.class);
        startActivity(intenttienda);
    }
    public void onCarritoClick(MenuItem menuItem) {
        Intent intentCarrito = new Intent(VistaPorPedidos.this, VistaPorCarrito.class);
        startActivity(intentCarrito);
    }
    public void onCerrarSesionClick(MenuItem menuItem) {
        Intent intentCerrarSesion = new Intent(VistaPorPedidos.this, MainActivity.class);
        startActivity(intentCerrarSesion);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.actualizarLista(obtenerPedidosPorIdDeUsuario(MainActivity.userId));
    }

    public static List<Pedido> obtenerPedidosPorIdDeUsuario(int idUsuario) {

        List<Pedido> pedidos = new ArrayList<>();

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();


            // Utilizamos el método rawQuery para realizar la consulta SQL
            Cursor cursor = db.rawQuery("SELECT * FROM pedidos WHERE id_usuario = ?", new String[]{String.valueOf(MainActivity.userId)});

            // Verificamos si el cursor no es nulo y si hay al menos una columna
            if (cursor != null && cursor.getCount() > 0) {
                // Movemos el cursor a la primera fila
                cursor.moveToFirst();

                // Obtenemos los índices de las columnas
                int indexId = cursor.getColumnIndex("id");
                int indexFecha = cursor.getColumnIndex("fecha");
                int indexDireccion = cursor.getColumnIndex("direccion");
                int indexPrecio = cursor.getColumnIndex("precio");

                // Recorremos el cursor y agregamos productos a la lista
                do {
                    int id = cursor.getInt(indexId);
                    String fecha = cursor.getString(indexFecha);
                    float precio = Float.parseFloat(cursor.getString(indexPrecio));
                    String direccion = cursor.getString(indexDireccion);

                    Pedido pedido = new Pedido(id, idUsuario, fecha, direccion, precio);
                    pedidos.add(pedido);
                } while (cursor.moveToNext());

                // Cerramos el cursor
                cursor.close();
            }

            // Cerramos la base de datos
            db.close();

            for (Pedido pedido : pedidos) {
                Log.e("uwuw", pedido.toString());
            }

            return pedidos;

        } catch(NullPointerException e) {

        }

        return pedidos;
    }
}