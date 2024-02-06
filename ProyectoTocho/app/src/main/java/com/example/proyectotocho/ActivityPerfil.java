package com.example.proyectotocho;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectotocho.R;

public class ActivityPerfil extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewPerfil;

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

        try {
            Log.e("uwu LLEGO AL TRY",String.valueOf(userId));

            Cursor cursor = db.rawQuery("SELECT nombre FROM usuarios WHERE id = ?", new String[]{userId});
            if (cursor.moveToFirst()) {
                String nombreuser = cursor.getString(0);
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
