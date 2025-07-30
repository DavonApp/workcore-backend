package com.todolist.todolist.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)



    // Fields
    private int id;

    private String title;
    private boolean isCompleted;
    private LocalDate dueDate;
    private String category;
    private Priority priority;
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    // Constructor
    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
        this.priority = Priority.MEDIUM;
    }

    // Default constructor 
    public Task() {}

    // Getters and Setters
    public int getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean getIsCompleted() { return isCompleted; }
    public void setCompletion(boolean completed) { this.isCompleted = completed; }
    public void markAsCompleted() { this.isCompleted = true; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public User getUser(){ return user; }
    public void setUser(User user){ this.user = user; }
}
