package com.sajjad.todo.main;


import com.sajjad.todo.model.Task;
import com.sajjad.todo.model.TaskDao;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private TaskDao taskDao;
    private List<Task> tasks;

    public MainPresenter(TaskDao taskDao) {
        this.taskDao = taskDao;
        tasks = taskDao.getAll();
    }

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;

        if (!tasks.isEmpty()) {
            view.showTask(tasks);
            view.setEmptyStateVisibility(false);
        } else {
            view.setEmptyStateVisibility(true);
        }

    }


    @Override
    public void onSearch(String q) {
        List<Task> tasks = taskDao.search(q);
        view.showTask(tasks);
    }

    @Override
    public void onItemClick(Task task) {
       task.setCompleted(!task.isCompleted());

       int result = taskDao.update(task);
       if (result>0){
           view.updateTask(task);
       }
    }

    @Override
    public void onDeleteAllBtnClick() {
        taskDao.deleteAll();

        view.clearAllTasks();
        view.setEmptyStateVisibility(true);

    }

    @Override
    public void onDetach() {

    }
}
