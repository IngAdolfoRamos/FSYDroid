package com.example.fsydroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    String prueba;

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

        String eventValue;
        Intent getEventValue = getIntent();
        eventValue = getEventValue.getStringExtra("eventValue");
        Toast.makeText(ManualSelection.this, "Valor recuperado: " + eventValue, Toast.LENGTH_SHORT).show();

        List<String> groupsArray = new ArrayList<String>();
        String[] columns = {Utilities.referencia};
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

        /* Populating Spinner with hardcoded groups*/
        groupsArray.add("Grupo 01");
        groupsArray.add("Grupo 02");
        groupsArray.add("Grupo 03");
        groupsArray.add("Grupo 04");
        groupsArray.add("Grupo 05");
        groupsArray.add("Grupo 06");
        groupsArray.add("Grupo 07");
        groupsArray.add("Grupo 08");
        groupsArray.add("Grupo 09");
        for (int i  = 10; i <= 80; i++){
            groupsArray.add("Grupo " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, groupsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        groupSelectorS.setAdapter(adapter);


        groupSelectorS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String[] valor = new String[1];
                String item = adapterView.getSelectedItem().toString();
                Toast.makeText(ManualSelection.this, item.toString(), Toast.LENGTH_SHORT).show();
                valor[0] = String.valueOf(item);
                System.out.println(valor[0]);
                prueba = valor[0];
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
                Toast.makeText(ManualSelection.this, "Seleccion de id: " + arrayList.toString() + " " + prueba,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}