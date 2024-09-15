package com.supera.desafio.tarefas.service;

import com.supera.desafio.tarefas.enums.Order;
import com.supera.desafio.tarefas.infra.handle.ResponseFactory;
import com.supera.desafio.tarefas.model.task.Task;
import com.supera.desafio.tarefas.model.taskFolder.TaskFolder;
import com.supera.desafio.tarefas.repository.TaskFolderRepository;
import com.supera.desafio.tarefas.repository.TaskRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskFolderService {

    private final TaskFolderRepository taskFolderRepository;
    private final TaskRepository taskRepository;

    public TaskFolderService(TaskFolderRepository taskFolderRepository, TaskRepository taskRepository) {
        this.taskFolderRepository = taskFolderRepository;
        this.taskRepository = taskRepository;
    }

    public Object save(String nameFolder) {
        try {
            if (taskFolderRepository.existsByFolderName(nameFolder)) {
                return ResponseFactory.errorConflict("já existe uma tarefa com este nome", "mude o nome e tente novamente");
            }
            TaskFolder taskFolder = new TaskFolder();
            taskFolder.setFolderName(nameFolder);
            taskFolderRepository.save(taskFolder);
            return ResponseFactory.create(taskFolder.getFolderName(), "Pasta criada com sucesso", "Esta pasta já pode ser gerenciada");
        } catch (DataAccessException e) {
            return ResponseFactory.errorBadRequest("Erro ao acessar o banco de dados", e.getMessage());
        } catch (Exception e) {
            return ResponseFactory.errorBadRequest("Ocorreu um erro inesperado", e.getMessage());
        }
    }

    public Object delete(Integer id) {
        TaskFolder taskFolder = taskFolderRepository.findById(id).orElse(null);
        if (taskFolder != null) {
            taskFolderRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseFactory.errorNotFound("pasta de tarefas não encontrada", "verifique o id e tente novamente");
    }

    public Object edit(Integer id, String newNameFolder) {
        TaskFolder taskFolder = taskFolderRepository.findById(id).orElse(null);
        if (taskFolder != null) {
            if (taskFolderRepository.existsByFolderName(newNameFolder)) {
                return ResponseFactory.errorConflict("já existe uma tarefa com este nome", "mude o nome e tente novamente");
            }
            taskFolder.setFolderName(newNameFolder);
            taskFolderRepository.save(taskFolder);
            return ResponseFactory.ok(taskFolder.getFolderName(), "nome alterado com sucesso");
        }
        return ResponseFactory.errorNotFound("pasta de tarefas não encontrada", "verifique o id e tente novamente");
    }

    public Object getOne(Integer id) {
        TaskFolder taskFolder = taskFolderRepository.findById(id).orElse(null);
        if (taskFolder != null) {
            return ResponseFactory.ok(taskFolder);
        }
        return ResponseFactory.errorNotFound("pasta de tarefas não encontrada", "verifique o id e tente novamente");
    }

    public Object getList(int page, Order order) {
        try {
            PageRequest pageable = PageRequest.of(page, 5);

            Page<TaskFolder> taskFolders = taskFolderRepository.findAllOrdered(pageable, order.name());

            return ResponseFactory.ok(taskFolders);
        } catch (Exception e) {
            return ResponseFactory.errorBadRequest("Ocorreu um erro inesperado", e.getMessage());
        }
    }

    public Object getTasksOrderedBy(Integer folderId, Order order) {
        TaskFolder taskFolder = taskFolderRepository.findById(folderId).orElse(null);
        if (taskFolder == null) {
            return ResponseFactory.errorNotFound("Pasta de tarefas não encontrada", "Verifique o id e tente novamente");
        }

        List<Task> sortedTasks = taskRepository.findTasksByFolderIdOrdered(folderId, order.name());

        taskFolder.setTasks(sortedTasks);

        return ResponseFactory.ok(taskFolder);
    }



}
