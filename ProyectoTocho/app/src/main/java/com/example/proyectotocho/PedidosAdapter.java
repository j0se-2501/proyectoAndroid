package com.example.proyectotocho;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {
    private List<Pedido> pedidos;
    private Context context;
    View view;

    public PedidosAdapter(Context context, List<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.elemento_lista_pedidos, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Pedido pedido = pedidos.get(position);

        holder.textViewId.setText(String.valueOf(pedido.getId()));
        holder.textViewFecha.setText(pedido.getFecha());
        holder.textViewPrecio.setText(String.valueOf(pedido.getPrecio()));
        holder.textViewDireccion.setText(pedido.getDireccion());

        holder.botonVerProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(context);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT p.nombre, pap.cantidad_producto, p.precio * pap.cantidad_producto AS precio_total " +
                        "FROM pedidos_a_piezas pap " +
                        "INNER JOIN piezas p ON pap.id_producto = p.id " +
                        "WHERE pap.id_pedido = ?;", new String[]{String.valueOf(pedido.getId())});
                // Crear un StringBuilder para construir el texto del dialog
                StringBuilder dialogText = new StringBuilder();

                // Iterar sobre el cursor y construir el texto del dialog
                if (cursor != null && cursor.moveToFirst()) {
                    double precioSumado=0;
                    do {

                        @SuppressLint("Range") String nombrePieza = cursor.getString(cursor.getColumnIndex("nombre"));
                        @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad_producto"));
                        @SuppressLint("Range") double precioTotal = cursor.getDouble(cursor.getColumnIndex("precio_total"));
                        precioSumado+=precioTotal;
                        dialogText.append(nombrePieza).append("\n - Cantidad: ").append(cantidad).append("\n - Precio total: ").append(precioTotal).append("€\n\n");
                    } while (cursor.moveToNext());
                    dialogText.append("Total: ").append(precioSumado).append("€");

                    cursor.close();
                }

                // Crear el AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Productos del pedido");
                builder.setMessage(dialogText.toString());

                // Añadir botón de aceptar
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cerrar el dialog
                        dialog.dismiss();
                    }
                });

                // Mostrar el AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {

        return pedidos.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewFecha;
        TextView textViewPrecio;
        TextView textViewDireccion;

        ImageButton botonVerProductos;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.textViewNumeroPedido);
            textViewFecha = itemView.findViewById(R.id.textViewFechaContenido);
            textViewPrecio = itemView.findViewById(R.id.textViewPrecioContenido);
            textViewDireccion = itemView.findViewById(R.id.textViewDireccionContenido);
            botonVerProductos = itemView.findViewById(R.id.btnVerProductos);
        }
    }

    public void actualizarLista(List<Pedido> nuevosPedidos) {
        pedidos = nuevosPedidos;
        notifyDataSetChanged();
    }

}
