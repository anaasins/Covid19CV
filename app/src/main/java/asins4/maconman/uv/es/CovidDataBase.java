package asins4.maconman.uv.es;

import static asins4.maconman.uv.es.CovidContract.CREATE_TABLE;
import static asins4.maconman.uv.es.CovidContract.DELETE_ENTRIES;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CovidDataBase extends SQLiteOpenHelper {
    // Database name
    private static final String DATABASE_NAME="Covid";

    public CovidDataBase(Context context, int version){
        super(context, DATABASE_NAME, null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDelete(SQLiteDatabase db) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}