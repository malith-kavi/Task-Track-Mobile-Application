package com.example.ticktask;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private LinearLayout tasksLayout;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        tasksLayout = rootView.findViewById(R.id.taskList);
        FloatingActionButton addButton = rootView.findViewById(R.id.btn_add);
        addButton.setOnClickListener(v -> openAddTaskDialog());

        sharedPreferences = getActivity().getSharedPreferences("TaskTrack", Context.MODE_PRIVATE);
        loadTasksFromSharedPreferences();
        return rootView;
    }

    private void openAddTaskDialog() {
        Dialog dialog = createDialog(R.layout.dialog_add);
        Button addButton = dialog.findViewById(R.id.button_add);
        Button cancelButton = dialog.findViewById(R.id.button_cancel);
        EditText taskEditText = dialog.findViewById(R.id.task);

        addButton.setOnClickListener(v -> {
            String taskText = taskEditText.getText().toString().trim();
            if (!taskText.isEmpty()) {
                addTask(taskText);
                saveTasksToSharedPreferences();
                dialog.dismiss();
                Toast.makeText(getContext(), "Task Added Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void openDeleteDialog(View taskView) {
        Dialog dialog = createDialog(R.layout.dialog_delete);
        Button yesButton = dialog.findViewById(R.id.button_yes);
        Button noButton = dialog.findViewById(R.id.button_no);

        yesButton.setOnClickListener(v -> {
            tasksLayout.removeView(taskView);
            saveTasksToSharedPreferences();
            dialog.dismiss();
            Toast.makeText(getContext(), "Task Deleted Successfully", Toast.LENGTH_SHORT).show();
        });

        noButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void openEditDialog(View taskView, TextView taskTextView) {
        Dialog dialog = createDialog(R.layout.dialog_edit_item); // Corrected layout here
        Button updateButton = dialog.findViewById(R.id.button_yes);
        Button cancelButton = dialog.findViewById(R.id.button_no);
        EditText taskEditText = dialog.findViewById(R.id.task);

        taskEditText.setText(taskTextView.getText().toString());

        updateButton.setOnClickListener(v -> {
            String updatedTask = taskEditText.getText().toString().trim();
            taskTextView.setText(updatedTask);
            saveTasksToSharedPreferences();
            dialog.dismiss();
            Toast.makeText(getContext(), "Task Edited Successfully", Toast.LENGTH_SHORT).show();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private Dialog createDialog(int layoutResId) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(layoutResId);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    private void addTask(String taskText) {
        View taskView = LayoutInflater.from(getActivity()).inflate(R.layout.card, null);
        TextView taskTextView = taskView.findViewById(R.id.textView21);
        ImageView deleteImageView = taskView.findViewById(R.id.imageView3);
        ImageView editImageView = taskView.findViewById(R.id.imageView2);

        taskTextView.setText(taskText);
        deleteImageView.setOnClickListener(v -> openDeleteDialog(taskView));
        editImageView.setOnClickListener(v -> openEditDialog(taskView, taskTextView));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 40);
        taskView.setLayoutParams(layoutParams);

        tasksLayout.addView(taskView);
    }

    private void saveTasksToSharedPreferences() {
        StringBuilder tasksStringBuilder = new StringBuilder();
        for (int i = 0; i < tasksLayout.getChildCount(); i++) {
            TextView taskTextView = tasksLayout.getChildAt(i).findViewById(R.id.textView21);
            String taskText = taskTextView.getText().toString();
            tasksStringBuilder.append(taskText);
            if (i < tasksLayout.getChildCount() - 1) {
                tasksStringBuilder.append(",");
            }
        }
        String tasksString = tasksStringBuilder.toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tasks", tasksString);
        editor.apply();
    }

    private void loadTasksFromSharedPreferences() {
        String tasksString = sharedPreferences.getString("tasks", "");
        if (!tasksString.isEmpty()) {
            String[] tasksArray = tasksString.split(",");
            for (String task : tasksArray) {
                addTask(task);
            }
        }
    }
}
