package com.example.fsydroid;

public class Utilities {

    public static final String PERSON_TABLE = "Person";
    public static final String ID_FIELD = "Id";
    public static final String REFERENCE_FIELD = "Reference";

    public static final String RECORDS_TABLE = "Records";
    public static final String ID_RECORDS_FIELD = "Id";
    public static final String EVENT_FIELD = "Event";
    public static final String REFERENCE_RECORDS_FIELD = "Reference";
    public static final String PERSON_ID_FIELD = "Person_Id";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + PERSON_TABLE + " (" + ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + REFERENCE_FIELD + " TEXT)";

    public static final String CREATE_RECORDS_TABLE =
            "CREATE TABLE " + RECORDS_TABLE + " (" + ID_RECORDS_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EVENT_FIELD + " TEXT, " + REFERENCE_RECORDS_FIELD + " TEXT, " + PERSON_ID_FIELD + " TEXT)";
}
