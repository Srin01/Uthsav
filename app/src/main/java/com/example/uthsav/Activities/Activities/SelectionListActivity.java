package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.uthsav.Activities.Adapter.SelectionListAdapter;
import com.example.uthsav.Activities.Expert.SelectedEventsExpert;
import com.example.uthsav.R;

public class SelectionListActivity extends AppCompatActivity implements SelectionListAdapter.OnEventClickListener {

    public static final String EVENT_ID = "eventId";
    TextView selectionList;
    RecyclerView selectionListRecyclerView;
    SelectionListAdapter selectionListAdapter;
    SelectedEventsExpert selectedEventsExpert;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_list);

        bindViews();

        selectionListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectionListAdapter = new SelectionListAdapter(this,this);
        selectionListRecyclerView.setAdapter(selectionListAdapter);
    }

    private void bindViews()
    {
        selectedEventsExpert = SelectedEventsExpert.getInstance();
        selectionList = findViewById(R.id.selectionList_textView);
        selectionListRecyclerView = findViewById(R.id.selectionList_recyclerView);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onEventClick(int position) {
        Log.d("myTag", "onEventClick: clicked on book " + position);
        Intent intent = new Intent(this, SelectedStudentsListActivity.class);
        intent.putExtra(EVENT_ID,selectedEventsExpert.getEventOfPosition(position).getEventId());
        startActivity(intent);
    }

    public void onClickToolBar(View view)
    {
        startActivity(new Intent(this, HomeActivity.class));
    }
}