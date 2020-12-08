package com.example.uthsav.Activities.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.uthsav.Activities.Adapter.SelectedStudentListAdapter;
import com.example.uthsav.Activities.Adapter.SelectionListAdapter;
import com.example.uthsav.R;

import static com.example.uthsav.Activities.Activities.SelectionListActivity.EVENT_ID;

public class SelectedStudentsListActivity extends AppCompatActivity {

    private TextView eventName;
    String eventId;
    private RecyclerView selectedStudentListRecyclerView;
    SelectedStudentListAdapter selectedStudentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_students_list);

        bindViews();

        selectedStudentListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedStudentListAdapter = new SelectedStudentListAdapter(this,eventId);
        selectedStudentListRecyclerView.setAdapter(selectedStudentListAdapter);
    }

    private void bindViews()
    {
        eventId = getIntent().getStringExtra(EVENT_ID);
        eventName = findViewById(R.id.selectedStudentsList_eventName);
        selectedStudentListRecyclerView = findViewById(R.id.selectedStudentsList_recyclerView);
    }
}