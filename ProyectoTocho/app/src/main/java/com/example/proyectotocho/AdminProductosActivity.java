package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminProductosActivity extends AppCompatActivity {

    Intent intentProductos = getIntent();

    private Button agregarProductoButton;
    private Button modificarProductoButton;
    private Button eliminarProductoButton;

    private Button mostrarProductoButton;

    private Button salirButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_productos);

        Intent intentProductos = getIntent();

        agregarProductoButton = findViewById(R.id.Anadir);
        modificarProductoButton = findViewById(R.id.Modificar);
         eliminarProductoButton = findViewById(R.id.Eliminar);
        mostrarProductoButton = findViewById(R.id.mostrarProductos);
        salirButton = findViewById(R.id.salir);

        salirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cierra la actividad actual
                Intent intentSalir = new Intent(AdminProductosActivity.this, MainActivity.class);
                startActivity(intentSalir);
            }
        });

        eliminarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad para eliminar un producto
                Intent intentEliminarProducto = new Intent(AdminProductosActivity.this, EliminarProductoActivity.class);
                startActivity(intentEliminarProducto);
            }
        });

        mostrarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad para mostrar la lista de productos
                Intent intentMostrarProductos = new Intent(AdminProductosActivity.this, MostarrProductosActivity.class);
                startActivity(intentMostrarProductos);
            }
        });

        agregarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad para agregar un nuevo producto
                Intent intentAgregarProducto = new Intent(AdminProductosActivity.this, AgregarProductoActivity.class);
                startActivity(intentAgregarProducto);
            }
        });

        modificarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre la actividad para modificar un producto existente
                Intent intentModificarProducto = new Intent(AdminProductosActivity.this, ModificarProductoActivity.class);
                startActivity(intentModificarProducto);
            }
        });
    }
}
