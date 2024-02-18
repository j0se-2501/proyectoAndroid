package com.example.proyectotocho;

import static com.example.proyectotocho.MainActivity.userId;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

import com.example.proyectotocho.ActivityPerfil;
import com.example.proyectotocho.DbHelper;
import com.example.proyectotocho.R;
import com.example.proyectotocho.VistaPorCategoria;
import com.example.proyectotocho.VistaPorFavoritos;
import com.google.android.material.navigation.NavigationView;

public class UserActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private String id_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intentPerfil = new Intent(this, ActivityPerfil.class);
        Intent intent = new Intent(this, VistaPorCategoria.class);
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
        //Cosas necesarias par apillar el correo y el nombre del user
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView drawenombreTextView = headerView.findViewById(R.id.drawernombre);
        TextView drawercorreoTextView = headerView.findViewById(R.id.drawercorreo);

// Ahora puedes usar drawenombreTextView y drawercorreoTextView para establecer los textos

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        id_usuario = String.valueOf(MainActivity.userId);

        try {
            Log.e("drawer",String.valueOf(id_usuario));

            Cursor cursor = db.rawQuery("SELECT nombre, correo FROM usuarios WHERE id = ?", new String[]{id_usuario});            if (cursor.moveToFirst()) {
                String nombreuser = cursor.getString(0);
                String correo = cursor.getString(1);
                drawercorreoTextView.setText(correo);
                drawenombreTextView.setText(nombreuser);

                Log.e("drawer", "Nombre del usuario: " + nombreuser);
                Log.e("drawer", "Correo del usuario: " + correo);

            } else {
                Log.e("drawer", "Cursor vacío, no se encontró el usuario con ID: " + id_usuario);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("drawer", "Error en la consulta de la base de datos", e);
        } finally {
            db.close();
        }
        ImageButton drawerButton = findViewById(R.id.buttonAbrirDrawer);

        drawerButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));


    }

    public void onProfileClick(MenuItem menuItem) {
        Intent intent = new Intent(UserActivity.this, ActivityPerfil.class);
        intent.putExtra("USER_ID", String.valueOf(userId)); // Replace userId with the actual user ID variable
        startActivity(intent);
    }

    public void onFavoritesClick(MenuItem menuItem) {
        Intent intentfavoritos = new Intent(UserActivity.this, VistaPorFavoritos.class);
        startActivity(intentfavoritos);
    }

    public void onTiendaClick(MenuItem menuItem) {
        Intent intenttienda = new Intent(UserActivity.this, UserActivity.class);
        startActivity(intenttienda);
    }
    public void onCarritoClick(MenuItem menuItem) {
        Intent intentCarrito = new Intent(UserActivity.this, VistaPorCarrito.class);
        startActivity(intentCarrito);
    }
    public void onCerrarSesionClick(MenuItem menuItem) {
        Intent intentCerrarSesion = new Intent(UserActivity.this, MainActivity.class);
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