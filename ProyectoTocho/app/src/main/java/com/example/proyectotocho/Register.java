package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    public Button botonregistrar;
    public  Button botonloguear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        botonregistrar = findViewById(R.id.registrarse);
        botonloguear = findViewById(R.id.loguearseregister);
        EditText correoregister = findViewById(R.id.email2);
        EditText contraseñaregister = findViewById(R.id.contraseña2);

        //Creamos la base de datos
        DbHelper dbhelper = new DbHelper(Register.this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        if (db!=null){
            Toast.makeText(Register.this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Register.this, "ERROR EN BASE DE DATOS", Toast.LENGTH_LONG).show();
        }
    //Al hacer click en el boton insertamos los datos
        botonregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         String correo = correoregister.getText().toString();
         String contraseña = contraseñaregister.getText().toString();
         if (correo.isEmpty() || contraseña.isEmpty()){
             Toast.makeText(Register.this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();

         }else {
             DbHelper dbHelper = new DbHelper(Register.this);
             SQLiteDatabase db = dbHelper.getWritableDatabase();
             ContentValues valores= new ContentValues();
             valores.put("correo",correo);
             valores.put("contraseña",contraseña);

             long idInsercion = db.insert("usuarios", null, valores);

             correoregister.setText("");
             contraseñaregister.setText("");

             if (idInsercion != -1) {
                 Toast.makeText(Register.this, "DATOS INSERTADOS", Toast.LENGTH_LONG).show();
                 Intent intent = new Intent(Register.this, MainActivity.class);
                 startActivity(intent);
             } else {
                 Toast.makeText(Register.this, "ERROR. DATOS NO INSERTADOS", Toast.LENGTH_LONG).show();
             }

         }
            }
        });

        //Aqui hacemos que el boton loguearse nos lleve a la activity de logueo
        botonloguear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);

            }
        });




    }
}