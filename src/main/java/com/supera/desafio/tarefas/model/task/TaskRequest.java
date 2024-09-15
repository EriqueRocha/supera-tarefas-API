package com.supera.desafio.tarefas.model.task;

import com.supera.desafio.tarefas.enums.Priority;
import com.supera.desafio.tarefas.enums.Status;
import jakarta.validation.constraints.NotBlank;

public record TaskRequest(

        @NotBlank
        String taskName,

        Priority priority,

        Status status,

        @NotBlank
        Integer taskFolderId

) {
}
