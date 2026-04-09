package com.todolist.todolist.service;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.TaskRepository;
import com.todolist.todolist.repository.UserRepository;
import com.todolist.todolist.model.Priority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // Constructor Injection
    TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }  

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskByID(int id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public void addTask(Task task) {
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
    public void setDueTime(int id, LocalTime time){
    Optional<Task> optionalTask = taskRepository.findById(id);
    if(optionalTask.isPresent()){
        Task task = optionalTask.get();
        task.setDueTime(time);
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
    public void setDescription(int id, String description){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setDescription(description);
            taskRepository.save(task);
        }
    }
    public void addTaskForUser(String title, int userId ){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Task task = new Task(title);
        task.setUser(user);
        taskRepository.save(task);
    }

    public void updateTask(int id, Task updatedTask){
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()){
            Task existingTask = optionalTask.get();

            if(updatedTask.getTitle() != null){
                existingTask.setTitle(updatedTask.getTitle());
            }
            if(updatedTask.getDescription() != null){
                existingTask.setDescription(updatedTask.getDescription());
            }
            if(updatedTask.getCategory() != null){
                existingTask.setCategory(updatedTask.getCategory());
            }
            if(updatedTask.getPriority() != null){
                existingTask.setPriority(Priority.valueOf(updatedTask.getPriority().name().toUpperCase()));
            }
            if(updatedTask.getDueDate() != null){
                existingTask.setDueDate(updatedTask.getDueDate());
            }
            if(updatedTask.getDueTime() != null){
                existingTask.setDueTime(updatedTask.getDueTime());
            }

            existingTask.setCompletion(updatedTask.getIsCompleted());

            taskRepository.save(existingTask);
        }
    }
}