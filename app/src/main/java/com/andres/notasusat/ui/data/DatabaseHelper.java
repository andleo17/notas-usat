package com.andres.notasusat.ui.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE course (id INTEGER PRIMARY KEY AUTOINCREMENT, code TEXT, name TEXT, teacher TEXT, credits INTEGER, grade INTEGER, state INTEGER)");
        db.execSQL("CREATE TABLE unity (id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, weight NUMERIC, grade NUMERIC, idCourse INTEGER NOT NULL CONSTRAINT fk_id_course REFERENCES course(id) ON DELETE CASCADE ON UPDATE CASCADE)");
        db.execSQL("CREATE TABLE activy (id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, weight NUMERIC, grade NUMERIC, idUnity INTEGER NOT NULL CONSTRAINT fk_id_unity REFERENCES unity(id) ON DELETE CASCADE ON UPDATE CASCADE)");
//        db.execSQL("INSERT INTO course (code , name , teacher, credits, grade, state) VALUES ('GAA1', 'Curso 01', 'Juan Perez', 3, 20, 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS course");
        db.execSQL("DROP TABLE IF EXISTS unity");
        db.execSQL("DROP TABLE IF EXISTS activity");
        onCreate(db);
    }
}
