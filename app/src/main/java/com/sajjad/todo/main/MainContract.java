package com.sajjad.todo.main;


import com.sajjad.todo.model.Task;

import java.util.List;

public interface MainContract {

    interface View {

        void showTask(List<Task> tasks);

        void clearAllTasks();

        void addTask(Task task);

        void updateTask(Task task);

        void setEmptyStateVisibility(boolean visible);


    }


    interface Presenter {

        void onAttach(View view);

        void onSearch(String q);

        void onItemClick(Task task);

        void onDeleteAllBtnClick();

        void onDetach();

    }
}
