package com.sajjad.todo.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.sajjad.todo.R;
import com.sajjad.todo.main.MainActivity;
import com.sajjad.todo.model.AppDatabase;
import com.sajjad.todo.model.Task;


public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View {

    private int selectedImportance = Task.IMPORTANCE_NORMAL;
    private ImageView lastSelectedImportanceIv;

    private TaskDetailContract.Presenter presenter;

    private Button saveChangeBtn;
    private EditText title;
    private View deleteTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        presenter = new TaskDetailPresenter(AppDatabase.getAppDatabase(this).getTaskDao(), (Task) getIntent().getParcelableExtra(MainActivity.EXTRA_KEY_TASK));

        setupViews();

        presenter.onAttach(this);


        View backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View normalImportanceBtn = findViewById(R.id.normalImportanceBtn);
        lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.normalImportanceCheckIv);

        View highImportanceBtn = findViewById(R.id.highImportanceBtn);
        highImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_HIGH) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.highImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_HIGH;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });

        View lowImportanceBtn = findViewById(R.id.lowImportanceBtn);
        lowImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_LOW) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.lowImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_LOW;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });

        normalImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_NORMAL) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.normalImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_NORMAL;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });


        saveChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSaveChangeClick(selectedImportance, title.getText().toString());
            }
        });

        deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteTask();
            }
        });


    }

    private void setupViews() {
        deleteTaskBtn = findViewById(R.id.deleteTaskBtn);
        saveChangeBtn = findViewById(R.id.saveChangesBtn);
        title = findViewById(R.id.taskEt);
    }

    @Override
    public void showTask(Task task) {
        title.setText(task.getTitle());

        switch (task.getImportance()) {

            case Task.IMPORTANCE_NORMAL:
                    findViewById(R.id.normalImportanceBtn).performClick();
                break;

            case Task.IMPORTANCE_LOW:
                findViewById(R.id.lowImportanceBtn).performClick();
                break;

            case Task.IMPORTANCE_HIGH:
                findViewById(R.id.highImportanceBtn).performClick();
                break;

        }

    }

    @Override
    public void setDeleteBtnVisibility(boolean visible) {
        if (visible) {
            deleteTaskBtn.setVisibility(View.VISIBLE);
        } else
            deleteTaskBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void returnResult(int requestCode, Task task) {

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_KEY_TASK, task);
        setResult(requestCode, intent);
        finish();
    }


}
