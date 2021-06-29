package com.sajjad.todo.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.sajjad.todo.R;
import com.sajjad.todo.detail.TaskDetailActivity;
import com.sajjad.todo.model.AppDatabase;
import com.sajjad.todo.model.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, TaskAdapter.TaskItemEventListener {

    private static final int REQUEST_CODE = 372;
    public static final String EXTRA_KEY_TASK = "task";
    public static final int RESULT_CODE_ADD_TASK = 1001;
    public static final int RESULT_CODE_DELETE_TASK = 1002;
    public static final int RESULT_CODE_UPDATE_TASK = 1003;

    private View emptyState;


    private EditText search_edt;
    private Button deleteAllTasks_btn;
    private Button addNewTask_btn;

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    private MainContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupViews();

        presenter = new MainPresenter(AppDatabase.getAppDatabase(this).getTaskDao());

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);


        addNewTask_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, TaskDetailActivity.class), REQUEST_CODE);
            }
        });

        deleteAllTasks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDeleteAllBtnClick();
            }
        });


        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        presenter.onAttach(this);


    }


    private void setupViews() {
        search_edt = findViewById(R.id.searchEt);
        deleteAllTasks_btn = findViewById(R.id.deleteAllBtn);
        addNewTask_btn = findViewById(R.id.addNewTaskBtn);
        recyclerView = findViewById(R.id.taskListRv);
        emptyState = findViewById(R.id.emptyState);
        taskAdapter = new TaskAdapter(this, this);
    }


    @Override
    public void showTask(List<Task> tasks) {
        taskAdapter.setTasks(tasks);
    }

    @Override
    public void clearAllTasks() {
        taskAdapter.clearItems();
    }

    @Override
    public void addTask(Task task) {
    }

    @Override
    public void updateTask(Task task) {
        taskAdapter.updateItem(task);
    }

    @Override
    public void setEmptyStateVisibility(boolean visible) {
        emptyState.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if ((resultCode == RESULT_CODE_ADD_TASK || resultCode == RESULT_CODE_UPDATE_TASK || resultCode == RESULT_CODE_DELETE_TASK) && data != null) {
                Task task = data.getParcelableExtra(EXTRA_KEY_TASK);
                if (task != null) {
                    switch (resultCode) {
                        case RESULT_CODE_ADD_TASK: {
                            taskAdapter.addItem(task);
                            setEmptyStateVisibility(false);
                            break;
                        }
                        case RESULT_CODE_DELETE_TASK: {
                            taskAdapter.deleteItem(task);
                        }
                        case RESULT_CODE_UPDATE_TASK: {
                            taskAdapter.updateItem(task);
                        }

                    }
                    setEmptyStateVisibility(taskAdapter.getItemCount() == 0);
                }
            }
        }
    }
    //for Recycler View
    @Override
    public void onClick(Task task) {
        presenter.onItemClick(task);
    }

    @Override
    public void onLongClick(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
        intent.putExtra(EXTRA_KEY_TASK, task);
        startActivityForResult(intent, REQUEST_CODE);
    }

}
