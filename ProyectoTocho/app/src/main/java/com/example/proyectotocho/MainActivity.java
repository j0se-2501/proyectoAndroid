package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String correoIntent;
    private Button registrarboton;
    private Button login;
    private Button invitadoboton;

    public static int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText correologin = findViewById(R.id.emaillogin);
        EditText contrasenalogin = findViewById(R.id.contrasenalogin);
        registrarboton = findViewById(R.id.registarselogin);
        login = findViewById(R.id.entrar);
        invitadoboton=findViewById(R.id.invitado);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));


        registrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        invitadoboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InvitadoActivity.class);

               intent.putExtra("USER_EMAIL", "");
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = correologin.getText().toString();
                String contrasena = contrasenalogin.getText().toString();
                correoIntent = correo;

                if (correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    // Verificar si el usuario es admin
                    if (esAdmin(correo, contrasena)) {
                        // Si el usuario es admin, iniciar AdminActivity
                        Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(adminIntent);
                        userId=1;
                    } else {
                        // Si el usuario no es admin, validar en la base de datos
                        userId = validarUsuario(correo, contrasena);
                        Log.e("uwu", String.valueOf(userId));
                        if (userId != -1) {
                            // Si las credenciales son correctas, iniciar UserActivity
                            Log.e("uwu despeus de validar",String.valueOf(userId));
                            Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
                            userIntent.putExtra("USER_ID", String.valueOf(userId));

                            startActivity(userIntent);
                        } else {
                            // Si las credenciales no son correctas, mostrar un mensaje de error
                            Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });



    }
    private int validarUsuario(String correo, String contrasena) {
        // Obtener una instancia de la base de datos
        SQLiteDatabase db = DbHelper.getInstance(MainActivity.this).getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT id FROM usuarios WHERE correo=? AND contrasena=?", new String[]{correo, contrasena});

        int userId = -1; // Valor por defecto en caso de no encontrar el usuario

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0); // Obtener la ID del usuario
        }

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();
        db.close();
        Log.e("uwu",String.valueOf(userId));
        return userId;
    }

    private boolean esAdmin(String correo, String contrasena) {
        String correoAdmin = "admin@admin.com";
        String contrasenaAdmin = "admin";

        return correo.equals(correoAdmin) && contrasena.equals(contrasenaAdmin);
    }
}
