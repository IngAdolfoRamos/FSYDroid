package com.example.fsydroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int REQUEST_CODE = 123456;

    Spinner eventSelectionS;
    Button qrB, manualSelectionB, deleteDBB;
    RequestQueue myQueue;

    SQLiteHelper connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myQueue = Volley.newRequestQueue(this);

        eventSelectionS = findViewById(R.id.actionSpinner);
        qrB = findViewById(R.id.qrB);
        manualSelectionB = findViewById(R.id.manualB);
        deleteDBB = findViewById(R.id.deleteDBB);

        checkPermission();

        connection = new SQLiteHelper(this, "FSY", null, 1);
        try {

            SQLiteDatabase db = connection.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Person",null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0){
                Toast.makeText(this, "BD existente", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "1 Longitud del cursor: " + cursor.getCount(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Creando DB por primera vez", Toast.LENGTH_LONG).show();
//                db.rawQuery("INSERT INTO Person VALUES ('205', 'Referencia 205')",null);
                Toast.makeText(this, "2 Longitud del cursor: " + cursor.getCount(), Toast.LENGTH_SHORT).show();
                getJsonRequest();
                //Creando el array para insertar varios registros a la vez
                /*SQLiteDatabase ins = connection.getWritableDatabase();
                List<String> registros = new ArrayList<String>();
                String ids, referencias;
                ContentValues val = new ContentValues();
                for (int i = 1; i < 10; i++){
                    ids = "" + i;
                    referencias = "Referencia: " + i;
                    val.put(Utilities.id, ids);
                    val.put(Utilities.referencia, referencias);
                    ins.insert(Utilities.PERSON_TABLE, null, val);
                }
                ins.close();*/
                //Intento crear la base de datos por primera vez
                /*try {
                    SQLiteDatabase insert = connection.getWritableDatabase();
                    Toast.makeText(this, "Creando registros", Toast.LENGTH_SHORT).show();
//                    insert.execSQL("INSERT INTO Person VALUES ('207', 'Referencia 205')"); //Sin funciona
                    ContentValues valores = new ContentValues();
                    String id = "900";
                    String referencia = "Referencia 900";
                    valores.put(Utilities.ID_FIELD, id);
                    valores.put(Utilities.REFERENCE_FIELD, referencia);
                    insert.insert(Utilities.PERSON_TABLE, null, valores);
                    insert.close();
                }catch (Exception e){
                    Toast.makeText(this, "Error creando registros: " +e.toString(), Toast.LENGTH_LONG).show();
                }*/
            }
            db.close();
        }catch(Exception e){
            Toast.makeText(this, "Error 999: " +  e.toString(), Toast.LENGTH_LONG).show();
        }


        ArrayList<String> items = new ArrayList<>();
        items.add("Ingreso");
        items.add("Desayuno");
        items.add("Comida");
        items.add("Cena");
        items.add("Salida");
        items.add("Conteo nocturno");

        eventSelectionS.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items));
        
        eventSelectionS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();

                qrB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent goToQRPage = new Intent(MainActivity.this, QR.class);
                        goToQRPage.putExtra("eventValue", item);
                        startActivity(goToQRPage);
                    }
                });

                manualSelectionB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent goToManualSelectionPage = new Intent(MainActivity.this, ManualSelection.class);
                        goToManualSelectionPage.putExtra("eventValue", item);
                        startActivity(goToManualSelectionPage);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        deleteDBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteHelper connection = new SQLiteHelper(MainActivity.this, "FSY", null, 1);
                SQLiteDatabase db = connection.getWritableDatabase();

                try {
                    ContentValues values = new ContentValues();
                    values.put(Utilities.id,8);
                    values.put(Utilities.referencia, "REFERENCIA 8");
                    Long result = db.insert(Utilities.PERSON_TABLE, null, values);
                    Toast.makeText(MainActivity.this, "Insertado en Person: ", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Fallo person: " + e.toString(), Toast.LENGTH_LONG).show();
                }

                try {
                    ContentValues referenceTable = new ContentValues();
                    referenceTable.put(Utilities.ID_RECORDS_FIELD, 3);
                    referenceTable.put(Utilities.EVENT_FIELD, "Comida");
                    referenceTable.put(Utilities.REFERENCE_RECORDS_FIELD, "Referencia de records table");
                    referenceTable.put(Utilities.PERSON_ID_FIELD, "Comida");
                    db.insert(Utilities.RECORDS_TABLE, null, referenceTable);
                    Toast.makeText(MainActivity.this, "Insertado en Records: ", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Fallo records: " + e.toString(), Toast.LENGTH_LONG).show();
                }

//                Toast.makeText(MainActivity.this, "Insertado correctamente: ", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "Id: " + result, Toast.LENGTH_SHORT).show();
                db.close();
            }
        });
    }

    private void getJsonRequest(){
        String url = "https://fsy.estacaamecameca.org/api/v1/personas/list";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String respuesta = response.toString();
                    JSONObject start = new JSONObject(respuesta);
                    JSONObject dataObject = start.getJSONObject("data");
                    JSONArray personasArray = dataObject.getJSONArray("personas");
                    JSONObject nuevo = new JSONObject();
                    JSONArray nuevoArray = new JSONArray();
                    ArrayList<String> arr = new ArrayList<String>();
                    SQLiteHelper connections = new SQLiteHelper(MainActivity.this, "FSY", null, 1);
                    SQLiteDatabase insertFT = connections.getWritableDatabase();
                    ContentValues valuesFT = new ContentValues();
                    for (int o = 0; o <= personasArray.length(); o++){
                        JSONObject end = personasArray.getJSONObject(o);
                        String id = end.getString("id");
                        String referencia = end.getString("referencia");
                        System.out.println("El id del registro " + o + " es: " + id);
                        System.out.println("La referencia del registro " + o + " es: " + referencia);

                        nuevo.put("id", id);
                        nuevo.put("refo", referencia);

                        //Insertando registros en la base de datos
                        valuesFT.put(Utilities.id,id);
                        valuesFT.put(Utilities.referencia, referencia);
                        Long result = insertFT.insert(Utilities.PERSON_TABLE, null, valuesFT);

                        arr.add(nuevo.toString());
                        System.out.println("ARRR: " + arr);

//                        nuevoArray.put(nuevo);

                        System.out.println("Nuevo Json id " + o + " es: " + nuevo.getString("id"));
                        System.out.println("Respuesta json: " + nuevo);
                        System.out.println("Array final: " + nuevoArray);
                        System.out.println("------------------");
                    }
                    System.out.println("JSON CREADO: " + nuevo);
                    System.out.println("Array finalillo: " + nuevoArray);
//                    System.out.println("Respuesta json: " + respuesta);

                    insertFT.close();
                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        myQueue.add(request);
    }

    public void checkPermission()
    {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.INTERNET};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[3]) == PackageManager.PERMISSION_GRANTED) {

            // Requesting the permission
//            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
//            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this, permissions,REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(MainActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}