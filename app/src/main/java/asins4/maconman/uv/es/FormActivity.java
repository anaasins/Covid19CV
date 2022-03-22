package asins4.maconman.uv.es;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.location.LocationListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {
    int caso;
    String municipio;
    EditText editText;
    boolean update=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

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

    public class Localizacion implements LocationListener {
        FormActivity formActivity;
        public FormActivity getFormActivity() {
            return formActivity;
        }
        public void setMainActivity(FormActivity formActivity) {
            this.formActivity = formActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            this.formActivity.setLocation(loc);
        }
        /*
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje1.setText("GPS Desactivado");
        }*/
        /*
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje1.setText("GPS Activado");
        }*/
        /*
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }*/
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    municipio = DirCalle.getLocality();
                    EditText myeditText = (EditText) findViewById(R.id.EditTextMunicipality);
                    myeditText.setText(DirCalle.getLocality());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}