package asins4.maconman.uv.es;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CovidCursorAdapter extends RecyclerView.Adapter<CovidCursorAdapter.ViewHolder> {
    private Cursor items;
    View.OnClickListener mOnItemClickListener;

    public Cursor getCursor(){
        return items;
    }

    public void setCursor(Cursor newCursor){
        items = newCursor;
        notifyDataSetChanged();

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CovidCursorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_covid, parent, false);
        v.setOnClickListener(mOnItemClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCursorAdapter.ViewHolder holder, int position) {
        items.moveToPosition(position);
        int id_code = items.getColumnIndex(CovidContract.CovidEntry.COLUMN_NAME_CODE);
        String code = items.getString(id_code);
        holder.textView.setText(code);
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.getCount() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Referencias UI
        public TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textView);
            v.setTag(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

