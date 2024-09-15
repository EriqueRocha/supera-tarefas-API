package com.supera.desafio.tarefas.repository;

import com.supera.desafio.tarefas.model.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findAll(Pageable pageable);

    boolean existsByTaskName(String taskName);

    @Query("SELECT t FROM Task t ORDER BY " +
            "CASE WHEN :order = 'PRIORITY' THEN t.priority END ASC, " +
            "CASE WHEN :order = 'STATUS' THEN t.status END ASC, " +
            "CASE WHEN :order = 'DATE' THEN t.creationDate END DESC")
    Page<Task> findAllOrdered(Pageable pageable, @Param("order") String order);

    @Query("SELECT t FROM Task t WHERE t.taskFolder.id = :folderId " +
            "ORDER BY " +
            "CASE WHEN :order = 'PRIORITY' THEN " +
            "  CASE t.priority " +
            "    WHEN com.supera.desafio.tarefas.enums.Priority.HIGH THEN 1 " +
            "    WHEN com.supera.desafio.tarefas.enums.Priority.MEDIUM THEN 2 " +
            "    WHEN com.supera.desafio.tarefas.enums.Priority.LOW THEN 3 " +
            "  END " +
            "END ASC, " +
            "CASE WHEN :order = 'STATUS' THEN t.status END ASC, " +
            "CASE WHEN :order = 'DATE' THEN t.creationDate END DESC")
    List<Task> findTasksByFolderIdOrdered(@Param("folderId") Integer folderId, @Param("order") String order);
}
