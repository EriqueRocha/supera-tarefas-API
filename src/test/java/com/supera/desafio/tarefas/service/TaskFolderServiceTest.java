package com.supera.desafio.tarefas.service;

import com.supera.desafio.tarefas.infra.handle.Response;
import com.supera.desafio.tarefas.model.taskFolder.TaskFolder;
import com.supera.desafio.tarefas.repository.TaskFolderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskFolderServiceTest {

    @Autowired
    private TaskFolderService taskFolderService;

    @MockBean
    private TaskFolderRepository taskFolderRepository;

    @Test
    void save_ShouldReturnErrorWhenFolderNameExists() {
        String nameFolder = "Existing Folder";
        when(taskFolderRepository.existsByFolderName(nameFolder)).thenReturn(true);

        Object response = taskFolderService.save(nameFolder);

        assertTrue(response instanceof ResponseEntity<?>);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("já existe uma tarefa com este nome", resBody.getMessage());
        verify(taskFolderRepository, never()).save(any(TaskFolder.class));
    }

    @Test
    void save_ShouldCreateFolderSuccessfully() {
        String nameFolder = "New Folder";
        when(taskFolderRepository.existsByFolderName(nameFolder)).thenReturn(false);

        Object response = taskFolderService.save(nameFolder);

        assertTrue(response instanceof ResponseEntity<?>);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("Pasta criada com sucesso", resBody.getMessage());
        assertEquals(nameFolder, resBody.getBody());
        verify(taskFolderRepository, times(1)).save(any(TaskFolder.class));
    }

    @Test
    void save_ShouldReturnErrorWhenDataAccessExceptionOccurs() {
        String nameFolder = "New Folder";
        when(taskFolderRepository.existsByFolderName(nameFolder)).thenReturn(false);
        doThrow(new DataAccessException("Erro no banco de dados") {}).when(taskFolderRepository).save(any(TaskFolder.class));

        Object response = taskFolderService.save(nameFolder);

        assertTrue(response instanceof ResponseEntity<?>);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("Erro ao acessar o banco de dados", resBody.getMessage());
    }

    @Test
    void save_ShouldReturnErrorWhenUnexpectedExceptionOccurs() {
        String nameFolder = "New Folder";
        when(taskFolderRepository.existsByFolderName(nameFolder)).thenReturn(false);
        doThrow(new RuntimeException("Erro inesperado")).when(taskFolderRepository).save(any(TaskFolder.class));

        Object response = taskFolderService.save(nameFolder);

        assertTrue(response instanceof ResponseEntity<?>);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("Ocorreu um erro inesperado", resBody.getMessage());
    }

    @Test
    void delete_ShouldReturnNoContentWhenFolderExists() {

        Integer folderId = 1;
        TaskFolder mockTaskFolder = new TaskFolder();
        mockTaskFolder.setId(folderId);
        when(taskFolderRepository.findById(folderId)).thenReturn(Optional.of(mockTaskFolder));

        Object response = taskFolderService.delete(folderId);

        assertTrue(response instanceof ResponseEntity<?>);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(taskFolderRepository, times(1)).deleteById(folderId);
    }

    @Test
    void delete_ShouldReturnErrorWhenFolderNotFound() {
        Integer folderId = 1;

        when(taskFolderRepository.findById(folderId)).thenReturn(Optional.empty());

        Object response = taskFolderService.delete(folderId);

        assertTrue(response instanceof ResponseEntity<?>);

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;

        Response<?> resBody = (Response<?>) responseEntity.getBody();

        assertEquals("pasta de tarefas não encontrada", resBody.getMessage());
        assertEquals("verifique o id e tente novamente", resBody.getObservation());

        verify(taskFolderRepository, never()).deleteById(folderId);
    }
}