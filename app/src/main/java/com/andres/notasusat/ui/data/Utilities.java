package com.andres.notasusat.ui.data;

public class Utilities {

    public static final String TABLE_COURSE = "course";
    public static final String COLUMN_ID_COURSE = "id";
    public static final String COLUMN_CODE_COURSE = "code";
    public static final String COLUMN_NAME_COURSE = "name";
    public static final String COLUMN_TEACHER_COURSE = "teacher";
    public static final String COLUMN_GRADE_COURSE = "grade";
    public static final String COLUMN_STATE_COURSE = "state";
    public static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSE +" (" +  COLUMN_ID_COURSE +
                            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CODE_COURSE + " TEXT, " +  COLUMN_NAME_COURSE + " TEXT, " +
                            COLUMN_TEACHER_COURSE + " TEXT, "+COLUMN_GRADE_COURSE+ " INTEGER, " + COLUMN_STATE_COURSE+
                            " BOOLEAN); ";


    public static final String TABLE_UNITY = "unity";
    public static final String COLUMN_ID_UNITY = "id";
    public static final String COLUMN_DESCRIPTION_UNITY = "description";
    public static final String COLUMN_WEIGHT_UNITY = "weight";
    public static final String COLUMN_GRADE_UNITY = "grade";
    public static final String CREATE_TABLE_UNITY = "CREATE TABLE "+TABLE_UNITY+ " ("+COLUMN_ID_UNITY+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            COLUMN_DESCRIPTION_UNITY+ " TEXT, "+COLUMN_WEIGHT_UNITY+ " REAL, "+ COLUMN_GRADE_UNITY+ " REAL);";


    public static final String TABLE_ACTIVITY = "unity";
    public static final String COLUMN_ID_ACTIVITY = "id";
    public static final String COLUMN_DESCRIPTION_ACTIVITY = "description";
    public static final String COLUMN_WEIGHT_ACTIVITY = "weight";
    public static final String COLUMN_GRADE_ACTIVITY = "grade";
    public static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE "+TABLE_ACTIVITY+ " ("+COLUMN_ID_ACTIVITY+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_DESCRIPTION_ACTIVITY+ " TEXT, "+COLUMN_WEIGHT_ACTIVITY+ " REAL, "+ COLUMN_GRADE_ACTIVITY+ " REAL);";




}
