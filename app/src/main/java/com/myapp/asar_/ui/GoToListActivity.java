package com.myapp.asar_.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.asar_.MainActivity;
import com.myapp.asar_.R;
import com.myapp.asar_.adapter.GoToListAdapter;
import com.myapp.asar_.database.entities.Places;
import com.myapp.asar_.helper.AppPreferenceHelper;
import com.myapp.asar_.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoToListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.notFoundLayout)
    LinearLayout notFoundLayout;

    AppViewModel viewModel;

    AppPreferenceHelper appPreferenceHelper;

    int currentUserID;

    GoToListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_list);
        ButterKnife.bind(this);

        initUI();
    }

    private void initUI() {
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appPreferenceHelper = AppPreferenceHelper.getInstance(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        currentUserID = appPreferenceHelper.getCurrentUserID();

        viewModel.getAllPlaces().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
                List<Places> myGoToList = new ArrayList<>();

                for(Places places1: places){
                    if(places1.isAddedToGoList() && places1.getUserID() == currentUserID){
                        myGoToList.add(places1);
                    }
                }

                if(myGoToList.size() ==0){
                    notFoundLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    return;
                }
                adapter = new GoToListAdapter(myGoToList,getApplicationContext());
                recyclerView.setAdapter(adapter);

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GoToListActivity.this, MainActivity.class));
    }
}