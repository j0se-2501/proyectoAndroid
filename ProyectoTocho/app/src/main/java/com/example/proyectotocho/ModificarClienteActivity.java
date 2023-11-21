package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModificarClienteActivity extends AppCompatActivity {

    private EditText editTextClienteId;
    private EditText editTextNuevoCorreo;
    private EditText editTextNuevaContrasenna;
    private Button btnModificarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);

        editTextClienteId = findViewById(R.id.editTextID);
        editTextNuevoCorreo = findViewById(R.id.editTextCorreo);
        editTextNuevaContrasenna = findViewById(R.id.editTextContrasena);
        btnModificarCliente = findViewById(R.id.btnModificarCliente);

        btnModificarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarCliente();
            }
        });
    }

    private void modificarCliente() {
        // Obtén los datos para la modificación del producto
        String clienteIdStr = editTextClienteId.getText().toString();
        String nuevoCorreo = editTextNuevoCorreo.getText().toString();
        String nuevaContrasenna = editTextNuevaContrasenna.getText().toString();

        // Verifica si se ingresó el ID del producto
        if (clienteIdStr.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del usuario a modificar", Toast.LENGTH_SHORT).show();
            return;
        }

        int clienteId = Integer.parseInt(clienteIdStr);

        // Accede a la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Verifica si el producto con el ID dado existe
        String[] projection = {"id"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(clienteId)};
        Cursor cursor = db.query("usuarios", projection, selection, selectionArgs, null, null, null);

        if (cursor.getCount() > 0) {
            // El producto existe, procede con la modificación
            ContentValues values = new ContentValues();
            if (!nuevoCorreo.isEmpty()) values.put("correo", nuevoCorreo);
            if (!nuevaContrasenna.isEmpty()) values.put("contraseña", nuevaContrasenna);

            // Realiza la actualización del producto
            int numRowsUpdated = db.update("usuarios", values, "id=?", new String[]{String.valueOf(clienteId)});

            if (numRowsUpdated > 0) {
                Toast.makeText(this, "Usuario modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se pudo modificar el usuario. Verifique el ID.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // El cliente no existe
            Toast.makeText(this, "El usuario con el ID proporcionado no existe", Toast.LENGTH_SHORT).show();
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();
    }
}