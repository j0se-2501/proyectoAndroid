// ModificarProductoActivity.java

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

public class ModificarProductoActivity extends AppCompatActivity {
    private EditText editTextProductoId;
    private EditText editTextNuevoNombre;
    private EditText editTextNuevaDescripcion;
    private EditText editTextNuevoPrecio;
    private EditText editTextNuevoStock;
    private EditText editTextIdCategoria;
    private EditText editTextImagenUrl;
    private Button btnModificarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        editTextProductoId = findViewById(R.id.editTextProductoId);
        editTextNuevoNombre = findViewById(R.id.editTextNuevoNombre);
        editTextNuevaDescripcion = findViewById(R.id.editTextNuevaDescripcion);
        editTextNuevoPrecio = findViewById(R.id.editTextNuevoPrecio);
        editTextNuevoStock = findViewById(R.id.editTextNuevoStock);
        editTextIdCategoria = findViewById(R.id.editTextIdCategoria);
        editTextImagenUrl = findViewById(R.id.editTextImagenUrl);
        btnModificarProducto = findViewById(R.id.btnModificarProducto);

        btnModificarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarProducto();
            }
        });
    }

    private void modificarProducto() {
        // Obtén los datos para la modificación del producto
        String productoIdStr = editTextProductoId.getText().toString();
        String nuevoNombre = editTextNuevoNombre.getText().toString();
        String nuevaDescripcion = editTextNuevaDescripcion.getText().toString();
        String nuevaCategoria = editTextIdCategoria.getText().toString();
        String nuevaImagen = editTextImagenUrl.getText().toString();
        double nuevoPrecio = Double.parseDouble(editTextNuevoPrecio.getText().toString());
        int nuevoStock = Integer.parseInt(editTextNuevoStock.getText().toString());

        // Verifica si se ingresó el ID del producto
        if (productoIdStr.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID del producto a modificar", Toast.LENGTH_SHORT).show();
            return;
        }

        int productoId = Integer.parseInt(productoIdStr);

        // Accede a la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Verifica si el producto con el ID dado existe
        String[] projection = {"id"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(productoId)};
        Cursor cursor = db.query("piezas", projection, selection, selectionArgs, null, null, null);

        if (cursor.getCount() > 0) {
            // El producto existe, procede con la modificación
            ContentValues values = new ContentValues();
            if (!nuevoNombre.isEmpty()) values.put("nombre", nuevoNombre);
            if (!nuevaDescripcion.isEmpty()) values.put("descripcion", nuevaDescripcion);
            values.put("precio", nuevoPrecio);
            values.put("stock", nuevoStock);
            if (!nuevaImagen.isEmpty()) values.put("imagen_url", nuevaImagen);
            if (!nuevaCategoria.isEmpty()) values.put("categoria_id", nuevaCategoria);

            // Realiza la actualización del producto
            int numRowsUpdated = db.update("piezas", values, "id=?", new String[]{String.valueOf(productoId)});

            if (numRowsUpdated > 0) {
                Toast.makeText(this, "Producto modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se pudo modificar el producto. Verifique el ID.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // El producto no existe
            Toast.makeText(this, "El producto con el ID proporcionado no existe", Toast.LENGTH_SHORT).show();
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();
    }
}
