package com.example.codehero.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codehero.R;
import com.example.codehero.model.ItemEvents;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ItemEvents> list;

    public EventsAdapter(List<ItemEvents> list) {
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
        ItemEvents events = list.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;

        viewHolder.name.setText(events.getName());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }

        return 0;
    }
}
