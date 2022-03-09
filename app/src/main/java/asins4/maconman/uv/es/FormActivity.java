package asins4.maconman.uv.es;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

public class FormActivity extends AppCompatActivity {
    int caso;
    String municipio;
    EditText editText;
    boolean update=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if(getIntent().getExtras()!=null){
            caso = getIntent().getIntExtra("caso", 0);
            municipio = getIntent().getStringExtra("munName");
        }
        if (municipio!= null && municipio!=""){
            editText = (EditText) findViewById(R.id.EditTextMunicipality);
            editText.setText(municipio);
        }
        if(caso != 0){
            update=true;

            //saco el caso covid a partir del id que he recogido
            CovidDataBase dbHelper = new CovidDataBase(this, 1);
            // Gets the data repository in read mode
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] projection = {
                    BaseColumns._ID,
                    CovidContract.CovidEntry.COLUMN_NAME_CODE,
                    CovidContract.CovidEntry.COLUMN_NAME_MUNICIPALITY,
                    CovidContract.CovidEntry.COLUMN_NAME_DATE,
                    CovidContract.CovidEntry.COLUMN_NAME_FEVER,
                    CovidContract.CovidEntry.COLUMN_NAME_COUGH,
                    CovidContract.CovidEntry.COLUMN_NAME_BREATH,
                    CovidContract.CovidEntry.COLUMN_NAME_FATIGUE,
                    CovidContract.CovidEntry.COLUMN_NAME_MUSCLE,
                    CovidContract.CovidEntry.COLUMN_NAME_HEADACHE,
                    CovidContract.CovidEntry.COLUMN_NAME_TASTE,
                    CovidContract.CovidEntry.COLUMN_NAME_THROAT,
                    CovidContract.CovidEntry.COLUMN_NAME_CONGESTION,
                    CovidContract.CovidEntry.COLUMN_NAME_NAUSEA,
                    CovidContract.CovidEntry.COLUMN_NAME_DIARRHEA,
                    CovidContract.CovidEntry.COLUMN_NAME_CONTACT
            };

            // Filter results WHERE
            String selection = CovidContract.CovidEntry._ID + " = ?";
            String[] selectionArgs = { String.valueOf(caso) };

            Cursor cursor = db.query(
                    CovidContract.CovidEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null
            );
           cursor.moveToPosition(0);
           //voy sacando la informacion y escribiendola en cada campo.
            int id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_CODE);
            String data = cursor.getString(id_column);
            editText = (EditText) findViewById(R.id.inputDiagnostic);
            editText.setText(data);

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_MUNICIPALITY);
            data = cursor.getString(id_column);
            editText = (EditText) findViewById(R.id.EditTextMunicipality);
            editText.setText(data);

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_DATE);
            data = cursor.getString(id_column);
            editText = (EditText) findViewById(R.id.editTextDate);
            editText.setText(data);

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_FEVER);
            int dataI = cursor.getInt(id_column);
            CheckBox check = findViewById(R.id.checkBoxFever);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_COUGH);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxCough);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_BREATH);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxBreathing);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_FATIGUE);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxFatigue);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_MUSCLE);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxMucle);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_HEADACHE);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxHead);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_TASTE);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxTaste);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_THROAT);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxSore);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_CONGESTION);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxCongestion);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_NAUSEA);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxNausea);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_DIARRHEA);
            dataI = cursor.getInt(id_column);
            check = findViewById(R.id.checkBoxDiarrhea);
            if (dataI==1){
                check.setChecked(true);
            }

            id_column = cursor.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_CONTACT);
            dataI = cursor.getInt(id_column);
            Switch contactS = findViewById(R.id.switch1);
            if (dataI==1){
                contactS.setChecked(true);
            }

        }
    }

    public void writeToDatabase() {
        String app = getResources().getString(R.string.app_name);
        CovidDataBase dbHelper = new CovidDataBase(this, 1);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        editText = (EditText) findViewById(R.id.EditTextMunicipality);
        String municipality = editText.getText().toString();

        editText = (EditText) findViewById(R.id.inputDiagnostic);
        String code = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editTextDate);
        String fecha = editText.getText().toString();

        CheckBox check = findViewById(R.id.checkBoxFever);
        boolean checked = check.isChecked();
        int fever;
        if(checked){
            fever=1;
        }else {
            fever=0;
        }

        check = findViewById(R.id.checkBoxCough);
        checked = check.isChecked();
        int cough;
        if(checked){
            cough=1;
        }else {
            cough=0;
        }

        check = findViewById(R.id.checkBoxBreathing);
        checked = check.isChecked();
        int breathing;
        if(checked){
            breathing=1;
        }else {
            breathing=0;
        }

        check = findViewById(R.id.checkBoxFatigue);
        checked = check.isChecked();
        int fatigue;
        if(checked){
            fatigue=1;
        }else {
            fatigue=0;
        }

        check = findViewById(R.id.checkBoxMucle);
        checked = check.isChecked();
        int muscle;
        if(checked){
            muscle=1;
        }else {
            muscle=0;
        }

        check = findViewById(R.id.checkBoxHead);
        checked = check.isChecked();
        int head;
        if(checked){
            head=1;
        }else {
            head=0;
        }

        check = findViewById(R.id.checkBoxTaste);
        checked = check.isChecked();
        int taste;
        if(checked){
            taste=1;
        }else {
            taste=0;
        }

        check = findViewById(R.id.checkBoxSore);
        checked = check.isChecked();
        int sore;
        if(checked){
            sore=1;
        }else {
            sore=0;
        }

        check = findViewById(R.id.checkBoxCongestion);
        checked = check.isChecked();
        int congestion;
        if(checked){
            congestion=1;
        }else {
            congestion=0;
        }

        check = findViewById(R.id.checkBoxNausea);
        checked = check.isChecked();
        int nausea;
        if(checked){
            nausea=1;
        }else {
            nausea=0;
        }

        check = findViewById(R.id.checkBoxDiarrhea);
        checked = check.isChecked();
        int diarrhea;
        if(checked){
            diarrhea=1;
        }else {
            diarrhea=0;
        }

        Switch contactS = findViewById(R.id.switch1);
        checked = contactS.isChecked();
        int contact;
        if(checked){
            contact=1;
        }else {
            contact=0;
        }

        ContentValues values = new ContentValues();
        values.put(CovidContract.CovidEntry.COLUMN_NAME_CODE, code);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_MUNICIPALITY, municipality);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_DATE, fecha);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_FEVER, fever);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_COUGH, cough);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_BREATH, breathing);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_FATIGUE, fatigue);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_MUSCLE, muscle);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_HEADACHE, head);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_TASTE, taste);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_THROAT, sore);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_CONGESTION, congestion);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_NAUSEA, nausea);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_DIARRHEA, diarrhea);
        values.put(CovidContract.CovidEntry.COLUMN_NAME_CONTACT, contact);

        if (update){
            //actualizo los datos
            // la clausula where
            String selection = CovidContract.CovidEntry._ID + " = ?";
            String[] selectionArgs = { String.valueOf(caso) };
            db.update(CovidContract.CovidEntry.TABLE_NAME, values, selection, selectionArgs);
        }else{
            //añado los datos
            long newRowId = db.insert(CovidContract.CovidEntry.TABLE_NAME, null, values);
        }
        //Abrir otra pagina
        Intent intent = new Intent(getApplicationContext(), MunicipalitiesActivity.class);
        startActivity(intent);
    }

    public void delete(){
        if (update){
            CovidDataBase dbHelper = new CovidDataBase(this, 1);
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // la clausula where
            String selection = CovidContract.CovidEntry._ID + " = ?";
            String[] selectionArgs = { String.valueOf(caso) };
            db.delete(CovidContract.CovidEntry.TABLE_NAME, selection, selectionArgs);
        }
        Intent intent = new Intent(getApplicationContext(), MunicipalitiesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_menu, menu);
        if(update){
            menu.findItem(R.id.menu_delete).setTitle("Eliminar");
            menu.findItem(R.id.menu_add).setTitle("Actualizar");

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_delete:
                delete();
                return true;
            case R.id.menu_add:
                writeToDatabase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}