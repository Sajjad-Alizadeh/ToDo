package com.sajjad.todo.detail;


import com.sajjad.todo.model.Task;

public interface TaskDetailContract {

    interface View {
        void showTask(Task task);

        void setDeleteBtnVisibility(boolean visible);

        void showError(String error);

        void returnResult(int requestCode, Task task);
    }

    interface Presenter {

        void onAttach(View view);

        void onSaveChangeClick(int importance, String title);

        void deleteTask();

        void onDetach();

    }
}
