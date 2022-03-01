package asins4.maconman.uv.es;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.SearchView;

import java.io.Serializable;

public class MunicipalitiesActivity extends AppCompatActivity {
    AdapterMunicipios adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipalities);

        //Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterMunicipios adapter = new AdapterMunicipios(this);
        adapt=adapter;
        recyclerView.setAdapter(adapter);

        View.OnClickListener onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This viewHolder will have all required values.
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int posicion = viewHolder.getAdapterPosition();
                Municipio mun = adapter.getMunPosicion(posicion);
                // Implement the listener!
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("municipio", mun);
                startActivity(intent);
            }
        };

        adapter.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapt.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapt.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RecyclerView recyclerView;
        AdapterMunicipios adapter;
// Handle item selection
        switch (item.getItemId()) {
            case R.id.web:
                // Do something when the user clicks on the new game
                String url = "https://dadesobertes.gva.es/es/dataset/covid-19-casos-confirmats-pcr-casos-pcr-en-els-ultims-14-dies-i-persones-mortes-per-municipi-2022";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            case R.id.casos:
                // Do something when the user clicks on the help item
                recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new AdapterMunicipios(getApplicationContext());
                adapter.ordenCasos();
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.incidencia:
                // Do something when the user clicks on the help item
                recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new AdapterMunicipios(getApplicationContext());
                adapter.ordenIncidencia();
                recyclerView.setAdapter(adapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}