package com.example.fsydroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class QR extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        this.setTitle("Escaneo QR");
        /*String dateTime;
        Calendar calendar;
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();*/

//        System.out.println(dateTime);

        SQLiteHelper connection = new SQLiteHelper(this, "FSY", null, 1);

        String eventValue;
        Intent getEventValue = getIntent();
        eventValue = getEventValue.getStringExtra("eventValue");
//        Toast.makeText(QR.this, "Valor recuperado: " + eventValue, Toast.LENGTH_LONG).show();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(QR.this, result.getText(), Toast.LENGTH_LONG).show();
                        String resultText = result.getText().toString();
                        InsertOne(resultText, eventValue);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }

    private void InsertOne(String reference, String value){
        SQLiteHelper connection = new SQLiteHelper(this, "FSY", null, 1);
        SQLiteDatabase read = connection.getReadableDatabase();
        String[] fields = {Utilities.id,Utilities.referencia,Utilities.id,Utilities.nombre_s};
        String[] ref = {reference};
//        String[] id = {value.toString()};

//        Cursor cursor = read.rawQuery("SELECT * FROM Person WHERE Id = 1", null);
        //busco el registro que se acaba de escanear para ver si existe ese codigo o referencia
        Cursor cursor = read.query(Utilities.PERSON_TABLE, fields, Utilities.referencia + " LIKE ?", ref,null,null,null,null);
//        cursor.moveToFirst();

//        String referencia = cursor.getString(1);
//        Toast.makeText(this, "devuelve: " + cursor.getCount(), Toast.LENGTH_SHORT).show();

        //Si encuentra el registro
        if (cursor.moveToFirst()){
//            Toast.makeText(this, "Encontrado...insertando", Toast.LENGTH_SHORT).show();
            String referencia = cursor.getString(1);
            String id = cursor.getString(2);
            String Sent = "False";

            try {
                String dateTime;
                Calendar calendar;
                calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat;
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateTime = simpleDateFormat.format(calendar.getTime()).toString();
                String Fecha = String.valueOf(dateTime);

                SQLiteDatabase db = connection.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Utilities.EVENT_FIELD, value);
                values.put(Utilities.REFERENCE_RECORDS_FIELD, referencia);
                values.put(Utilities.PERSON_ID_FIELD, id);
                values.put(Utilities.SENT, Sent);
                values.put(Utilities.CREATED_AT, Fecha);
                values.put(Utilities.UPDATED_AT, Fecha);
                Long result = db.insert(Utilities.RECORDS_TABLE, null, values);
//                Toast.makeText(this, "Insertado correctamente: ", Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "Insertado: " + result, Toast.LENGTH_SHORT).show();
                db.close();
            }catch(Exception e){
                Toast.makeText(this, "Intentalo de nuevo ", Toast.LENGTH_SHORT).show();
            }
        }
        else{

//            String referencia = cursor.getString(1);
            String id = "null";
            String Sent = "False";
//            Toast.makeText(this, "NOOOO Encontrado...insertando", Toast.LENGTH_SHORT).show();
            try {

                String dateTime;
                Calendar calendar;
                calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat;
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateTime = simpleDateFormat.format(calendar.getTime()).toString();
                String Fecha = String.valueOf(dateTime);

                SQLiteDatabase db = connection.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Utilities.EVENT_FIELD, value);
                values.put(Utilities.REFERENCE_RECORDS_FIELD, reference);
                values.put(Utilities.PERSON_ID_FIELD, id);
                values.put(Utilities.SENT, Sent);
                values.put(Utilities.CREATED_AT, Fecha);
                values.put(Utilities.UPDATED_AT, Fecha);
                Long result = db.insert(Utilities.RECORDS_TABLE, null, values);
//                Toast.makeText(this, "Insertado correctamente: ", Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "Insertado: " + result, Toast.LENGTH_SHORT).show();
                db.close();
            }catch(Exception e){
                Toast.makeText(this, "Intentalo de nuevo", Toast.LENGTH_SHORT).show();
            }
        }
//        Toast.makeText(this, "Registro: Id " + cursor.getString(0) + "Ref " + cursor.getString(1), Toast.LENGTH_SHORT).show();
        cursor.close();

//        System.out.println("valor de referencia: " + ref.toString());

/*        try {
            //Primero se manda el campo que quieres consultar, y luego el valor que buscara en ese campo
            Cursor cursor = read.query(Utilities.PERSON_TABLE, null, "Id = 1", null, null,null,null);
            *//*Cursor cursor = read.rawQuery("SELECT " + Utilities.ID_FIELD + "," + Utilities.REFERENCE_FIELD + " FROM " + Utilities.PERSON_TABLE
                + " WHERE " + Utilities.REFERENCE_FIELD + "=?", ref);*//*
            cursor.moveToFirst();
            cursor.close();
            Toast.makeText(this, "Registro: Id " + cursor.getString(0) + "Ref " + cursor.getString(1), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Registro no encontrado" + ref, Toast.LENGTH_SHORT).show();
        }*/

        //Save new data on scanned
        /*SQLiteDatabase db = connection.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilities.REFERENCE_FIELD, reference);
        Long result = db.insert(Utilities.PERSON_TABLE, null, values);
        Toast.makeText(this, "Insertado correctamente: ", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Id: " + result, Toast.LENGTH_SHORT).show();
        db.close();*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}