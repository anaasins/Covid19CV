package asins4.maconman.uv.es;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

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