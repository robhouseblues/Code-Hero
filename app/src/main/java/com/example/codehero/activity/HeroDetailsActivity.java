package com.example.codehero.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codehero.R;
import com.example.codehero.adapter.EventsAdapter;
import com.example.codehero.adapter.SeriesAdapter;
import com.example.codehero.model.Hero;
import com.example.codehero.util.CircleCrop;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_hero_details)
public class HeroDetailsActivity extends AppCompatActivity {
    @ViewById
    ImageView heroImage;

    @ViewById
    TextView heroName;

    @ViewById
    TextView heroDescription;

    @ViewById
    RecyclerView list;

    @ViewById
    TabItem tabSeries;

    @ViewById
    TabItem tabEvents;

    @ViewById
    TabLayout tabs;

    @Extra
    Hero hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
        Picasso.get().load(hero.getUrl()).resizeDimen(R.dimen.list_image_size, R.dimen.list_image_size).transform(new CircleCrop()).into(heroImage);

        heroName.setText(hero.getName());

        if (hero.getDescription().isEmpty()) {
            heroDescription.setText(R.string.nao_ha_descricao);
        } else {
            heroDescription.setText(hero.getDescription());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HeroDetailsActivity.this, LinearLayoutManager.VERTICAL, false);

        list.setLayoutManager(linearLayoutManager);
        list.setNestedScrollingEnabled(false);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getText().toString()) {
                    case "Descrição":
                        heroDescription.setVisibility(View.VISIBLE);
                        list.setVisibility(View.GONE);
                        break;
                    case "Séries":
                        loadList("SERIES");
                        list.setVisibility(View.VISIBLE);
                        heroDescription.setVisibility(View.GONE);
                        break;
                    case "Eventos":
                        loadList("EVENTS");
                        list.setVisibility(View.VISIBLE);
                        heroDescription.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadList(String type) {
        switch (type) {
            case "SERIES":
                SeriesAdapter seriesAdapter = new SeriesAdapter(hero.getSeries());
                list.setAdapter(seriesAdapter);
                break;

            case "EVENTS":
                EventsAdapter eventsAdapter = new EventsAdapter(hero.getEvents());
                list.setAdapter(eventsAdapter);
                break;

            default:
                break;
        }
    }
}