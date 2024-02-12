package com.example.proyectotocho;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intentPerfil = new Intent(this, ActivityPerfil.class);
        Intent intent = new Intent(this, VistaPorCategoria.class);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        // Obtener el correo del usuario que ha iniciado sesión
        String userId= getIntent().getStringExtra("USER_ID");
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);

        Log.e("uwu ha lleagdo el intent?",String.valueOf(userId));


        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        try {
            Log.e("uwu LLEGO AL TRY",String.valueOf(userId));

            Cursor cursor = db.rawQuery("SELECT nombre FROM usuarios WHERE id = ?", new String[]{userId});
            if (cursor.moveToFirst()) {
                String nombreuser = cursor.getString(0);
                userEmailTextView.setText("Bienvenido, " + nombreuser + ".");
                Log.e("uwu", "Nombre del usuario: " + nombreuser);
            } else {
                Log.e("uwu", "Cursor vacío, no se encontró el usuario con ID: " + userId);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("owo", "Error en la consulta de la base de datos", e);
        } finally {
            db.close();
        }


        Button buttonFavoritos = findViewById(R.id.buttonFavoritos);
        // Configurar OnClickListener para cada botón


        buttonFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFavoritos = new Intent(UserActivity.this, VistaPorFavoritos.class);
                startActivity(intentFavoritos);

            }
        });



                // Mostrar el correo en un TextView




        Button buttonPerfil = findViewById(R.id.buttonPerfil);
        // Configurar OnClickListener para cada botón


        buttonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPerfil = new Intent(UserActivity.this, ActivityPerfil.class);
                intentPerfil.putExtra("USER_ID", userId); // Pasa el id
                startActivity(intentPerfil);

            }
        });

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

        // Obtener una referencia al DrawerLayout
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        // Obtener una referencia al botón que abrirá el navigation drawer
        Button drawerButton = findViewById(R.id.buttonAbrirDrawer);

        // Configurar OnClickListener para el botón que abrirá el drawer
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir el navigation drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
