package com.supera.desafio.tarefas.repository;

import com.supera.desafio.tarefas.model.taskFolder.TaskFolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskFolderRepository extends JpaRepository<TaskFolder, Integer> {

    Page<TaskFolder> findAll(Pageable pageable);

    boolean existsByFolderName(String folderName);

    @Query("SELECT tf FROM TaskFolder tf LEFT JOIN FETCH tf.tasks t " +
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
    Page<TaskFolder> findAllOrdered(Pageable pageable, @Param("order") String order);



}
