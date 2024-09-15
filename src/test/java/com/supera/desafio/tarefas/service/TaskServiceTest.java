package com.supera.desafio.tarefas.service;

import com.supera.desafio.tarefas.enums.Order;
import com.supera.desafio.tarefas.enums.Priority;
import com.supera.desafio.tarefas.enums.Status;
import com.supera.desafio.tarefas.infra.handle.Response;
import com.supera.desafio.tarefas.model.task.Task;
import com.supera.desafio.tarefas.model.task.TaskRequest;
import com.supera.desafio.tarefas.model.taskFolder.TaskFolder;
import com.supera.desafio.tarefas.repository.TaskFolderRepository;
import com.supera.desafio.tarefas.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskFolderRepository taskFolderRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldCreateTaskSuccessfully() {
        TaskRequest taskRequest = new TaskRequest("New Task",  Priority.LOW, Status.NOT_STARTED, 1);
        TaskFolder mockTaskFolder = new TaskFolder();

        mockTaskFolder.setId(1);

        when(taskFolderRepository.findById(1)).thenReturn(Optional.of(mockTaskFolder));
        when(taskRepository.existsByTaskName("New Task")).thenReturn(false);

        Object response = taskService.save(taskRequest);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("tarefa criada com sucesso", resBody.getMessage());
        verify(taskFolderRepository, times(1)).save(mockTaskFolder);

        Task createdTask = mockTaskFolder.getTasks().get(0);

        assertEquals(Priority.LOW, createdTask.getPriority());
        assertEquals(Status.NOT_STARTED, createdTask.getStatus());
        assertNotNull(createdTask.getCreationDate());
    }


    @Test
    void save_ShouldReturnErrorWhenTaskAlreadyExists() {

        TaskRequest taskRequest = new TaskRequest("Existing Task", Priority.LOW, Status.NOT_STARTED, null);

        when(taskRepository.existsByTaskName("Existing Task")).thenReturn(true);

        Object response = taskService.save(taskRequest);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("já existe uma tarefa com este nome", resBody.getMessage());
        verify(taskFolderRepository, never()).save(any(TaskFolder.class));
    }


    @Test
    void save_ShouldReturnErrorWhenTaskFolderNotFound() {
        TaskRequest taskRequest = new TaskRequest("Task With No Folder", Priority.LOW, Status.NOT_STARTED, null);

        when(taskFolderRepository.findById(999)).thenReturn(Optional.empty());

        Object response = taskService.save(taskRequest);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("pasta de tarefas não encontrada", resBody.getMessage());
        verify(taskFolderRepository, never()).save(any(TaskFolder.class));
    }

    @Test
    void delete_ShouldDeleteTaskSuccessfully() {

        Integer taskId = 1;
        Task mockTask = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

        Object response = taskService.delete(taskId);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(taskRepository, times(1)).delete(mockTask);
    }

    @Test
    void delete_ShouldReturnErrorWhenTaskNotFound() {
        Integer taskId = 1;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Object response = taskService.delete(taskId);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("pasta de tarefas ou task não encontrada", resBody.getMessage());
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    void edit_ShouldUpdateTaskNameSuccessfully() {
        Integer taskId = 1;
        String newTaskName = "Updated Task Name";
        Task mockTask = new Task();

        mockTask.setTaskName("Old Task Name");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));
        when(taskRepository.existsByTaskName(newTaskName)).thenReturn(false);

        Object response = taskService.edit(taskId, newTaskName);

        assertTrue(response instanceof Response);

        Response<?> resBody = (Response<?>) response;

        assertEquals("nome alterado com sucesso", resBody.getMessage());
        assertEquals(newTaskName, mockTask.getTaskName());
        verify(taskRepository, times(1)).save(mockTask);
    }


    @Test
    void edit_ShouldReturnErrorWhenTaskNameAlreadyExists() {
        Integer taskId = 1;
        String newTaskName = "Existing Task Name";
        Task mockTask = new Task();

        mockTask.setTaskName("Old Task Name");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));
        when(taskRepository.existsByTaskName(newTaskName)).thenReturn(true);

        Object response = taskService.edit(taskId, newTaskName);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("já existe uma tarefa com este nome", resBody.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void edit_ShouldReturnErrorWhenTaskNotFound() {
        Integer taskId = 1;
        String newTaskName = "Updated Task Name";

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Object response = taskService.edit(taskId, newTaskName);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("pasta de tarefas não encontrada", resBody.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }


    @Test
    void getOne_ShouldReturnTaskWhenFound() {
        Integer taskId = 1;
        Task mockTask = new Task();

        mockTask.setId(taskId);
        mockTask.setTaskName("tarefa teste");
        mockTask.setPriority(Priority.HIGH);
        mockTask.setStatus(Status.NOT_STARTED);
        mockTask.setCreationDate(LocalDateTime.of(2024, 9, 14, 15, 28));

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

        Object response = taskService.getOne(taskId);

        assertTrue(response instanceof ResponseEntity);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertNotNull(resBody);
        assertTrue(resBody.getSuccess());
        assertEquals("Consulta realizada com sucesso", resBody.getMessage());
        assertEquals(200, resBody.getCode());

        Task responseTask = (Task) resBody.getBody();
        assertNotNull(responseTask);
        assertEquals(taskId, responseTask.getId());
        assertEquals("tarefa teste", responseTask.getTaskName());
        assertEquals(Priority.HIGH, responseTask.getPriority());
        assertEquals(Status.NOT_STARTED, responseTask.getStatus());
        assertEquals(LocalDateTime.of(2024, 9, 14, 15, 28), responseTask.getCreationDate());
    }

    @Test
    void getOne_ShouldReturnErrorWhenNotFound() {

        Integer taskId = 1;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Object response = taskService.getOne(taskId);

        assertTrue(response instanceof ResponseEntity<?>);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertNotNull(resBody);
        assertEquals("tarefa não encontrada", resBody.getMessage());
        assertNull(resBody.getBody());
    }

    @Test
    void getList_ShouldReturnSortedTasks() {
        int page = 0;
        Order order = Order.PRIORITY;

        Task task1 = new Task();
        task1.setPriority(Priority.HIGH);
        task1.setCreationDate(LocalDateTime.of(2024, 9, 14, 15, 30));

        Task task2 = new Task();
        task2.setPriority(Priority.LOW);
        task2.setCreationDate(LocalDateTime.of(2024, 9, 15, 10, 0));

        List<Task> tasks = Arrays.asList(task1, task2);
        Page<Task> tasksPage = new PageImpl<>(tasks, PageRequest.of(page, 10), tasks.size());

        when(taskRepository.findAllOrdered(any(Pageable.class), anyString())).thenReturn(tasksPage);

        Object response = taskService.getList(page, order);

        assertTrue(response instanceof ResponseEntity<?>);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertNotNull(resBody);
        assertNotNull(resBody.getBody());
        assertTrue(resBody.getBody() instanceof Page<?>);

        Page<?> sortedTasksPage = (Page<?>) resBody.getBody();
        List<?> sortedTasks = sortedTasksPage.getContent();

        assertEquals(2, sortedTasks.size());
        assertEquals(Priority.HIGH, ((Task) sortedTasks.get(0)).getPriority());
        assertEquals(Priority.LOW, ((Task) sortedTasks.get(1)).getPriority());
        assertEquals("Consulta realizada com sucesso", resBody.getMessage());
    }

}