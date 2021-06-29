package com.sajjad.todo.detail;


import com.sajjad.todo.main.MainActivity;
import com.sajjad.todo.model.Task;
import com.sajjad.todo.model.TaskDao;

public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    private TaskDetailContract.View view;
    private TaskDao taskDao;
    private Task task;

    public TaskDetailPresenter(TaskDao taskDao, Task task) {
        this.taskDao = taskDao;
        this.task = task;
    }

    @Override
    public void onAttach(TaskDetailContract.View view) {
        this.view = view;

        if (task != null) {
            view.setDeleteBtnVisibility(true);
            view.showTask(task);
        }

    }

    @Override
    public void onSaveChangeClick(int importance, String title) {

        if (!title.isEmpty()){
            view.showError("plz enter title...");
        }

        if (task != null) {
            task.setImportance(importance);
            task.setTitle(title);
            int result = taskDao.update(task);
            if (result > 0) {
                view.returnResult(MainActivity.RESULT_CODE_UPDATE_TASK, task);
            }
        } else {

            Task task = new Task();
            task.setTitle(title);
            task.setImportance(importance);

            long id = taskDao.add(task);
            task.setId(id);

            view.returnResult(MainActivity.RESULT_CODE_ADD_TASK, task);

        }
    }


    @Override
    public void deleteTask() {
        if (taskDao != null) {
            int result = taskDao.delete(task);
            if (result > 0) {
                view.returnResult(MainActivity.RESULT_CODE_DELETE_TASK, task);
            }
        }
    }

    @Override
    public void onDetach() {

    }
}
