package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EliminarClienteActivity extends AppCompatActivity {

    private EditText editTextUsuarioId;
    private Button btnEliminarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cliente);

        editTextUsuarioId = findViewById(R.id.editTextClienteId);
        btnEliminarUsuario = findViewById(R.id.btnEliminarCliente);

        btnEliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUsuario();
            }
        });
    }

    private void eliminarUsuario() {
        // Obtén el ID del usuario a eliminar
        String usuarioIdStr = editTextUsuarioId.getText().toString();

        if (usuarioIdStr.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del usuario a eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        int usuarioId = Integer.parseInt(usuarioIdStr);

        // Accede a la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Define la condición de eliminación
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(usuarioId) };

        // Elimina el usuario de la tabla "usuarios"
        int numRowsDeleted = db.delete("usuarios", whereClause, whereArgs);

        db.close();

        if (numRowsDeleted > 0) {
            Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo eliminar el usuario. Verifique el ID.", Toast.LENGTH_SHORT).show();
        }
    }
}