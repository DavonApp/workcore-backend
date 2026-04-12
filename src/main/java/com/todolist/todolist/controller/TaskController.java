package com.todolist.todolist.controller;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.service.TaskService;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks(@RequestParam int userId) {
        return taskService.getTasksForUser(userId);
    }
    

    @PostMapping
    public Task addTask(@RequestBody Task task, @RequestParam int userId) {
        return taskService.addTask(task, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id, @RequestParam int userId){
        taskService.deleteTask(id, userId);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task updatedTask, @RequestParam int userId) {
        taskService.updateTask(id, updatedTask, userId);
    }

}
