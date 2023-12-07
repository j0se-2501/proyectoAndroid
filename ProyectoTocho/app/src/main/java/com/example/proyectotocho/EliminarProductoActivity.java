
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

public class EliminarProductoActivity extends AppCompatActivity {
    private EditText editTextProductoId;
    private Button btnEliminarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_producto);

        editTextProductoId = findViewById(R.id.editTextProductoId);
        btnEliminarProducto = findViewById(R.id.btnEliminarProducto);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProducto();
            }
        });
    }

    private void eliminarProducto() {
        // Obtén el ID del producto a eliminar
        String productoIdStr = editTextProductoId.getText().toString();

        if (productoIdStr.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del producto a eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        int productoId = Integer.parseInt(productoIdStr);

        // Accede a la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Define la condición de eliminación
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(productoId) };

        // Elimina el producto de la tabla "piezas"
        int numRowsDeleted = db.delete("piezas", whereClause, whereArgs);

        db.close();

        if (numRowsDeleted > 0) {
            Toast.makeText(this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo eliminar el producto. Verifique el ID.", Toast.LENGTH_SHORT).show();
        }
    }
}
