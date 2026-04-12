package com.todolist.todolist.controller;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.service.TaskService;

import jakarta.servlet.http.HttpSession;

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

    // Helper to extract userId from session - throws 401 if not logged in
    private int getUserIdFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) throw new RuntimeException("Not logged in");
        return userId;
    }

    @GetMapping
    public List<Task> getAllTasks(HttpSession session) {
        return taskService.getTasksForUser(getUserIdFromSession(session));
    }
    

    @PostMapping
    public Task addTask(@RequestBody Task task, HttpSession session) {
        return taskService.addTask(task, getUserIdFromSession(session));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id, HttpSession session){
        taskService.deleteTask(id, getUserIdFromSession(session));
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task updatedTask, HttpSession session) {
        taskService.updateTask(id, updatedTask, getUserIdFromSession(session));
    }

}
