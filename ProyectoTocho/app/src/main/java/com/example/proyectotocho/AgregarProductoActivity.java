package com.example.proyectotocho;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class AgregarProductoActivity extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextPrecio;
    private EditText editTextStock;
    private Spinner spinnerCategoria;
    private EditText editTextImagenUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        editTextStock = findViewById(R.id.editTextStock);
        editTextImagenUrl = findViewById(R.id.editTextImagenUrl);
        spinnerCategoria =findViewById(R.id.spinnerCategoria);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_notif_bar));
        // Configurar el adaptador para el Spinner usando el array de categorías
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categorias,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        Button btnAgregarProducto = findViewById(R.id.btnAgregarProducto);

        btnAgregarProducto.setOnClickListener(v -> agregarProducto());
    }

    // AgregarProductoActivity.java

// ...

    private void agregarProducto() {
        // Obtén los datos del nuevo producto
        String nombre = editTextNombre.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String precio = editTextPrecio.getText().toString();
        String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString();
        int stock;
        String imagenUrl = editTextImagenUrl.getText().toString();

        try {
            // Intenta convertir el texto del precio a un número decimal

            stock = Integer.parseInt(editTextStock.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese un precio y stock válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Extraer el número de la categoría
        int categoriaId;
        try {
            categoriaId = Integer.parseInt(categoriaSeleccionada.split("\\.")[0].trim());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(this, "Error al obtener la categoría seleccionada", Toast.LENGTH_SHORT).show();
            return;
        }

        // Accede a la base de datos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Crea un objeto ContentValues para insertar los datos en la tabla "piezas"
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("stock", stock);
        values.put("imagen_url", imagenUrl);
        values.put("categoria_id", categoriaId);
        // Inserta el nuevo producto en la tabla "piezas"
        long newRowId = db.insert("piezas", null, values);

        // Cierra la base de datos
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
        }
    }


}

