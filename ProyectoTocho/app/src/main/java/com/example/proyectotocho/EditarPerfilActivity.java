package com.example.proyectotocho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText editTextName, editTextAddress, editTextPassword;
    private Button btnSave;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        userId = getIntent().getStringExtra("USER_ID");
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPassword=findViewById(R.id.editTextPassword);
        btnSave = findViewById(R.id.btnSave);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editTextName.getText().toString();
                String newAddress = editTextAddress.getText().toString();
                String newPassword=editTextPassword.getText().toString();
                updateUserData(userId, newName, newAddress, newPassword);
            }
        });
    }

    private void updateUserData(String userId, String newName, String newAddress, String newPassword) {
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (!newName.isEmpty()) values.put("nombre", newName);
        if (!newAddress.isEmpty()) values.put("direccion", newAddress);
        if (!newPassword.isEmpty()) values.put("contrasena", newPassword);

        int rowsAffected = db.update("usuarios", values, "id = ?", new String[]{userId});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
