package com.example.fsydroid;

public class Utilities {

    public static final String PERSON_TABLE = "Person";
    public static final String ID_FIELD = "Id";
    public static final String REFERENCE_FIELD = "Reference";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + PERSON_TABLE + " (" + ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + REFERENCE_FIELD + " TEXT)";
}
