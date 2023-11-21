package com.example.proyectotocho;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Obtener el correo del usuario que ha iniciado sesión
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Mostrar el correo en un TextView
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);
        userEmailTextView.setText("Correo: " + userEmail);

        // Mostrar la información de todos los objetos en la base de datos
        mostrarInformacionObjetos();
    }

    private void mostrarInformacionObjetos() {
        // Realizar una consulta a la base de datos para obtener la información de los objetos
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("piezas", null, null, null, null, null, null);

        // Recorrer el cursor y mostrar la información en algún lugar de la interfaz de usuario
        // Aquí asumo que estás utilizando un TextView para mostrar la información.
        TextView objetosTextView = findViewById(R.id.objetosTextView);

        StringBuilder objetosInfo = new StringBuilder();
        while (cursor.moveToNext()) {
            // Verificar si la columna existe en el cursor antes de intentar acceder a ella
            int nombreIndex = cursor.getColumnIndex("nombre");
            int descripcionIndex = cursor.getColumnIndex("descripcion");
            int precioIndex = cursor.getColumnIndex("precio");

            if (nombreIndex != -1 && descripcionIndex != -1 && precioIndex != -1) {
                String nombre = cursor.getString(nombreIndex);
                String descripcion = cursor.getString(descripcionIndex);
                double precio = cursor.getDouble(precioIndex);

                objetosInfo.append("Nombre: ").append(nombre).append("\n");
                objetosInfo.append("Descripción: ").append(descripcion).append("\n");
                objetosInfo.append("Precio: ").append(precio).append("\n\n");
            } else {
                // Manejar el caso en el que alguna de las columnas no exista en el cursor
                objetosInfo.append("Información no disponible\n\n");
            }
        }

        // Mostrar la información en el TextView
        objetosTextView.setText(objetosInfo.toString());

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();
    }
}
