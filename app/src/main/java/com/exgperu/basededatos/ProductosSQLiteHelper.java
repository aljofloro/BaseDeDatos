package com.exgperu.basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Beto on 13/01/2015.
 */
public class ProductosSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia de creacion de la base de datos
    String sqlCreate = "CREATE TABLE Productos(codigo INTEGER, nombre TEXT, precio REAL )";

    public ProductosSQLiteHelper (Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(contexto,nombre,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //ejecutamos la creacion de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //vamos a eliminar una tabla anterior en caso exista, lo mas indicado seria
        //migrar los datos a la nueva estructura, pero para efectos del ejemplo puede funcionar de esta manera

        //eliminamos la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS DBProductos.Productos");

        //Creamos la nueva tabla
        db.execSQL(sqlCreate);
    }
}
