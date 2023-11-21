package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminClientesActivity extends AppCompatActivity {

    //comentario para actualizar

    Intent intentClientes = getIntent();

    private Button agregarClienteButton;
    private Button modificarClienteButton;
    private Button eliminarClienteButton;

    private Button mostrarClienteButton;

    private Button salirButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_clientes);


        Intent intentClientes=getIntent();

        agregarClienteButton = findViewById(R.id.Anadir);
        modificarClienteButton = findViewById(R.id.Modificar);
        eliminarClienteButton = findViewById(R.id.Eliminar);
        mostrarClienteButton = findViewById(R.id.mostrarCliente);
        salirButton = findViewById(R.id.salir);

        salirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cierra la actividad actual
                Intent intentSalir = new Intent(AdminClientesActivity.this, MainActivity.class);
                startActivity(intentSalir);
            }
        });

        eliminarClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad para eliminar un producto
                Intent intentEliminarProducto = new Intent(AdminClientesActivity.this, EliminarClienteActivity.class);
                startActivity(intentEliminarProducto);
            }
        });

        mostrarClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad para mostrar la lista de productos
                Intent intentMostrarProductos = new Intent(AdminClientesActivity.this, MostarrClienteActivity.class);
                startActivity(intentMostrarProductos);
            }
        });

        agregarClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad para agregar un nuevo producto
                Intent intentAgregarProducto = new Intent(AdminClientesActivity.this, AgregarClienteActivity.class);
                startActivity(intentAgregarProducto);
            }
        });

        modificarClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad para modificar un producto existente
                Intent intentModificarProducto = new Intent(AdminClientesActivity.this, ModificarClienteActivity.class);
                startActivity(intentModificarProducto);
            }
        });
    }
}