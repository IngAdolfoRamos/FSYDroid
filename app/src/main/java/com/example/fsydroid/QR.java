package com.example.fsydroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

        Long eventValue;

        Intent getEventValue = getIntent();
        eventValue = getEventValue.getLongExtra("eventValue",0);

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
                        InsertOne(result.getText());
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

    private void InsertOne(String reference){
        SQLiteHelper connection = new SQLiteHelper(this, "FSY", null, 1);

        SQLiteDatabase db = connection.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilities.REFERENCE_FIELD, reference);
        Long result = db.insert(Utilities.PERSON_TABLE, Utilities.ID_FIELD, values);
        Toast.makeText(this, "Insertado correctamente: ", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Id: " + result, Toast.LENGTH_SHORT).show();
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