package com.example.proyectotocho;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

public class ProductoAdapterCarrito extends RecyclerView.Adapter<ProductoAdapterCarrito.ViewHolder> {
    private List<Producto> productos;
    private Context context;
    View view;

    int cantidad;



    public ProductoAdapterCarrito(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.elemento_lista_carrito, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Producto producto = productos.get(position);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT cantidad_producto FROM carritos WHERE id_usuario = ? AND id_producto = ?", new String[]{String.valueOf(MainActivity.userId),String.valueOf(producto.getId())});

        holder.textViewNombre.setText(producto.getNombre());
        if (cursor!=null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            cantidad= cursor.getInt(0);
            float precio=Float.parseFloat(producto.getPrecio());
            DecimalFormat d = new DecimalFormat("#.##");

            holder.editTextUnidades.setText(String.valueOf(cantidad));
            holder.textViewPrecioTotal.setText(String.valueOf(d.format(precio*cantidad)));
            holder.textViewPrecioIndividual.setText(producto.getPrecio());
            // Cargar la imagen desde el recurso Drawable
            int resourceId = context.getResources().getIdentifier(producto.getImagenUrl(), "drawable", context.getPackageName());

            holder.botonBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbHelper dbHelper = new DbHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String whereClause = "id_usuario = ? AND id_producto = ?";
                    String[] whereArgs = { String.valueOf(MainActivity.userId), String.valueOf(producto.getId()) };

                    // Elimina el producto de la tabla "favoritos"
                    int numRowsDeleted = db.delete("carritos", whereClause, whereArgs);

                    if (numRowsDeleted > 0) {
                        Toast.makeText(context, "Eliminado del carrito.", Toast.LENGTH_SHORT).show();

                        Iterator<Producto> iterator = productos.iterator();
                        int index = 0;
                        while (iterator.hasNext()) {
                            Producto producto = iterator.next();
                            if (index == position) {
                                iterator.remove();
                                notifyItemRemoved(position);
                                VistaPorCarrito.actualizarPrecioTotal();
                                break;
                            }
                            index++;
                        }

                        notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
                    }

                    db.close();
                }


            });;

            holder.editTextUnidades.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // No necesitamos hacer nada aquí
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // No necesitamos hacer nada aquí
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // Comprueba si el texto ha cambiado desde el original
                    if ((!holder.editTextUnidades.getText().toString().equals(String.valueOf(cantidad)))&&!holder.editTextUnidades.getText().toString().isEmpty()) {

                        if((Integer.parseInt(holder.editTextUnidades.getText().toString())<100)&&(Integer.parseInt(holder.editTextUnidades.getText().toString())>0)){
                            // Accede a la base de datos
                            DbHelper dbHelper = new DbHelper(context);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();



                                ContentValues values = new ContentValues();
                                values.put("cantidad_producto", holder.editTextUnidades.getText().toString());



                                int numRowsUpdated = db.update("carritos", values, "id_usuario=? AND id_producto=?", new String[]{String.valueOf(MainActivity.userId), String.valueOf(producto.getId())});
                            if (numRowsUpdated <1) {
                                Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show();
                            }
                            db.close();
                            cantidad=Integer.parseInt(holder.editTextUnidades.getText().toString());
                            holder.textViewPrecioTotal.setText(String.valueOf(d.format(precio*cantidad)));
                            VistaPorCarrito.actualizarPrecioTotal();
                        } else {
                            cantidad=1;
                            DbHelper dbHelper = new DbHelper(context);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();



                            ContentValues values = new ContentValues();
                            values.put("cantidad_producto", String.valueOf(cantidad));



                            int numRowsUpdated = db.update("carritos", values, "id_usuario=? AND id_producto=?", new String[]{String.valueOf(MainActivity.userId), String.valueOf(producto.getId())});
                            cursor.close();
                            db.close();
                            holder.editTextUnidades.setText("1");
                            holder.textViewPrecioTotal.setText(String.valueOf(d.format(precio*cantidad)));
                            VistaPorCarrito.actualizarPrecioTotal();
                        }

                    }
                }
            });


            Glide.with(context)
                    .load(resourceId)
                    .error(R.drawable.logo)
                    .into(holder.imageViewProducto);

        }


    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        TextView textViewPrecioTotal;
        TextView textViewPrecioIndividual;
        EditText editTextUnidades;
        ImageView imageViewProducto;

        ImageButton botonBorrar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewPrecioTotal = itemView.findViewById(R.id.textViewPrecioTotal);
            textViewPrecioIndividual = itemView.findViewById(R.id.textViewPrecioIndividual);
            imageViewProducto = itemView.findViewById(R.id.imageViewProducto);
            editTextUnidades = itemView.findViewById(R.id.editTextUnidades);
            botonBorrar = itemView.findViewById(R.id.imageButtonPapelera);
        }
    }

    public void actualizarLista(List<Producto> nuevosProductos) {
        productos = nuevosProductos;
        notifyDataSetChanged();
    }

}
