package com.example.codehero.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codehero.R;
import com.example.codehero.model.ItemSeries;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ItemSeries> list;

    public SeriesAdapter(List<ItemSeries> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemSeries series = list.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;

        viewHolder.name.setText(series.getName());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }

        return 0;
    }
}
