package com.supera.desafio.tarefas.service;

import com.supera.desafio.tarefas.enums.Order;
import com.supera.desafio.tarefas.enums.Priority;
import com.supera.desafio.tarefas.enums.Status;
import com.supera.desafio.tarefas.infra.handle.ResponseFactory;
import com.supera.desafio.tarefas.model.task.Task;
import com.supera.desafio.tarefas.model.task.TaskRequest;
import com.supera.desafio.tarefas.model.taskFolder.TaskFolder;
import com.supera.desafio.tarefas.repository.TaskFolderRepository;
import com.supera.desafio.tarefas.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskFolderRepository taskFolderRepository;


    public TaskService(TaskRepository taskRepository, TaskFolderRepository taskFolderRepository) {
        this.taskRepository = taskRepository;
        this.taskFolderRepository = taskFolderRepository;
    }

    public Object save(TaskRequest taskRequest) {
        try {
            if(taskRequest.taskName() == null || taskRequest.taskName().isEmpty()){
                return ResponseFactory.errorNotAcceptable(null,"informe o nome da task","o nome da task é obrigatório");
            }
            if (taskRepository.existsByTaskName(taskRequest.taskName())){
                return ResponseFactory.errorConflict("já existe uma tarefa com este nome","mude o nome e tente novamente");
            }
            TaskFolder taskFolder = taskFolderRepository.findById(taskRequest.taskFolderId()).orElse(null);
            if (taskFolder != null) {
                Task task = new Task();
                BeanUtils.copyProperties(taskRequest, task);
                if (taskRequest.priority() == null){
                    task.setPriority(Priority.LOW);
                }
                if (taskRequest.status() == null){
                    task.setStatus(Status.NOT_STARTED);
                }
                task.setCreationDate(LocalDateTime.now());
                taskFolder.addTask(task);
                taskFolderRepository.save(taskFolder);
                return ResponseFactory.create(task.getTaskName(), "tarefa criada com sucesso", "Esta tarefa já pode ser gerenciada");
            }
            return ResponseFactory.errorNotFound("pasta de tarefas não encontrada", "verifique i id e tente novamente");

        } catch (DataAccessException e) {
            return ResponseFactory.errorBadRequest("Erro ao acessar o banco de dados", e.getMessage());
        } catch (Exception e) {
            return ResponseFactory.errorBadRequest("Ocorreu um erro inesperado", e.getMessage());
        }
    }

    public Object delete(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task !=null) {
            taskRepository.delete(task);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseFactory.errorNotFound("pasta de tarefas ou task não encontrada", "verifique os ids e tente novamente");
    }

    public Object edit(Integer taskId, String taskName) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task !=null) {
            if (taskRepository.existsByTaskName(taskName)){
                return ResponseFactory.errorConflict("já existe uma tarefa com este nome","mude o nome e tente novamente");
            }
            task.setTaskName(taskName);
            taskRepository.save(task);
            return ResponseFactory.ok(task.getTaskName(), "nome alterado com sucesso");
        }
        return ResponseFactory.errorNotFound("pasta de tarefas não encontrada", "verifique o id e tente novamente");
    }

    public Object getOne(Integer id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            return ResponseFactory.ok(task);
        }
        return ResponseFactory.errorNotFound("tarefa não encontrada", "verifique o id e tente novamente");
    }

    public Object getList(int page, Order order) {
        try {
            PageRequest pageable = PageRequest.of(page, 10);

            Page<Task> tasksPage = taskRepository.findAllOrdered(pageable, order.name());

            return ResponseFactory.ok(tasksPage);
        } catch (Exception e) {
            return ResponseFactory.errorBadRequest("Ocorreu um erro inesperado", e.getMessage());
        }
    }


}
