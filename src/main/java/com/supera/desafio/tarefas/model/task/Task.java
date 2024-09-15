package com.supera.desafio.tarefas.model.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supera.desafio.tarefas.enums.Priority;
import com.supera.desafio.tarefas.enums.Status;
import com.supera.desafio.tarefas.model.taskFolder.TaskFolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_folder_id")
    @JsonBackReference
    private TaskFolder taskFolder;

    @Size(max = 100)
    private String taskName;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime creationDate;

//----------------------------------------------------------


    public TaskFolder getTaskFolder() {
        return taskFolder;
    }

    public void setTaskFolder(TaskFolder taskFolder) {
        this.taskFolder = taskFolder;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
