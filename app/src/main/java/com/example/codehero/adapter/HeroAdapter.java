package com.example.codehero.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codehero.R;
import com.example.codehero.model.Hero;
import com.example.codehero.util.CircleCrop;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HeroAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final List<Hero> list;

    public HeroAdapter(Context context, List<Hero> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (list.get(position).getId() != null) {
            return Long.parseLong(list.get(position).getId());
        }

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.hero_list_item, null);

            holder = new ViewHolder();

            holder.image = convertView.findViewById(R.id.hero_image);
            holder.name = convertView.findViewById(R.id.hero_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Hero hero = list.get(position);

        Picasso.get().load(hero.getUrl()).resizeDimen(R.dimen.list_image_size, R.dimen.list_image_size).transform(new CircleCrop()).into(holder.image);

        holder.name.setText(hero.getName());

        return convertView;
    }

    static class ViewHolder {
        private ImageView image;
        private TextView name;
    }
}
