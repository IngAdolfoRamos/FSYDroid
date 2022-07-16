package com.example.fsydroid;

public class Utilities {

    //Son 22 campos
    public static final String PERSON_TABLE = "Person";
    public static final String id = "id";
    public static final String referencia = "referencia";
    public static final String a_paterno = "a_paterno";
    public static final String a_materno = "a_materno";
    public static final String nombre_s = "nombre_s";
    public static final String tipo = "tipo";
    public static final String sexo = "sexo";
    public static final String f_nacimiento = "f_nacimiento";
    public static final String gruponombre = "gruponombre";
    public static final String consejero_grupo = "consejero_grupo";
    public static final String companianombre = "companianombre";
    public static final String consejero_compania = "consejero_compania";
    public static final String cabananombre = "cabananombre";
    public static final String cabanatipo = "cabanatipo";
    public static final String seguro = "seguro";
    public static final String informacion_medica = "informacion_medica";
    public static final String dificultad_fisica = "dificultad_fisica";
    public static final String tratamiento_fisico = "tratamiento_fisico";
    public static final String informacion_alimenticia = "informacion_alimenticia";
    public static final String problemas_digestivos = "problemas_digestivos";
    public static final String colitis_gastritis = "colitis_gastritis";
    public static final String diabetico_asmatico = "diabetico_asmatico";

    public static final String RECORDS_TABLE = "Records";
    public static final String ID_RECORDS_FIELD = "Id";
    public static final String EVENT_FIELD = "Event";
    public static final String REFERENCE_RECORDS_FIELD = "Reference";
    public static final String PERSON_ID_FIELD = "Person_Id";
    public static final String SENT = "Sent";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + PERSON_TABLE + " (" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + referencia + " TEXT, " + a_paterno + " TEXT, "
                    + a_materno + " TEXT, " + nombre_s + " TEXT, "
                    + tipo + " TEXT, " + sexo + " TEXT, "
                    + f_nacimiento + " TEXT, " + gruponombre + " TEXT, "
                    + consejero_grupo + " TEXT, " + companianombre + " TEXT, "
                    + consejero_compania + " TEXT, " + cabananombre + " TEXT, "
                    + cabanatipo + " TEXT, " + seguro + " TEXT, "
                    + informacion_medica + " TEXT, " + dificultad_fisica + " TEXT, "
                    + tratamiento_fisico + " TEXT, " + informacion_alimenticia + " TEXT, "
                    + problemas_digestivos + " TEXT, " + colitis_gastritis + " TEXT, "
                    + diabetico_asmatico + " TEXT" + ")";

    public static final String CREATE_RECORDS_TABLE =
            "CREATE TABLE " + RECORDS_TABLE + " (" + ID_RECORDS_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EVENT_FIELD + " TEXT, " + REFERENCE_RECORDS_FIELD + " TEXT, " + PERSON_ID_FIELD + " TEXT, "
                    + SENT + " TEXT, " + CREATED_AT + " TEXT, " + UPDATED_AT + " TEXT" + ")";
}
