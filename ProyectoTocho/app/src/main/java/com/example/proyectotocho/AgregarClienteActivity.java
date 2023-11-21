package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarClienteActivity extends AppCompatActivity {

    private EditText editTextCorreo;
    private EditText editTextContrasenna;

    private Button btnAgregarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasenna = findViewById(R.id.editTextContrasena);
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);

        btnAgregarCliente.setOnClickListener(v -> agregarCliente());

    }

    private void agregarCliente() {
        // Obtén los datos del nuevo cliente
        String correo = editTextCorreo.getText().toString();
        String contrasenna = editTextContrasenna.getText().toString();

        // Accede a la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Crea un objeto ContentValues para insertar los datos en la tabla "piezas"
        ContentValues values = new ContentValues();
        values.put("correo", correo);
        values.put("contraseña", contrasenna);
        // Inserta el nuevo producto en la tabla "piezas"
        long newRowId = db.insert("usuarios", null, values);

        // Cierra la base de datos
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
        }
    }
}