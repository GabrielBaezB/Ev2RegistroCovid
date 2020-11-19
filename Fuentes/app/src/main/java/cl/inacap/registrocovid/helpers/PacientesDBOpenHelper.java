package cl.inacap.registrocovid.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PacientesDBOpenHelper extends SQLiteOpenHelper {

    private final String sqlCreate = "CREATE TABLE pacientes("+
            "rut TEXT PRIMARY KEY NOT NULL,"+
            "nombre TEXT,"+
            "apellido TEXT,"+
            "fecha TEXT,"+
            "areaTrabajo TEXT,"+
            "sintomas TEXT,"+
            "temperatura REAL,"+
            "tos TEXT,"+
            "presionArterial INTEGER)";


    public PacientesDBOpenHelper(@Nullable Context context
            , @Nullable String name
            , @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS pacientes");
        db.execSQL(sqlCreate);

    }
}
