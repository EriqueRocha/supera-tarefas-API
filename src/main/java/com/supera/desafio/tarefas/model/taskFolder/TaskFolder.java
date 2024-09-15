package com.supera.desafio.tarefas.model.taskFolder;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.supera.desafio.tarefas.model.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "taskFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

    @Size(max = 35)
    private String folderName;

//----------------------------------------------------


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setTaskFolder(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setTaskFolder(null);
    }
}
