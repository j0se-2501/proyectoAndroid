package com.example.proyectotocho;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


import android.database.SQLException;


public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "basededatos1";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS categorias (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT UNIQUE)");
            db.execSQL("CREATE TABLE IF NOT EXISTS piezas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, precio REAL, stock INTEGER, imagen_url TEXT, categoria_id INTEGER, FOREIGN KEY (categoria_id) REFERENCES categorias(id))");
            db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, correo TEXT UNIQUE, contrasena TEXT,nombre TEXT,direccion TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS pedidos (id INTEGER PRIMARY KEY AUTOINCREMENT, id_usuario INTEGER , fecha TEXT, direccion TEXT, precio REAL, FOREIGN KEY (id_usuario) REFERENCES usuarios(id))");
            db.execSQL("CREATE TABLE IF NOT EXISTS pedidos_a_piezas (id INTEGER PRIMARY KEY AUTOINCREMENT, id_pedido INTEGER, id_producto INTEGER, cantidad_producto INTEGER, FOREIGN KEY (id_pedido) REFERENCES pedido(id), FOREIGN KEY (id_producto) REFERENCES piezas(id))");
            db.execSQL("CREATE TABLE IF NOT EXISTS favoritos (id_usuario INTEGER, id_producto INTEGER, PRIMARY KEY (id_usuario, id_producto), FOREIGN KEY (id_usuario) REFERENCES usuarios(id), FOREIGN KEY (id_producto) REFERENCES piezas(id))");
            db.execSQL("CREATE TABLE IF NOT EXISTS carritos (id_usuario INTEGER, id_producto INTEGER, cantidad_producto INTEGER, PRIMARY KEY (id_usuario, id_producto), FOREIGN KEY (id_usuario) REFERENCES usuarios(id), FOREIGN KEY (id_producto) REFERENCES piezas(id))");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('1', 'motor')");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('2', 'transmision')");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('3', 'sobrealimentacion')");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('4', 'neumaticos')");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('5', 'llantas')");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('6', 'suspension')");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('7', 'frenos')");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('8', 'carroceria')");
            db.execSQL("INSERT INTO categorias (id, nombre) VALUES ('9', 'electronica')");

            // Motor
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Motor de Alto Rendimiento', 'Potente motor para mejorar el rendimiento de tu vehículo', 99, 10, 'imagen_motor', 1)");

// Transmisión
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Kit de Embrague Deportivo', 'Mejora la respuesta y el rendimiento de la transmisión', 29, 15, 'imagen_transmision', 2)");

// Sobrealimentación
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Turbocharger de Alta Potencia', 'Aumenta la potencia de tu motor con este turbocharger', 59, 8, 'imagen_sobrealimentacion', 3)");

// Neumáticos
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Neumáticos Deportivos', 'Mejora el agarre y la tracción en carretera', 14, 20, 'imagen_neumaticos', 4)");

// Llantas
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Llantas de Aleación Ligera', 'Llantas elegantes y ligeras para un mejor manejo', 199, 12, 'imagen_llantas', 5)");

// Suspensión
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Kit de Suspensión Deportiva', 'Mejora la estabilidad y el manejo de tu vehículo', 34, 10, 'imagen_suspension', 6)");

// Frenos
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Kit de Frenos de Alto Rendimiento', 'Mejora la potencia de frenado con este kit', 49, 15, 'imagen_frenos', 7)");

// Carrocería
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Alerón Deportivo', 'Agrega estilo y aerodinámica a tu vehículo', 12, 18, 'imagen_carroceria', 8)");

// Electrónica
            db.execSQL("INSERT INTO piezas (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES ('Centralita de Potencia', 'Optimiza el rendimiento del motor con esta centralita', 449, 12, 'imagen_electronica', 9)");



            db.execSQL("INSERT INTO usuarios (correo, contrasena) VALUES ('admin@admin.com', 'admin')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/////jajajaajjajaajjaja
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes manejar la actualización de la base de datos si es necesario.
    }

    public static DbHelper getInstance(Context context) {
        // Verificar si la base de datos ya existe.
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // La base de datos no existe todavía.
        }

        if (checkDB != null) {
            checkDB.close();
            return new DbHelper(context);
        } else {
            return new DbHelper(context);
        }
    }
}
