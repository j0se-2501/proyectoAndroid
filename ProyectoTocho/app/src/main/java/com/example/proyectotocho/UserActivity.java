package com.example.proyectotocho;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = new Intent(this, VistaPorCategoria.class);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        // Obtener el correo del usuario que ha iniciado sesión
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Mostrar el correo en un TextView
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);
        userEmailTextView.setText("Correo: " + userEmail);

        // Configurar OnClickListener para cada botón
        Button motorButton = findViewById(R.id.motorButton);
        motorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "motor");
                startActivity(intent);
            }
        });

        Button transmisionButton = findViewById(R.id.transmisionButton);
        transmisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "transmision");
                startActivity(intent);
            }
        });

        Button sobrealimentacionButton = findViewById(R.id.sobrealimentacionButton);
        sobrealimentacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "sobrealimentacion");
                startActivity(intent);
            }
        });

        Button neumaticosButton = findViewById(R.id.neumaticosButton);
        neumaticosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "neumaticos");
                startActivity(intent);
            }
        });

        Button llantasButton = findViewById(R.id.llantasButton);
        llantasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "llantas");
                startActivity(intent);
            }
        });

        Button suspensionButton = findViewById(R.id.suspensionButton);
        suspensionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "suspension");
                startActivity(intent);
            }
        });

        Button frenosButton = findViewById(R.id.frenosButton);
        frenosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "frenos");
                startActivity(intent);
            }
        });

        Button carroceriaButton = findViewById(R.id.carroceriaButton);
        carroceriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "carroceria");
                startActivity(intent);
            }
        });

        Button electronicaButton = findViewById(R.id.electronicaButton);
        electronicaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "electronica");
                startActivity(intent);
            }
        });
    }


}
