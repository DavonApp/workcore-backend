package com.todolist.todolist.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "users") 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Fields
    private int id;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<>();

    // Default Constructor
    public User () {

    }

    // Constructor for registering users
    public User (String email, String password){
        this.email = email;
        this.password = password;
    }

    // Getters
    public int getId (){
        return id;
    }

    public String getEmail (){
        return email;
    }
    public String getPassword (){
        return password;
    }

    public List<Task> getTasks(){
        return tasks;
    }

    // Setters
    public void setId (int id){
        this.id = id;
    }

    public void setEmail (String email){
        this.email = email;
    }

    public void setPassword (String password){
        this.password = password;
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
    }

    @Override
    public String toString(){
        return "User{" + "id=" + id + ", email=" + email + '\'' + '}';
    }
}
