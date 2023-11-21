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
            // Crear la tabla usuarios
            db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY, correo TEXT UNIQUE, contraseña TEXT)");

            db.execSQL("INSERT INTO usuarios (correo, contraseña) VALUES ('admin@admin.com', 'admin')");
            db.execSQL("CREATE TABLE IF NOT EXISTS piezas (id INTEGER PRIMARY KEY, nombre TEXT, descripcion TEXT, precio REAL, stock INTEGER, imagen_url TEXT)");
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
