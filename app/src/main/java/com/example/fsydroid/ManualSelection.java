package com.example.fsydroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManualSelection extends AppCompatActivity implements QuantityListener{

    Spinner groupSelectorS;
    ArrayList<String> groups;
    ArrayList<Person> ListPeople;
    RecyclerView peopleListRV;
    Button saveDataB;

    SQLiteHelper connection;
    QuantityAdapter quantityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_selection);

        connection = new SQLiteHelper(this, "FSY", null, 1);

        groupSelectorS = findViewById(R.id.groupSelectorS);
        peopleListRV  = findViewById(R.id.peopleListRV);
        saveDataB = findViewById(R.id.saveDataB);

        Long eventValue;
        Intent getEventValue = getIntent();
        eventValue = getEventValue.getLongExtra("eventValue",0);
        Integer value = eventValue.intValue();
        Toast.makeText(ManualSelection.this, "Valor recuperado: " + eventValue, Toast.LENGTH_SHORT).show();

        List<String> groupsArray = new ArrayList<String>();
        String[] columns = {Utilities.REFERENCE_FIELD};
        String reference = "7501593830225";
        String[] ref = {reference};

        /* Obtener las referencias o grupos para llenar el Picker pero se va a harcodear */
/*        SQLiteDatabase readGroups = connection.getReadableDatabase();
        Cursor cursor = readGroups.rawQuery("SELECT Id,Reference FROM Person",null);
        cursor.moveToFirst();
        groupsArray.add("agregado manual");

        if (cursor.moveToFirst()) {
            do {
                groupsArray.add(cursor.getString(0).toString());
                Toast.makeText(this, "DB: " + cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while(cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, groupsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        groupSelectorS.setAdapter(adapter);*/

        groupSelectorS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setRecyclerView();

    }

    private ArrayList<String> getQauntityData(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        arrayList.add("7");
        return arrayList;
    }
    private void setRecyclerView() {
        peopleListRV.setHasFixedSize(true);
        peopleListRV.setLayoutManager(new LinearLayoutManager(this));
        quantityAdapter = new QuantityAdapter(this, getQauntityData(), this);
        peopleListRV.setAdapter(quantityAdapter);
    }

    @Override
    public void onQuantityChange(ArrayList<String> arrayList) {
        saveDataB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ManualSelection.this, "Seleccion de id: " + arrayList.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}