package com.todolist.todolist.controller;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.dto.PriorityRequest;
import com.todolist.todolist.model.Priority;
import com.todolist.todolist.service.TaskService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "http://localhost:5500")
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
    
    @GetMapping("/{id}")
    public Task getTaskByID(@PathVariable int id) {
        return taskService.getTaskByID(id);
    }
    

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return task;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        taskService.updateTask(id, updatedTask);
    }

}
