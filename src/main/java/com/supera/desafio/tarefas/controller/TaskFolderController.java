package com.supera.desafio.tarefas.controller;

import com.supera.desafio.tarefas.enums.Order;
import com.supera.desafio.tarefas.service.TaskFolderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskFolder")
public class TaskFolderController {

    private final TaskFolderService taskFolderService;

    public TaskFolderController(TaskFolderService taskFolderService) {
        this.taskFolderService = taskFolderService;
    }

    @PostMapping("/new")
    @Operation(summary = "cria uma pasta de tarefas")
    public Object save(@RequestParam("nameFolder") @Size(max = 35, message = "O nome da pasta não pode exceder 35 caracteres") String nameFolder) {
        return taskFolderService.save(nameFolder);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "deleta uma pasta de tarefas")
    public Object delete(@PathVariable Integer id) {
        return taskFolderService.delete(id);
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "edita o nome da pasta")
    public Object edit(@PathVariable Integer id, String newNameFolder) {
        return taskFolderService.edit(id, newNameFolder);
    }

    @GetMapping("/getOne/{id}")
    @Operation(summary = "retorna uma pasta pelo id")
    public Object getOne(@PathVariable Integer id) {
        return taskFolderService.getOne(id);
    }

    @GetMapping("/getOneOrder/{id}")
    @Operation(summary = "Retorna uma pasta pelo id com as tarefas com filtro de ordenacão")
    public Object getOne(@PathVariable Integer id, @RequestParam(defaultValue = "DATE") Order order) {
        return taskFolderService.getTasksOrderedBy(id, order);
    }

    @GetMapping("/getList/{page}")
    @Operation(summary = "Retorna a lista de pastas paginada com as tarefas ordenadas por filtro")
    public Object getList(@PathVariable int page, @RequestParam(defaultValue = "DATE") Order order) {
        return taskFolderService.getList(page, order);
    }
}
