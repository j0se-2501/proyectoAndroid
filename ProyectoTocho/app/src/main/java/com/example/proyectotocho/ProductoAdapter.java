package com.example.proyectotocho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    private List<Producto> productos;
    private Context context;
    View view;

    public ProductoAdapter(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.elemento_lista, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final boolean[] fav = {false};
        Producto producto = productos.get(position);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM favoritos WHERE id_usuario = ? AND id_producto = ?", new String[]{String.valueOf(MainActivity.userId),String.valueOf(producto.getId())});


        holder.textViewNombre.setText(producto.getNombre());
        holder.textViewDescripcion.setText(producto.getDescripcion());
        holder.textViewPrecio.setText(producto.getPrecio());
        ImageButton botonFav = view.findViewById(R.id.botonFavNo);
        ImageButton botonComprar = view.findViewById(R.id.btnComprar);
        if (cursor!=null && cursor.getCount() > 0) {
            botonFav.setImageResource(R.drawable.fav_si);
            fav[0] =true;
        } else fav[0] =false;
        cursor.close();
        db.close();
            // Cargar la imagen desde el recurso Drawable
        int resourceId = context.getResources().getIdentifier(producto.getImagenUrl(), "drawable", context.getPackageName());
        botonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fav[0]) {
                    botonFav.setImageResource(R.drawable.fav_si);
                    fav[0] =true;

                    DbHelper dbHelper = new DbHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    // Crea un objeto ContentValues para insertar los datos en la tabla "favoritos"
                    ContentValues values = new ContentValues();
                    values.put("id_usuario", MainActivity.userId);
                    values.put("id_producto", producto.getId());
                    // Inserta el nuevo producto en la tabla "favoritos"
                    long newRowId = db.insert("favoritos", null, values);

                    // Cierra la base de datos
                    db.close();

                    if (newRowId != -1) {
                        Toast.makeText(context, "Añadido a favoritos.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    botonFav.setImageResource(R.drawable.fav_no);
                    fav[0] =false;

                    DbHelper dbHelper = new DbHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    // Define la condición de eliminación
                    String whereClause = "id_usuario = ? AND id_producto = ?";
                    String[] whereArgs = { String.valueOf(MainActivity.userId), String.valueOf(producto.getId()) };

                    // Elimina el producto de la tabla "favoritos"
                    int numRowsDeleted = db.delete("favoritos", whereClause, whereArgs);

                    db.close();

                    if (numRowsDeleted > 0) {
                        Toast.makeText(context, "Eliminado de favoritos.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
                    }

                };
            }
        });

        botonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_cantidad, null);
                builder.setView(dialogView);

                final EditText etCantidad = dialogView.findViewById(R.id.etCantidad);
                Button btnAgregar = dialogView.findViewById(R.id.btnAgregar);
                Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cantidadStr = etCantidad.getText().toString().trim();
                        if (!cantidadStr.isEmpty()) {
                            int cantidad = Integer.parseInt(cantidadStr);
                            if (cantidad >= 1 && cantidad <= 99) {
                                DbHelper dbHelper = new DbHelper(context);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                // Crea un objeto ContentValues para insertar los datos en la tabla "carritos"
                                ContentValues values = new ContentValues();
                                values.put("id_usuario", MainActivity.userId);
                                values.put("id_producto", producto.getId());
                                values.put("cantidad_producto", cantidad);
                                // Inserta el nuevo producto en la tabla "carritos"
                                long newRowId = db.insert("carritos", null, values);

                                // Cierra la base de datos
                                db.close();



                                if (newRowId != -1) {
                                    alertDialog.dismiss();
                                    Toast.makeText(context, "Añadido al carrito.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Ya se encuentra en el carrito.", Toast.LENGTH_SHORT).show();
                                }
                                Log.d("MainActivity", "Cantidad seleccionada: " + cantidad);
                            } else {
                                etCantidad.setError("Ingrese una cantidad entre 1 y 99");
                            }
                        } else {
                            etCantidad.setError("Ingrese una cantidad");
                        }
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        Glide.with(context)
                .load(resourceId)
                .error(R.drawable.logo)
                .into(holder.imageViewProducto);

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        TextView textViewDescripcion;
        TextView textViewPrecio;
        ImageView imageViewProducto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewPrecio = itemView.findViewById(R.id.textViewPrecio);
            imageViewProducto = itemView.findViewById(R.id.imageViewProducto);
        }
    }

    public void actualizarLista(List<Producto> nuevosProductos) {
        productos = nuevosProductos;
        notifyDataSetChanged();
    }

}
