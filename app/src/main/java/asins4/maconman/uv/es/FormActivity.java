package asins4.maconman.uv.es;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.textfield.TextInputEditText;

public class FormActivity extends AppCompatActivity {
    int caso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if(getIntent().getExtras()!=null){
            caso = getIntent().getIntExtra("caso", 0);
        }
        if(caso != 0){
            Button save = findViewById(R.id.button);
            save.setText("Update");
        }
    }

    public void writeToDatabase(View view) {
        String app = getResources().getString(R.string.app_name);
        CovidDataBase dbHelper = new CovidDataBase(this, 1);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EditText editText = (EditText) findViewById(R.id.EditTextMunicipality);
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

        long newRowId = db.insert(CovidContract.CovidEntry.TABLE_NAME, null, values);
    }
}