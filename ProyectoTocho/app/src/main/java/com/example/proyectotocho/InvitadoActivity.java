package com.example.proyectotocho;

import static com.example.proyectotocho.MainActivity.userId;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class InvitadoActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = new Intent(this, VistaPorCategoriaInvitado.class);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));

        String userId = getIntent().getStringExtra("USER_ID");


        /*DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT nombre FROM usuarios WHERE id = ?", new String[]{userId});

            if (cursor.moveToFirst()) {
                String nombreuser = cursor.getString(0);
                userEmailTextView.setText("Bienvenido, " + nombreuser + ".");
            } else {
                Log.e("uwu", "Cursor vacío, no se encontró el usuario con ID: " + userId);
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("owo", "Error en la consulta de la base de datos", e);
        } finally {
            db.close();
        }*/












        ImageButton motorButton = findViewById(R.id.imageButton);

        motorButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "motor");
            startActivity(intent);
        });

        ImageButton transmisionButton = findViewById(R.id.imageButton2);

        transmisionButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "transmision");
            startActivity(intent);
        });

        ImageButton sobrealimentacionButton = findViewById(R.id.imageButton3);

        sobrealimentacionButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "sobrealimentacion");
            startActivity(intent);
        });

        ImageButton neumaticosButton = findViewById(R.id.imageButton4);

        neumaticosButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "neumaticos");
            startActivity(intent);
        });

        ImageButton llantasButton = findViewById(R.id.imageButton5);

        llantasButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "llantas");
            startActivity(intent);
        });

        ImageButton suspensionButton = findViewById(R.id.imageButton6);

        suspensionButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "suspension");
            startActivity(intent);
        });

        ImageButton frenosButton = findViewById(R.id.imageButton7);

        frenosButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "frenos");
            startActivity(intent);
        });

        ImageButton carroceriaButton = findViewById(R.id.imageButton8);

        carroceriaButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "carroceria");
            startActivity(intent);
        });

        ImageButton electronicaButton = findViewById(R.id.imageButton9);

        electronicaButton.setOnClickListener(view -> {
            intent.putExtra("CATEGORIA", "electronica");
            startActivity(intent);
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        ImageButton drawerButton = findViewById(R.id.buttonAbrirDrawer);

        drawerButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }


    public void onProfileClick(MenuItem menuItem) {

    }

    public void onFavoritesClick(MenuItem menuItem) {

    }

    public void onTiendaClick(MenuItem menuItem) {

    }
    public void onCarritoClick(MenuItem menuItem) {

    }
    public void onCerrarSesionClick(MenuItem menuItem) {
        Intent intentCerrarSesion = new Intent(InvitadoActivity.this, MainActivity.class);
        startActivity(intentCerrarSesion);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
