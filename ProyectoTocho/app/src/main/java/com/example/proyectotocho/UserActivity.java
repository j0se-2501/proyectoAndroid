package com.example.proyectotocho;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Obtener el correo del usuario que ha iniciado sesión
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Mostrar el correo en un TextView
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);
        userEmailTextView.setText("Correo: " + userEmail);


    }


}
