package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InvitadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado);
        Intent intent = new Intent(this, VistaPorCategoria.class);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));


        // Configurar OnClickListener para cada botón
        Button motorButton = findViewById(R.id.motorButtonInv);
        motorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invitadoIntent = new Intent(InvitadoActivity.this, VistaPorCategoriaInvitado.class);
                invitadoIntent.putExtra("CATEGORIA", "motor");
                startActivity(invitadoIntent);
            }
        });


        Button transmisionButton = findViewById(R.id.transmisionButtonInv);
        transmisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "transmision");
                startActivity(intent);
            }
        });

        Button sobrealimentacionButton = findViewById(R.id.sobrealimentacionButtonInv);
        sobrealimentacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "sobrealimentacion");
                startActivity(intent);
            }
        });

        Button neumaticosButton = findViewById(R.id.neumaticosButtonInv);
        neumaticosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "neumaticos");
                startActivity(intent);
            }
        });

        Button llantasButton = findViewById(R.id.llantasButtonInv);
        llantasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "llantas");
                startActivity(intent);
            }
        });

        Button suspensionButton = findViewById(R.id.suspensionButtonInv);
        suspensionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "suspension");
                startActivity(intent);
            }
        });

        Button frenosButton = findViewById(R.id.frenosButtonInv);
        frenosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "frenos");
                startActivity(intent);
            }
        });

        Button carroceriaButton = findViewById(R.id.carroceriaButtonInv);
        carroceriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "carroceria");
                startActivity(intent);
            }
        });

        Button electronicaButton = findViewById(R.id.electronicaButtonInv);
        electronicaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CATEGORIA", "electronica");
                startActivity(intent);
            }
        });
    }
}