package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button registrarboton;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText correologin = findViewById(R.id.emaillogin);
        EditText contraseñalogin = findViewById(R.id.contraseñalogin);
        registrarboton = findViewById(R.id.registarselogin);
        login = findViewById(R.id.entrar);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));

        registrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = correologin.getText().toString();
                String contraseña = contraseñalogin.getText().toString();

                if (correo.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    DbHelper dbHelper = new DbHelper(MainActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Verificar si el usuario no es admin
                    if (!esAdmin(correo, contraseña, db)) {
                        // Si el usuario no es admin, iniciar UserActivity
                        Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
                        userIntent.putExtra("USER_EMAIL", correo);
                        startActivity(userIntent);
                    } else {
                        // Si el usuario es admin, iniciar AdminActivity
                        Intent UserIntent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(UserIntent);
                    }

                    db.close();
                }
            }
        });
    }

    private boolean esAdmin(String correo, String contraseña, SQLiteDatabase db) {
        String validacionAdmin = "correo=? AND contraseña=?";
        String[] selectionArgs = { "admin@admin.com", "admin" };

        Cursor adminCursor = db.query("usuarios", null, validacionAdmin, selectionArgs, null, null, null);
        int count = adminCursor.getCount();
        adminCursor.close();

        return count > 0;
    }
}
