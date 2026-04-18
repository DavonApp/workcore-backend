package com.todolist.todolist.controller;

import com.todolist.todolist.config.AuthHelper;
import com.todolist.todolist.model.Task;
import com.todolist.todolist.model.User;
import com.todolist.todolist.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    private AuthHelper authHelper;

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
    public List<Task> getAllTasks(HttpServletRequest request, HttpSession session) {
        User user = authHelper.getCurrentUser(request, session);
        if (user == null) {
            throw new RuntimeException("Not logged in");
        }
        return taskService.getTasksForUser(user.getId());
    }
    

    @PostMapping
    public Task addTask(@RequestBody Task task, HttpServletRequest request, HttpSession session) {
        User user = authHelper.getCurrentUser(request, session);
        if (user == null) {
            throw new RuntimeException("Not logged in");
        }
        return taskService.addTask(task, user.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id,  HttpServletRequest request, HttpSession session){
        User user = authHelper.getCurrentUser(request, session);
        if (user == null) {
            throw new RuntimeException("Not logged in");
        }
        taskService.deleteTask(id, getUserIdFromSession(session));
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task updatedTask,
                           HttpServletRequest request, HttpSession session) {
        User user = authHelper.getCurrentUser(request, session);
        if (user == null){

            throw new RuntimeException("Not logged in");
        }
        taskService.updateTask(id, updatedTask, user.getId());
    }
}
