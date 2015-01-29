package com.exgperu.basededatos;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Abrimos la base de datos DBProductos
        ProductosSQLiteHelper prdbh = new ProductosSQLiteHelper(this, "DBProductos", null, 1);

        SQLiteDatabase db = prdbh.getWritableDatabase();

        //Si se ha abierto la BD correctamente
        if(db != null){
            //revisamos que no existan datos en la tabla
            Cursor c = db.rawQuery("SELECT * FROM Productos",null);
            if(c.getCount()>0){

            }else{
                //insertamos 10 productos de ejemplo
                for(int i=1; i<=10;i++){
                    //Generamos los datos a insertar
                    int codigo = i;
                    String nombre = "Producto"+ i;
                    double precio = Math.random()*100;

                    //insertamos los datos en la tabla Productos
                    db.execSQL("INSERT INTO Productos(codigo,nombre,precio)"+
                            "VALUES("+codigo+",'"+nombre+"',"+precio+")");

                }
            }
            //cerramos la base de datos
            db.close();
        }else{
            Toast.makeText(this,"Base de Datos no Definida",Toast.LENGTH_LONG).show();
        }

        consultar();

        Button insertar = (Button)findViewById(R.id.btn_ins);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

        Button eliminar = (Button)findViewById(R.id.btn_del);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
    }

    public void eliminar(){
        //Abrimos la base de datos DBProductos
        ProductosSQLiteHelper prdbh = new ProductosSQLiteHelper(this, "DBProductos", null, 1);

        SQLiteDatabase db = prdbh.getWritableDatabase();
        EditText codigo = (EditText)findViewById(R.id.txt_codigo);
        String clave = codigo.getText().toString();
        //cargamos el parametro
        String[] code = new String[] {clave};
        //realizamos la consulta
        Cursor c = db.rawQuery("SELECT * FROM Productos WHERE codigo=?",code);
        if(c.getCount()>0){
            //Si existe lo eliminaremos
            db.delete("Productos","codigo=?",code);
        }else{
            Toast.makeText(this,"El Registro no existe o ya fue eliminado",Toast.LENGTH_LONG).show();
        }
        consultar();
    }

    public void actualizar(){
        //Abrimos la base de datos DBProductos
        ProductosSQLiteHelper prdbh = new ProductosSQLiteHelper(this, "DBProductos", null, 1);

        SQLiteDatabase db = prdbh.getWritableDatabase();
        EditText codigo = (EditText)findViewById(R.id.txt_codigo);
        EditText nombre = (EditText)findViewById(R.id.txt_nombre);
        EditText precio = (EditText)findViewById(R.id.txt_precio);

        String clave = codigo.getText().toString();
        String name = nombre.getText().toString();
        String valor = precio.getText().toString();
        //cargamos el parametro
        String[] code = new String[] {clave};
        //realizamos la consulta
        Cursor c = db.rawQuery("SELECT * FROM Productos WHERE codigo=?",code);
        if(c.getCount()>0){
            //Procedemos a Actualizar un registro existente
            ContentValues registro = new ContentValues();
            registro.put("nombre",name);
            registro.put("precio",valor);
            //Argumentos
            String[] args = new String[]{clave};
            //procedemos con la actualizacion
            db.update("Productos",registro,"codigo=?",args);
        }else{
           //Ahora procederemos a Insertar
            ContentValues nuevo = new ContentValues();
            nuevo.put("codigo",clave);
            nuevo.put("nombre",name);
            nuevo.put("precio",valor);
            //Insertamos el registro en la Base de datos
            db.insert("Productos",null,nuevo);
        }
        consultar();
    }

    public void consultar(){
        //Abrimos la base de datos DBProductos
        ProductosSQLiteHelper prdbh = new ProductosSQLiteHelper(this, "DBProductos", null, 1);

        SQLiteDatabase db = prdbh.getWritableDatabase();

        //Procedemos a mostrar los registros actuales
        Cursor c = db.rawQuery("SELECT * from Productos",null);
        String registros = new String();
        if(c.moveToFirst()){
            //recorremos el cursor
            do{
                String codigo = c.getString(0);
                String nombre = c.getString(1);
                String precio = c.getString(2);
                registros += codigo +" - "+nombre+" - S/. "+precio+"\n";
            }while(c.moveToNext());
            TextView base = (TextView)findViewById(R.id.textView);
            base.setText(registros);
        }
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
