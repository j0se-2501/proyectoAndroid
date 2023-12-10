package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    private Button botonProductos;
    private Button botonClientes;
    private Button botonTienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();

        botonProductos = findViewById(R.id.Productos);
        botonClientes = findViewById(R.id.Clientes);
        botonTienda = findViewById(R.id.Tienda);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        botonProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProductos = new Intent(AdminActivity.this, AdminProductosActivity.class);
                startActivity(intentProductos);
            }
        });

        botonClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClientes = new Intent(AdminActivity.this, AdminClientesActivity.class);
                startActivity(intentClientes);
            }
        });

        botonTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTienda = new Intent(AdminActivity.this, UserActivity.class);
                intentTienda.putExtra("USER_EMAIL", MainActivity.correoIntent);
                startActivity(intentTienda);
            }
        });
    }
}
