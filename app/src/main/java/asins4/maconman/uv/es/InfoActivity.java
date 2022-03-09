package asins4.maconman.uv.es;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.Serializable;

public class InfoActivity extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        if(getIntent().getExtras()!=null){
            Municipio mun = (Municipio) getIntent().getSerializableExtra("municipio");
            this.setTitle(mun.getMunicipi());
            name = mun.getMunicipi();

            TextView tv = findViewById(R.id.textViewCodigo);
            tv.setText(String.valueOf(mun.getCodMunicipio()));

            tv = findViewById(R.id.textViewPCR);
            tv.setText(String.valueOf(mun.getCasosPCR()));

            tv = findViewById(R.id.textViewPCR14);
            tv.setText(String.valueOf(mun.getCasosPCR14()));

            tv = findViewById(R.id.textViewDefunciones);
            tv.setText(String.valueOf(mun.getDefunciones()));

            tv = findViewById(R.id.textViewIncidencia);
            tv.setText(mun.getIncidenciaPCR());

            tv = findViewById(R.id.textViewIncidencia14);
            tv.setText(mun.getIncidenciaPCR14());

            tv = findViewById(R.id.textViewTasa);
            tv.setText(mun.getTasaDefuncion());
        }

        CovidDataBase dbHelper = new CovidDataBase(this, 1);
        // Gets the data repository in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                CovidContract.CovidEntry.COLUMN_NAME_CODE
        };

        // Filter results WHERE
        String selection = CovidContract.CovidEntry.COLUMN_NAME_MUNICIPALITY + " = ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(
                CovidContract.CovidEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );

        // Setup cursor adapter using cursor from last step
        CovidCursorAdapter covidAdapter = new CovidCursorAdapter();
        covidAdapter.setCursor(cursor);
        RecyclerView recyclerView = findViewById(R.id.reciclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(covidAdapter);

        View.OnClickListener onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This viewHolder will have all required values.
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int posicion = viewHolder.getAdapterPosition();
                //muevo el cursor a la tarea que quiero marcar como hecha
                //este cursor sale de arriba de cuando cojo los datos a mostrar
                cursor.moveToPosition(posicion);
                //recojo el indice de dicha tarea
                int index = cursor.getColumnIndex(CovidContract.CovidEntry._ID);
                int id = cursor.getInt(index);
                // Implement the listener!
                Intent intent = new Intent(getApplicationContext(), FormActivity.class);
                intent.putExtra("caso", id);
                startActivity(intent);
            }
        };

        covidAdapter.setOnItemClickListener(onItemClickListener);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormActivity.class);
                intent.putExtra("munName", name);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.second_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RecyclerView recyclerView;
        AdapterMunicipios adapter;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.map:
                String adaptedName = name.replaceAll(" ", "+");
                // Do something when the user clicks on the new game
                String url = "geo:0,0?q="+adaptedName;
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}