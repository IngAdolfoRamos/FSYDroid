package com.example.fsydroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManualSelection extends AppCompatActivity {

    Spinner groupSelectorS;
    TextView nameTV;
    CheckBox nameSelectedCB;
    ArrayList<String> groups;
    ArrayList<Person> ListPeople;

    SQLiteHelper connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_selection);

        connection = new SQLiteHelper(this, "FSY", null, 1);

        groupSelectorS = findViewById(R.id.groupSelectorS);
        nameTV = findViewById(R.id.nameTV);
        nameSelectedCB = findViewById(R.id.nameSelectedCB);

        Long eventValue;
        Intent getEventValue = getIntent();
        eventValue = getEventValue.getLongExtra("eventValue",0);
        Integer value = eventValue.intValue();
        Toast.makeText(ManualSelection.this, "Valor recuperado: " + eventValue, Toast.LENGTH_SHORT).show();


/*        // you need to have a list of data that you want the spinner to display
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("item1");
        spinnerArray.add("item2");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.groupSelectorS);
        sItems.setAdapter(adapter);*/

        List<String> groupsArray = new ArrayList<String>();
        String[] columns = {Utilities.REFERENCE_FIELD};
        String reference = "7501593830225";
        String[] ref = {reference};

        SQLiteDatabase readGroups = connection.getReadableDatabase();

        Cursor cursor = readGroups.rawQuery("SELECT Id,Reference FROM Person",null);
        cursor.moveToFirst();
        groupsArray.add("agregado manual");

        if (cursor.moveToFirst()) {
            do {
                groupsArray.add(cursor.getString(1).toString());
                Toast.makeText(this, "DB: " + cursor.getString(0), Toast.LENGTH_SHORT).show();
            } while(cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, groupsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        groupSelectorS.setAdapter(adapter);

        groupSelectorS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getGroupList(){
        SQLiteDatabase db = connection.getReadableDatabase();

    }
}