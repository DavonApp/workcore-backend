package com.todolist.todolist.service;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.TaskRepository;
import com.todolist.todolist.repository.UserRepository;
import com.todolist.todolist.model.Priority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }  

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void addTask(String title) {
        Task task = new Task(title);
        taskRepository.save(task);
    }

    public void deleteTask(int id) {
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public void markAsCompleted(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.markAsCompleted();
            taskRepository.save(task); // Saves changes to database
        }
    }
    public void unmarkAsCompleted(int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setCompletion(false);
            taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found");
        }
    }
    public void editTaskTitle(int id, String newTitle){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setTitle(newTitle);
            taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found");
        }
    }
    public void setDueDate(int id, LocalDate date){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setDueDate(date);
            taskRepository.save(task);
        }
    }
    public void setCategory(int id, String category){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setCategory(category);
            taskRepository.save(task);
        }
    }
    public void setPriority(int id, Priority priority) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setPriority(priority);
            taskRepository.save(task);
        }
    }
}