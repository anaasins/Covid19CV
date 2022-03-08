package asins4.maconman.uv.es;

import android.provider.BaseColumns;

public class CovidContract {

    private CovidContract() {}

    public static class CovidEntry implements BaseColumns {
        // Name of the table in the database
        public static final String TABLE_NAME="Covid";
        // Name of the columns in the table
        public static final String COLUMN_NAME_CODE="code";
        public static final String COLUMN_NAME_DATE="date";
        public static final String COLUMN_NAME_FEVER="fever";
        public static final String COLUMN_NAME_COUGH="cough";
        public static final String COLUMN_NAME_BREATH="breath";
        public static final String COLUMN_NAME_FATIGUE="fatigue";
        public static final String COLUMN_NAME_MUSCLE="muscle";
        public static final String COLUMN_NAME_HEADACHE="headache";
        public static final String COLUMN_NAME_TASTE="taste";
        public static final String COLUMN_NAME_THROAT="throat";
        public static final String COLUMN_NAME_CONGESTION="congestion";
        public static final String COLUMN_NAME_NAUSEA="nausea";
        public static final String COLUMN_NAME_DIARRHEA="diarrhea";
        public static final String COLUMN_NAME_CONTACT="contact";
        public static final String COLUMN_NAME_MUNICIPALITY="municipality";

    }
    public static final String CREATE_TABLE =
            "CREATE TABLE " + CovidEntry.TABLE_NAME + " (" +
                    CovidEntry._ID + " INTEGER PRIMARY KEY," +
                    CovidEntry.COLUMN_NAME_CODE + " TEXT," +
                    CovidEntry.COLUMN_NAME_DATE + " TEXT," +
                    CovidEntry.COLUMN_NAME_FEVER + " INTEGER," +
                    CovidEntry.COLUMN_NAME_COUGH + " INTEGER," +
                    CovidEntry.COLUMN_NAME_BREATH + " INTEGER," +
                    CovidEntry.COLUMN_NAME_FATIGUE + " INTEGER," +
                    CovidEntry.COLUMN_NAME_MUSCLE + " INTEGER," +
                    CovidEntry.COLUMN_NAME_HEADACHE + " INTEGER," +
                    CovidEntry.COLUMN_NAME_TASTE + " INTEGER," +
                    CovidEntry.COLUMN_NAME_THROAT + " INTEGER," +
                    CovidEntry.COLUMN_NAME_CONGESTION + " INTEGER," +
                    CovidEntry.COLUMN_NAME_NAUSEA + " INTEGER," +
                    CovidEntry.COLUMN_NAME_DIARRHEA + " INTEGER," +
                    CovidEntry.COLUMN_NAME_CONTACT + " INTEGER," +
                    CovidEntry.COLUMN_NAME_MUNICIPALITY + " TEXT)";
    public static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CovidEntry.TABLE_NAME;
}
