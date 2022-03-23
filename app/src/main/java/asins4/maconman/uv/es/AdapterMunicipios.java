package asins4.maconman.uv.es;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdapterMunicipios extends RecyclerView.Adapter<AdapterMunicipios.ViewHolder> implements Filterable {
    private ArrayList<Municipio> municipios, municipiosFiltrado;
    Context context;
    View.OnClickListener mOnItemClickListener;
    public AdapterMunicipios(Context c)
    {
        context=c;
        Init();
    }
    public AdapterMunicipios(Context c, ArrayList<Municipio> mun){
        context = c;
        municipios = mun;
        Init();
    }
    public void Init() {
        // We read the JSON file and fill the “municipios” ArrayList
        //municipios=new ArrayList<Municipio>();
        /*
        InputStream is = context.getResources().openRawResource(R.raw.municipioscv);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */

        municipiosFiltrado = municipios;
    }
    public void ordenCasos() {
        Collections.sort(municipios, new Comparator<Municipio>() {
            @Override
            public int compare(Municipio p1, Municipio p2) {
                return new Float(p2.getCasosPCR14()).compareTo(new Float(p1.getCasosPCR14()));
            }
        });
    }
    public void ordenIncidencia() {
        Collections.sort(municipios, new Comparator<Municipio>() {
            @Override
            public int compare(Municipio p1, Municipio p2) {
                return new Double(p2.getIncidenciaPCR14()).compareTo(new Double(p1.getIncidenciaPCR14()));
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    municipiosFiltrado = municipios;
                } else {
                    ArrayList<Municipio> filteredList = new ArrayList<>();
                    for (Municipio row : municipios) {
                        //cuidado
                        if (row.municipi.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    municipiosFiltrado = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = municipiosFiltrado;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                municipiosFiltrado = (ArrayList<Municipio>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return municipiosFiltrado.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewMunicipio, textViewCasos, textViewIncidencia;
        public ViewHolder(View view) {
            super(view);
            textViewMunicipio = (TextView) view.findViewById(R.id.municipioTV);
            textViewCasos = (TextView) view.findViewById(R.id.pcr14TV);
            textViewIncidencia = (TextView) view.findViewById(R.id.incidencia14);
            view.setTag(this);
        }
        public TextView getTextViewMunicipio() {
            return textViewMunicipio;
        }

        public TextView getTextViewCasos() {
            return textViewCasos;
        }

        public TextView getTextViewIncidencia() {
            return textViewIncidencia;
        }
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listmunicipios, viewGroup, false);
        view.setOnClickListener(mOnItemClickListener);
        return new ViewHolder(view);
    }

    public Municipio getMunPosicion(int posicion){
        return municipiosFiltrado.get(posicion);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getTextViewMunicipio().setText(String.valueOf(municipiosFiltrado.get(position).getMunicipi()));
        holder.getTextViewCasos().setText(String.valueOf(municipiosFiltrado.get(position).getCasosPCR14()));
        holder.getTextViewIncidencia().setText(String.valueOf(municipiosFiltrado.get(position).getIncidenciaPCR14()));
        int pcr14 = municipiosFiltrado.get(position).getCasosPCR14();
        if (pcr14 < 200){
            holder.getTextViewCasos().setTextColor(Color.GREEN);
        }else {
            holder.getTextViewCasos().setTextColor(Color.RED);
        }
        double incidencia14 = Double.parseDouble(municipiosFiltrado.get(position).getIncidenciaPCR14());
        if(incidencia14 > 1000){
            holder.getTextViewIncidencia().setTextColor(Color.RED);
        }else {
            holder.getTextViewIncidencia().setTextColor(Color.GREEN);
        }
    }

}
