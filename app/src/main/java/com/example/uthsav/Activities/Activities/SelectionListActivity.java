package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.uthsav.Activities.Adapter.SelectionListAdapter;
import com.example.uthsav.R;

public class SelectionListActivity extends AppCompatActivity {

    TextView selectionList;
    RecyclerView selectionListRecyclerView;
    SelectionListAdapter selectionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_list);

        bindViews();

        selectionListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectionListAdapter = new SelectionListAdapter(this);
        selectionListRecyclerView.setAdapter(selectionListAdapter);
    }

    private void bindViews()
    {
        selectionList = findViewById(R.id.selectionList_textView);
        selectionListRecyclerView = findViewById(R.id.selectionList_recyclerView);
    }
}