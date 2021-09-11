package com.example.codehero.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.codehero.R;
import com.example.codehero.adapter.HeroAdapter;
import com.example.codehero.connection.RetrofitHelper;
import com.example.codehero.model.CallResponse;
import com.example.codehero.model.Hero;
import com.example.codehero.model.ItemEvents;
import com.example.codehero.model.ItemSeries;
import com.example.codehero.util.Keys;
import com.example.codehero.util.Util;
import com.google.android.material.button.MaterialButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById
    EditText inputName;

    @ViewById
    ListView list;

    @ViewById
    MaterialButton btnPreviousPage;

    @ViewById
    MaterialButton btnNextPage;

    @ViewById
    MaterialButton btnPage1;

    @ViewById
    MaterialButton btnPage2;

    @ViewById
    MaterialButton btnPage3;

    private final int PAGE_START = 1;
    private final int PAGE_SIZE = 4;
    private int TOTAL_PAGES = 0;
    private long OFFSET = 0;
    private int currentPage;

    private List<Hero> heroList;
    private HeroAdapter heroAdapter;
    private String nameSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentPage = PAGE_START;
    }

    @AfterViews
    void init() {
        resetPagination();
        loadHeroes();
    }

    @TextChange(R.id.input_name)
    void textChangeName() {
        if (!inputName.getText().toString().isEmpty() && inputName.getText() != null) {
            this.nameSearch = inputName.getText().toString();
        } else {
            this.nameSearch = null;
        }

        resetPagination();
        loadHeroes();
    }

    @ItemClick(R.id.list)
    void clickList(Hero selectedHero) {
        Intent intent = new Intent();

        intent.setClass(MainActivity.this, HeroDetailsActivity_.class);
        intent.putExtra("hero", selectedHero);

        startActivity(intent);
    }

    @Click(R.id.btn_page1)
    void clickPage1() {
        if (currentPage == TOTAL_PAGES) {
            goToPage("PREVIOUS", 2);
        } else {
            clickPreviousPage();
        }
    }

    @Click(R.id.btn_page2)
    void clickPage2() {
        if (currentPage == PAGE_START) {
            clickNextPage();
        } else if (currentPage == TOTAL_PAGES) {
            clickPreviousPage();
        }
    }

    @Click(R.id.btn_page3)
    void clickPage3() {
        if (currentPage == PAGE_START) {
            goToPage("NEXT", 2);
        } else {
            clickNextPage();
        }
    }

    @Click(R.id.btn_previous_page)
    void clickPreviousPage() {
        goToPage("PREVIOUS", 1);
    }

    @Click(R.id.btn_next_page)
    void clickNextPage() {
        goToPage("NEXT", 1);
    }

    private void goToPage(String direction, long page) {
        switch (direction) {
            case "PREVIOUS":
                if (this.currentPage > PAGE_START) {
                    this.OFFSET -= (page * PAGE_SIZE);
                    this.currentPage -= page;
                }

                break;
            case "NEXT":
                if (this.currentPage < TOTAL_PAGES) {
                    this.OFFSET += (page * PAGE_SIZE);
                    this.currentPage += page;
                }

                break;
            default:
                break;
        }

        loadHeroes();
    }

    private void loadHeroes() {
        long timestamp = Calendar.getInstance().getTimeInMillis();

        Call<CallResponse> call;

        if (nameSearch != null) {
            call = RetrofitHelper.getInstance().getMarvelService().findByName(timestamp, nameSearch, OFFSET, PAGE_SIZE, Keys.PUBLIC_KEY, Util.createHash(timestamp));
        } else {
            call = RetrofitHelper.getInstance().getMarvelService().findAll(timestamp, OFFSET, PAGE_SIZE, Keys.PUBLIC_KEY, Util.createHash(timestamp));
        }

        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                heroList = new ArrayList<>();

                if (response.body().getData() != null) {
                    int total = Integer.parseInt(response.body().getData().getTotal());

                    if (total <= 4) {
                        TOTAL_PAGES = 1;
                    } else {
                        TOTAL_PAGES = total / PAGE_SIZE;
                    }

                    int count = Integer.parseInt(response.body().getData().getCount());

                    for (int i = 0; i < count; i++) {
                        String id = response.body().getData().getResults().get(i).getId();
                        String name = response.body().getData().getResults().get(i).getName();
                        String description = response.body().getData().getResults().get(i).getDescription();
                        String thumbnail_url = response.body().getData().getResults().get(i).getThumbnail().getPath() + "." + response.body().getData().getResults().get(i).getThumbnail().getExtension();
                        List<ItemSeries> series = response.body().getData().getResults().get(i).getSeries().getItems();
                        List<ItemEvents> events = response.body().getData().getResults().get(i).getEvents().getItems();

                        Hero hero = new Hero(id, name, description, thumbnail_url, series, events);

                        heroList.add(hero);
                    }
                }

                heroAdapter = new HeroAdapter(MainActivity.this, heroList);

                list.setAdapter(heroAdapter);
                checkPaginationButtons();
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void resetPagination() {
        this.currentPage = PAGE_START;
        this.OFFSET = 0;

        resetPaginationButtons();
    }

    private void resetPaginationButtons() {
        btnPage1.setText(String.valueOf(PAGE_START));
        setButtonActive(btnPage1, true);

        btnPage2.setText(String.valueOf(PAGE_START + 1));
        btnPage2.setVisibility(View.VISIBLE);
        setButtonActive(btnPage2, false);

        btnPage3.setText(String.valueOf(PAGE_START + 2));
        btnPage3.setVisibility(View.VISIBLE);
        setButtonActive(btnPage3, false);
    }

    private void checkPaginationButtons() {
        btnPreviousPage.setClickable(this.currentPage != PAGE_START);
        btnNextPage.setClickable(this.currentPage != TOTAL_PAGES);

        if (TOTAL_PAGES >= 3) {
            if (this.currentPage == PAGE_START) {
                resetPaginationButtons();
            } else if (this.currentPage == TOTAL_PAGES) {
                btnPage1.setText(String.valueOf(currentPage - 2));
                setButtonActive(btnPage1, false);
                btnPage2.setText(String.valueOf(currentPage - 1));
                setButtonActive(btnPage2, false);
                btnPage3.setText(String.valueOf(currentPage));
                setButtonActive(btnPage3, true);
            } else {
                btnPage1.setText(String.valueOf(currentPage - 1));
                setButtonActive(btnPage1, false);
                btnPage2.setText(String.valueOf(currentPage));
                setButtonActive(btnPage2, true);
                btnPage3.setText(String.valueOf(currentPage + 1));
                setButtonActive(btnPage3, false);
            }
        } else if (TOTAL_PAGES == 2) {
            btnPage3.setText(String.valueOf(PAGE_START + 2));
            btnPage3.setVisibility(View.GONE);

            if (this.currentPage == PAGE_START) {
                btnPage1.setText(String.valueOf(PAGE_START));
                setButtonActive(btnPage1, true);
                btnPage2.setText(String.valueOf(PAGE_START + 1));
                setButtonActive(btnPage2, false);
            } else {
                btnPage1.setText(String.valueOf(PAGE_START));
                setButtonActive(btnPage1, false);
                btnPage2.setText(String.valueOf(PAGE_START + 1));
                setButtonActive(btnPage2, true);
            }
        } else if (TOTAL_PAGES == 1) {
            btnPage1.setText(String.valueOf(PAGE_START));
            setButtonActive(btnPage1, true);
            btnPage2.setText(String.valueOf(PAGE_START + 1));
            btnPage2.setVisibility(View.GONE);
            btnPage3.setText(String.valueOf(PAGE_START + 2));
            btnPage3.setVisibility(View.GONE);
        }
    }

    private void setButtonActive(MaterialButton button, boolean active) {
        button.setActivated(active);
        button.setClickable(!active);

        if (active) {
            button.setTextColor(getColor(R.color.white));
        } else {
            button.setTextColor(getColor(R.color.marvel_red));
        }
    }
}