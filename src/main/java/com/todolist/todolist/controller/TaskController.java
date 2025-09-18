package com.todolist.todolist.controller;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.model.Priority;
import com.todolist.todolist.service.TaskService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public void addTask(@RequestBody String title) {
        taskService.addTask(title);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}/complete")
    public void completeTask(@PathVariable int id){
        taskService.markAsCompleted(id);
    }
    @PutMapping("/{id}/uncomplete")
    public void uncompleteTask(@PathVariable int id) {
        taskService.unmarkAsCompleted(id);
    }

    @PutMapping("/{id}/edit")
    public void editTask(@PathVariable int id, @RequestBody String title) {
        taskService.editTaskTitle(id, title);
    }

    @PutMapping("/{id}/due-date")
    public void setDueDate(@PathVariable int id, @RequestBody String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        taskService.setDueDate(id, localDate);
    }

    @PutMapping("/{id}/category")
    public void setCategory(@PathVariable int id, @RequestParam String category) {
        taskService.setCategory(id, category);
    }

    @PutMapping("/{id}/priority")
    public void setPriority(@PathVariable int id, @RequestParam String priority) {
        taskService.setPriority(id, Priority.valueOf(priority.toUpperCase()));
    }


}
