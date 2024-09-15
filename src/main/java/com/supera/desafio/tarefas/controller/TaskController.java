package com.supera.desafio.tarefas.controller;

import com.supera.desafio.tarefas.enums.Order;
import com.supera.desafio.tarefas.model.task.TaskRequest;
import com.supera.desafio.tarefas.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/new")
    @Operation(summary = "cria uma tarefa")
    public Object save(@RequestBody TaskRequest taskRequest) {
        return taskService.save(taskRequest);
    }

    @DeleteMapping("/delete/{taskId}")
    @Operation(summary = "deleta uma tarefa")
    public Object delete(@PathVariable Integer taskId) {
        return taskService.delete(taskId);
    }

    @PutMapping("/edit/{taskId}")
    @Operation(summary = "edita uma tarefa")
    public Object edit(@PathVariable Integer taskId, String taskName) {
        return taskService.edit(taskId, taskName);
    }

    @GetMapping("/getOne/{id}")
    @Operation(summary = "retorna uma tarefa pelo id")
    public Object getOne(@PathVariable Integer id) {
        return taskService.getOne(id);
    }

    @GetMapping("/getList/{page}")
    @Operation(summary = "Retorna a lista de tarefas paginada com filtro de ordenacão")
    public Object getList(@PathVariable int page, @RequestParam(defaultValue = "DATE") Order order) {
        return taskService.getList(page, order);
    }

}
