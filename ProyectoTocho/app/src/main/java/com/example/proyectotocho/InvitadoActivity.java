package com.example.proyectotocho;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class InvitadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = new Intent(this, VistaPorCategoriaInvitado.class);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        // Obtener el correo del usuario que ha iniciado sesión
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);




        // Dividir el correo electrónico en dos partes: nombreDeUsuario y dominio
        String[] parts = userEmail.split("@");

        // Obtener solo la parte antes de '@'
        String nombreDeUsuario = parts[0];

        // Mostrar el correo en un TextView

        userEmailTextView.setText("Bienvenido, invitado.");

        // Configurar OnClickListener para cada botón
        ImageButton motorButton = findViewById(R.id.imageButton);
        motorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("CATEGORIA", "motor");
                startActivity(intent);
            }
        });

        ImageButton transmisionButton = findViewById(R.id.imageButton2);
        transmisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("CATEGORIA", "transmision");
                startActivity(intent);
            }
        });

        ImageButton sobrealimentacionButton = findViewById(R.id.imageButton3);
        sobrealimentacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("CATEGORIA", "sobrealimentacion");
                startActivity(intent);
            }
        });

        ImageButton neumaticosButton = findViewById(R.id.imageButton4);
        neumaticosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("CATEGORIA", "neumaticos");
                startActivity(intent);
            }
        });

        ImageButton llantasButton = findViewById(R.id.imageButton5);
        llantasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("CATEGORIA", "llantas");
                startActivity(intent);
            }
        });

        ImageButton suspensionButton = findViewById(R.id.imageButton6);
        suspensionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("CATEGORIA", "suspension");
                startActivity(intent);
            }
        });

        ImageButton frenosButton = findViewById(R.id.imageButton7);
        frenosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("CATEGORIA", "frenos");
                startActivity(intent);
            }
        });

        ImageButton carroceriaButton = findViewById(R.id.imageButton8);
        carroceriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                intent.putExtra("CATEGORIA", "carroceria");
                startActivity(intent);
            }
        });

        ImageButton electronicaButton = findViewById(R.id.imageButton9);
        electronicaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("CATEGORIA", "electronica");
                startActivity(intent);
            }
        });
    }


}
