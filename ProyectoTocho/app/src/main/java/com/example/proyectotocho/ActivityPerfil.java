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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.proyectotocho.R;

public class ActivityPerfil extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewPerfil;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        imageViewPerfil = findViewById(R.id.imageViewPerfil);
        // Dentro de onCreate en la clase UserActivity
        String userId = getIntent().getStringExtra("USER_ID");
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        TextView userNameTextView = findViewById(R.id.userEmailTextView);
        TextView userAddressTextView = findViewById(R.id.userEmailTextView2); // TextView para la dirección

        try {
            Log.e("uwu LLEGO AL TRY",String.valueOf(userId));

            Cursor cursor = db.rawQuery("SELECT nombre, direccion FROM usuarios WHERE id = ?", new String[]{userId});            if (cursor.moveToFirst()) {
                String nombreuser = cursor.getString(0);
                String direccion = cursor.getString(1);
                userAddressTextView.setText("Direccion: "+ direccion);
                userNameTextView.setText("Bienvenido, " + nombreuser + ".");
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

// Mostrar el nombre de usuario en algún TextView


        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzar la galería para seleccionar una imagen
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        Button btnEditProfile = findViewById(R.id.button); // Asegúrate de tener este botón en tu layout
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityPerfil.this, EditarPerfilActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        ImageButton drawerButton = findViewById(R.id.buttonAbrirDrawer);

        drawerButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));


    }

    public void onProfileClick(MenuItem menuItem) {
        Intent intent = new Intent(ActivityPerfil.this, ActivityPerfil.class);
        intent.putExtra("USER_ID", String.valueOf(userId)); // Replace userId with the actual user ID variable
        startActivity(intent);
    }

    public void onFavoritesClick(MenuItem menuItem) {
        Intent intentfavoritos = new Intent(ActivityPerfil.this, VistaPorFavoritos.class);
        startActivity(intentfavoritos);
    }

    public void onTiendaClick(MenuItem menuItem) {
        Intent intenttienda = new Intent(ActivityPerfil.this, UserActivity.class);
        startActivity(intenttienda);
    }
    public void onCarritoClick(MenuItem menuItem) {
        Intent intentCarrito = new Intent(ActivityPerfil.this, VistaPorCarrito.class);
        startActivity(intentCarrito);
    }
    public void onCerrarSesionClick(MenuItem menuItem) {
        Intent intentCerrarSesion = new Intent(ActivityPerfil.this, MainActivity.class);
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







    // Este método se llama cuando se completa la selección de la imagen desde la galería
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtener la URI de la imagen seleccionada
            // Puedes cargar y mostrar la imagen utilizando una biblioteca como Glide o Picasso
            imageViewPerfil.setImageURI(data.getData());
        }
    }




}
