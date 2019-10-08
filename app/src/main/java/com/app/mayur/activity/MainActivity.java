package com.app.mayur.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.mayur.R;
import com.app.mayur.adapter.SpeciesAdapter;
import com.app.mayur.pojo.Species;
import com.app.mayur.apis.ResultInterface;
import com.app.mayur.apis.WebManager;
import com.app.mayur.retrofit.LogEx;

import java.util.ArrayList;
import java.util.List;

import ru.alexbykov.nopaginate.callback.OnLoadMoreListener;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

public class MainActivity extends AppCompatActivity {

    SpeciesAdapter speciesAdapter;
    List<Species.Result> resultsList;
    RecyclerView recyclerView;

    SwipeRefreshLayout swipeContainer;
    NoPaginate noPaginate;

    int page = 1;
    int previousCount = 0;
    boolean firstTimeLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        fetchData();
        pullToRefresher();
    }

    private void findViews() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        recyclerView = (RecyclerView) findViewById(R.id.rvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resultsList = new ArrayList<>();
        speciesAdapter = new SpeciesAdapter(resultsList, R.layout.item_test,
                getApplicationContext());
        recyclerView.setAdapter(speciesAdapter);
    }

    private void fetchData() {
        fetchData(-1);
    }

    private void fetchData(int specificPage) {

        WebManager webManager = new WebManager(this);

        final int finalSpecificPage = specificPage;

        if (specificPage == -1) {
            specificPage = page;
        }

        webManager.getDataFromApi(specificPage, new ResultInterface() {
            @Override
            public void onResults(List<Species.Result> resultsList, int count) {
                dismissSwiper();

                if (finalSpecificPage != -1) {

                    // pull to refresh
                    setNewItemsAtBeginnings(resultsList, count);

                } else {
                    previousCount = count;
                    setAdapter(resultsList);
                }

                if (firstTimeLoading) {
                    firstTimeLoading = false;
                    setLoadMorePaginate();
                }

            }

            @Override
            public void onMessage(String message) {
                dismissSwiper();
                dismissPaginate();
            }
        });

    }

    private void pullToRefresher() {

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData(1);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_red_light,
                android.R.color.holo_red_light,
                android.R.color.holo_red_light);
    }

    private void dismissSwiper() {
        if (swipeContainer != null) {
            if (swipeContainer.isRefreshing())
                swipeContainer.setRefreshing(false);
        }
    }

    private void dismissPaginate() {
        if (noPaginate != null) {
            noPaginate.showLoading(false);
            noPaginate.setNoMoreItems(true);
        }
    }

    private void setLoadMorePaginate() {
        noPaginate = NoPaginate.with(recyclerView)
                .setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        page += 1;
                        fetchData();
                    }
                }).build();
    }

    private void setAdapter(List<Species.Result> resultsList) {
        int start = this.resultsList.size();
        this.resultsList.addAll(resultsList);
        int itemCount = this.resultsList.size();
        speciesAdapter.notifyItemRangeInserted(start, itemCount);
    }

    private void setNewItemsAtBeginnings(List<Species.Result> resultsList, int count) {

        try {

            // we count the new items
            if (previousCount != count) {
                int itemsToAdd = count - previousCount;

                // we only add the new items to top of the list
                for (int i = 0; i < itemsToAdd; i++) {
                    this.resultsList.add(i, resultsList.get(i));
                }

            }

            previousCount = count;
            speciesAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            LogEx.log(e);
        }

    }

}
