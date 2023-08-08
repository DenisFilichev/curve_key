package com.example.curve_key.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tasks", schema = "public", catalog = "curve_key")
public class TasksEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "client_seq",
            sequenceName = "vpn_client_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    private int id;
    @Basic
    @Column(name = "task_text")
    private String taskText;
    @Basic
    @Column(name = "date_generate")
    private Instant dateGenerate;
    @Basic
    @Column(name = "date_execution")
    private Instant dateExecution;
    @Basic
    @Column(name = "is_active")
    private boolean isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public Instant getDateGenerate() {
        return dateGenerate;
    }

    public void setDateGenerate(Instant dateGenerate) {
        this.dateGenerate = dateGenerate;
    }

    public Instant getDateExecution() {
        return dateExecution;
    }

    public void setDateExecution(Instant dateExecution) {
        this.dateExecution = dateExecution;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksEntity that = (TasksEntity) o;
        return id == that.id && isActive == that.isActive && Objects.equals(taskText, that.taskText) && Objects.equals(dateGenerate, that.dateGenerate) && Objects.equals(dateExecution, that.dateExecution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskText, dateGenerate, dateExecution, isActive);
    }
}
