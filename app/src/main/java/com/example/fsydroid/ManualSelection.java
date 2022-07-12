package com.example.fsydroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ManualSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_selection);

        Long eventValue;

        Intent getEventValue = getIntent();
        eventValue = getEventValue.getLongExtra("eventValue",0);

        Toast.makeText(ManualSelection.this, "Valor recuperado: " + eventValue, Toast.LENGTH_LONG).show();

    }
}