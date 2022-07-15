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

public class QR extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        SQLiteHelper connection = new SQLiteHelper(this, "FSY", null, 1);

        String eventValue;
        Intent getEventValue = getIntent();
        eventValue = getEventValue.getStringExtra("eventValue");
        Toast.makeText(QR.this, "Valor recuperado: " + eventValue, Toast.LENGTH_LONG).show();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(QR.this, result.getText(), Toast.LENGTH_LONG).show();
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
        String[] fields = {Utilities.ID_FIELD,Utilities.REFERENCE_FIELD};
        String[] ref = {reference};
        String[] id = {value.toString()};

//        Cursor cursor = read.rawQuery("SELECT * FROM Person WHERE Id = 1", null);
        //busco el registro que se acaba de escanear para ver si existe ese codigo o referencia
/*        Cursor cursor = read.query(Utilities.PERSON_TABLE,fields, Utilities.REFERENCE_FIELD + " LIKE ?",ref,null,null,null,null);
        cursor.moveToFirst();
        Toast.makeText(this, "Registro: Id " + cursor.getString(0) + "Ref " + cursor.getString(1), Toast.LENGTH_SHORT).show();
        cursor.close();*/

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
        try {
            SQLiteDatabase db = connection.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Utilities.EVENT_FIELD, "Prueba comida");
            values.put(Utilities.REFERENCE_RECORDS_FIELD, reference);
            values.put(Utilities.PERSON_ID_FIELD, "305");
            Long result = db.insert(Utilities.RECORDS_TABLE, null, values);
            Toast.makeText(this, "Insertado correctamente: ", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Id: " + result, Toast.LENGTH_SHORT).show();
            db.close();
        }catch(Exception e){
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

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