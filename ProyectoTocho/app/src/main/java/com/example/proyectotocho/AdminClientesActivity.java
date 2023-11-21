package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
    }
}