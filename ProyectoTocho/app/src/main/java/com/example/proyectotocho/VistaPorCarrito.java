package com.example.proyectotocho;

import static com.example.proyectotocho.MainActivity.userId;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VistaPorCarrito extends AppCompatActivity {

    private static DbHelper dbHelper;
    private String id_usuario;
    private RecyclerView recyclerView;

    public static ProductoAdapterCarrito adapter;

    int idPedido;

    private static float precioFinalFloat;

    private static TextView precioFinal;
    private Button btnComprar;
    private DrawerLayout drawerLayout;
    Context context=this;

    ArrayList<Integer> idProductosEnCarrito = new ArrayList<Integer>();

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


                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Crea un objeto ContentValues para insertar los datos en la tabla "piezas"
                ContentValues values = new ContentValues();
                values.put("id_usuario", String.valueOf(MainActivity.userId));
                // on below line we are creating and initializing
                // variable for simple date format.
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

                // on below line we are creating a variable
                // for current date and time and calling a simple date format in it.
                String currentDateAndTime = sdf.format(new Date());
                values.put("fecha", currentDateAndTime);

                values.put("direccion", consultarDireccion());
                values.put("precio", String.valueOf(precioFinalFloat));

                // Inserta el nuevo producto en la tabla "piezas"
                long newRowId = db.insert("pedidos", null, values);

                // Cierra la base de datos
                db.close();

                if (newRowId != -1) {

                    SQLiteDatabase db3 = dbHelper.getWritableDatabase();

                    Cursor cursor3 = db3.rawQuery("SELECT id FROM pedidos WHERE id_usuario = ?;", new String[]{String.valueOf(MainActivity.userId)});

                    if (cursor3 != null && cursor3.getCount() > 0) {
                        cursor3.moveToLast();
                        idPedido = cursor3.getInt(0);

                        SQLiteDatabase db2 = dbHelper.getWritableDatabase();

                        Cursor cursor2 = db2.rawQuery("SELECT id_producto, cantidad_producto FROM carritos WHERE id_usuario = ?;", new String[]{String.valueOf(MainActivity.userId)});

                        if (cursor2 != null && cursor2.getCount() > 0) {
                            // Movemos el cursor a la primera fila
                            cursor2.moveToFirst();
                            ContentValues values2 = new ContentValues();
                            long newRowId2;
                            do {

                                int id = cursor2.getInt(0);
                                int cantidadProducto = cursor2.getInt(1);


                                values2.put("id_pedido", String.valueOf(idPedido));

                                values2.put("id_producto", String.valueOf(id));

                                values2.put("cantidad_producto", cantidadProducto);

                                newRowId2 = db2.insert("pedidos_a_piezas", null, values2);

                            } while (cursor2.moveToNext());



                            // Inserta el nuevo producto en la tabla "piezas"

                            if (newRowId2 != -1){

                                Toast.makeText(context, "Pedido realizado.", Toast.LENGTH_SHORT).show();
                                SQLiteDatabase db4 = dbHelper.getWritableDatabase();
                                // Realizar la consulta DELETE
                                db4.delete("carritos", "id_usuario = ?", new String[]{String.valueOf(MainActivity.userId)});

                                // Cerrar la base de datos
                                db4.close();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                LayoutInflater inflater = LayoutInflater.from(context);
                                View dialogView = inflater.inflate(R.layout.dialog_compra_realizada, null);
                                builder.setView(dialogView);

                                TextView infoCompra = dialogView.findViewById(R.id.textViewInfoCompra);
                                TextView numPedido = dialogView.findViewById(R.id.textViewNumeroPedido);
                                numPedido.setText("Nº de pedido: "+idPedido+".");

                                Button btnAceptar = dialogView.findViewById(R.id.btnAceptar);
                                Button btnVerPedidos = dialogView.findViewById(R.id.btnVerPedidos);

                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                                btnVerPedidos.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, VistaPorPedidos.class);
                                        startActivity(intent);
                                    }
                                });

                                btnAceptar.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, UserActivity.class);
                                        startActivity(intent);
                                    }
                                });


                            } else {

                                Toast.makeText(context, "Error al realizar el pedido.", Toast.LENGTH_SHORT).show();
                            }

                            // Cierra la base de datos




                        }

                        db2.close();
                        }

                    db3.close();

                    }








            }
        });
        drawerLayout = findViewById(R.id.drawer_layout);
        //Cosas necesarias par apillar el correo y el nombre del user
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView drawenombreTextView = headerView.findViewById(R.id.drawernombre);
        TextView drawercorreoTextView = headerView.findViewById(R.id.drawercorreo);

// Ahora puedes usar drawenombreTextView y drawercorreoTextView para establecer los textos

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        id_usuario = String.valueOf(MainActivity.userId);

        try {
            Log.e("drawer",String.valueOf(id_usuario));

            Cursor cursor = db.rawQuery("SELECT nombre, correo FROM usuarios WHERE id = ?", new String[]{id_usuario});            if (cursor.moveToFirst()) {
                String nombreuser = cursor.getString(0);
                String correo = cursor.getString(1);
                drawercorreoTextView.setText(correo);
                drawenombreTextView.setText(nombreuser );

                Log.e("drawer", "Nombre del usuario: " + nombreuser);
                Log.e("drawer", "Correo del usuario: " + correo);

            } else {
                Log.e("drawer", "Cursor vacío, no se encontró el usuario con ID: " + id_usuario);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("drawer", "Error en la consulta de la base de datos", e);
        } finally {
            db.close();
        }
        ImageButton drawerButton = findViewById(R.id.buttonAbrirDrawer);

        drawerButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));


    }

    public void onProfileClick(MenuItem menuItem) {
        Intent intent = new Intent(VistaPorCarrito.this, ActivityPerfil.class);
        intent.putExtra("USER_ID", String.valueOf(userId)); // Replace userId with the actual user ID variable
        startActivity(intent);
    }

    public void onFavoritesClick(MenuItem menuItem) {
        Intent intentfavoritos = new Intent(VistaPorCarrito.this, VistaPorFavoritos.class);
        startActivity(intentfavoritos);
    }

    public void onTiendaClick(MenuItem menuItem) {
        Intent intenttienda = new Intent(VistaPorCarrito.this, UserActivity.class);
        startActivity(intenttienda);
    }
    public void onCarritoClick(MenuItem menuItem) {
        Intent intentCarrito = new Intent(VistaPorCarrito.this, VistaPorCarrito.class);
        startActivity(intentCarrito);
    }
    public void onCerrarSesionClick(MenuItem menuItem) {
        Intent intentCerrarSesion = new Intent(VistaPorCarrito.this, MainActivity.class);
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

    public static String consultarDireccion() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT direccion FROM usuarios WHERE id=?;", new String[]{String.valueOf(MainActivity.userId)});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return String.valueOf(cursor.getInt(0));
        }
        cursor.close();
        db.close();
        return null;
    }


}
