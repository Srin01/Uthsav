package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.uthsav.Activities.Drivers.EventDriver;
import com.example.uthsav.Activities.Expert.EventExpert;
import com.example.uthsav.Activities.Modal.Event;
import com.example.uthsav.R;

import java.util.ArrayList;

public class SearchEventActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;

    private ArrayList<String> eventNames = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    EventExpert eventExpert = EventExpert.getInstance();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        bindViews();

        for(int i = 0; i < eventExpert.getTotalEvents(); i++)
        {
            eventNames.add(eventExpert.getEventOfPosition(i).getEventName());
        }

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, eventNames);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void bindViews()
    {
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.event_list_view);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }

    public void onClickToolBar(View view)
    {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void onClickHomeScreen(View view)
    {


            startActivity(new Intent(this, HomeActivity.class));
    }
}