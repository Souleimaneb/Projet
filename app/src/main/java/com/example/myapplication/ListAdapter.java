package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Film> values;


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;
        TextView txtFooter;
        View layout;
        ImageView img;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            img = v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Film item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public ListAdapter(List<Film> myDataset) {
        values = myDataset;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Film currentFilm = values.get(position);
        holder.txtHeader.setText(currentFilm.getTitle());
        holder.txtFooter.setText(currentFilm.getRelease_date());

        }

    @Override
    public int getItemCount() {
        return values.size();
    }



    }

    // Return the size of your dataset (invoked by the layout manager)
//    @Override
  //  public int getItemCount() {
  //       = values.size();
   //     return size;
  //  }

//}