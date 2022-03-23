package asins4.maconman.uv.es;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MunicipalitiesActivity extends AppCompatActivity {
    AdapterMunicipios adapter;
    RecyclerView recyclerView;
    ArrayList municipios=new ArrayList<Municipio>();
    HTTPConnector httpConnector;

    class HTTPConnector extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... params) {
            String id = "";
            //Perform the request and get the answer
            // saber el utimo resource
            String url1 = "https://dadesobertes.gva.es/api/3/action/package_show?id=5403e057-5b64-4347-ae44-06fa7a65e1b8";
            Writer writer1 = new StringWriter();
            char[] buffer1 = new char[1024];
            try {
                URL obj = new URL(url1);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                con.setRequestProperty("accept", "application/json;");
                con.setRequestProperty("accept-language", "es");
                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF8"));
                int n;
                while ((n = in.read(buffer1)) != -1) {
                    writer1.write(buffer1, 0, n);
                }
                in.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsonString1 = writer1.toString();
            try {
                JSONObject file1 = new JSONObject(jsonString1);
                JSONObject result1 = file1.getJSONObject("result");
                JSONArray jsonArray1 = result1.getJSONArray("resources");
                //JSONObject records = result.getJSONObject("records");

                int num = jsonArray1.length();
                JSONObject jsonObject1 = jsonArray1.getJSONObject(num-1);
                id = jsonObject1.getString("id");
                Log.d("id", id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Practica 4 --> Cogemos los datos de Internet directamente
            String url = "https://dadesobertes.gva.es/es/api/3/action/datastore_search?resource_id="+id+"&limit=1000";
            Writer writer = new StringWriter();
            char[] buffer = new char[2048];
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                con.setRequestProperty("accept", "application/json;");
                con.setRequestProperty("accept-language", "es");
                con.connect();
                int responseCode = con.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF8"));
                int n;
                while ((n = in.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                in.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsonString = writer.toString();
            try {
                JSONObject file = new JSONObject(jsonString);
                JSONObject result = file.getJSONObject("result");
                JSONArray jsonArray = result.getJSONArray("records");
                //JSONObject records = result.getJSONObject("records");
                int num = jsonArray.length();
                //int num = records.length();
                for(int i=0 ; i< num; i++)
                {
                     JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //JSONObject jsonObject = records.getJSONObject(String.valueOf(i));
                    Municipio mun = new Municipio(jsonObject.getInt("_id"),jsonObject.getInt("CodMunicipio"),  jsonObject.getInt("Casos PCR+"), jsonObject.getInt("Casos PCR+ 14 dies"),
                            jsonObject.getInt("Defuncions"), jsonObject.getString("Municipi"), jsonObject.getString("Incidència acumulada PCR+").replaceAll("\\s","").replace(",", "."),
                            jsonObject.getString("Incidència acumulada PCR+14").replaceAll("\\s","").replace(",", "."), jsonObject.getString("Taxa de defunció").replaceAll("\\s",""));
                    municipios.add(mun);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return municipios;
        }

        @Override
        protected void onPostExecute(ArrayList municipios) {
            // Create the RecyclerView
            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            adapter = new AdapterMunicipios(getApplicationContext(), municipios);
            recyclerView.setAdapter(adapter);

            setListener();
        }
    }

    public void setListener(){
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipalities);

        httpConnector = new HTTPConnector();
        httpConnector.execute();

        //Set up the RecyclerView
       /* RecyclerView recyclerView = findViewById(R.id.recyclerview);
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

        adapter.setOnItemClickListener(onItemClickListener);*/

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormActivity.class);
                startActivity(intent);
            }
        });
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
                adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                adapter = new AdapterMunicipios(getApplicationContext(), municipios);
                adapter.ordenCasos();
                recyclerView.setAdapter(adapter);
                setListener();
                return true;
            case R.id.incidencia:
                // Do something when the user clicks on the help item
                recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new AdapterMunicipios(getApplicationContext(), municipios);
                adapter.ordenIncidencia();
                recyclerView.setAdapter(adapter);
                setListener();
                return true;
            case R.id.actualizar:
                // Do something when the user clicks on the help item
                municipios.clear();
                httpConnector = new HTTPConnector();
                httpConnector.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}