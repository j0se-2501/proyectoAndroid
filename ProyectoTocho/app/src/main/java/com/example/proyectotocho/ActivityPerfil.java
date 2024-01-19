package com.example.proyectotocho;

import android.content.Intent;
import android.os.Bundle;
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
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);




        // Dividir el correo electrónico en dos partes: nombreDeUsuario y dominio
        String[] parts = userEmail.split("@");

        // Obtener solo la parte antes de '@'
        String nombreDeUsuario = parts[0];

        // Mostrar el correo en un TextView

        userEmailTextView.setText("Bienvenido, " + nombreDeUsuario + ".");
        // Configurar OnClickListener para cambiar la imagen al hacer clic en ella
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
