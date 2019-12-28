package com.jabari.marketer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jabari.marketer.R;
import com.jabari.marketer.adapter.RegisteredMemAdapter;
import com.jabari.marketer.controller.RegisterController;
import com.jabari.marketer.network.config.ApiInterface;

import java.util.List;

public class RegisteredMarketerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RegisteredMemAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_marketer);

        getList();
    }

    private void setUpRecycler(List<String> list){

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.registered_marketer_recycler);
        adapter = new RegisteredMemAdapter(this,recyclerView,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }
    private void getList(){

        RegisterController registerController = new RegisterController(new ApiInterface.Register_MarketerCallback() {
            @Override
            public void onResponse(String message) {


            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
