package com.todolist.todolist.model;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDateTime;
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
    private LocalDateTime passwordLastChanged;
    private String name;
    private String provider; // "LOCAL" or "GOOGLE"
    private String providerId; // Google's unique ID for the user

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<>();

    // Default Constructor
    public User () {
        this.provider = "LOCAL";
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

    public LocalDateTime getPasswordLastChanged() {
        return passwordLastChanged;
    }

    public String getName () {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
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

    public void setPasswordLastChanged (LocalDateTime date) {
        this.passwordLastChanged = date;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setTasks (List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setProvider (String provider) {
        this.provider = provider;
    }

    public void setProviderId (String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString(){
        return "User{" + "id=" + id + ", email=" + email + ", name=" + name + '}';
    }
}
