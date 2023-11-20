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
        registrarboton= findViewById(R.id.registarselogin);
        login= findViewById(R.id.entrar);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));

        //Aqui hacemos que el boton de registrarse nos abra la otra activity
        registrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);

            }
        });

        //Aqui añadimos el listener de el boton de login y que nos valide si el usuario existe
login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String correo = correologin.getText().toString();
        String contraseña = contraseñalogin.getText().toString();
        if (correo.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
        }else {
            //Realizamos una consulta a la base de datos
            DbHelper dbHelper = new DbHelper(MainActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String selection = "correo = ? AND contraseña = ?";
            String[] selectionArgs = { correo, contraseña };
            Cursor cursor = db.query("usuarios", null, selection, selectionArgs, null, null, null);

            int rowCount = cursor.getCount();

            cursor.close();
            db.close();
            if (rowCount <= 0) {
                //En el caso de que el usuario introducido no exista se ejecutara esta linea

                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext(), "Sesion iniciada", Toast.LENGTH_SHORT).show();
                if(correo.equals("admin@admin.com" )  && contraseña.equals("admin")){

                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);

                    startActivity(intent);


                }
            }
            String validacionAdmin="correo= admin@admin.com AND contraseña= admin";
            Cursor AdminValidar = db.query("usuarios", null, validacionAdmin, selectionArgs, null, null, null);
            int Count = AdminValidar.getCount();

            if (Count <= 0) {


            }else{
                Toast.makeText(getApplicationContext(), "Sesion iniciada como admin", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);

                startActivity(intent);



            }
        }
        }
});


    }
}