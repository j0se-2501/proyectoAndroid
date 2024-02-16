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

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.textViewNumeroPedido);
            textViewFecha = itemView.findViewById(R.id.textViewFechaContenido);
            textViewPrecio = itemView.findViewById(R.id.textViewPrecioContenido);
            textViewDireccion = itemView.findViewById(R.id.textViewDireccionContenido);
        }
    }

    public void actualizarLista(List<Pedido> nuevosPedidos) {
        pedidos = nuevosPedidos;
        notifyDataSetChanged();
    }

}
