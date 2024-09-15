package com.supera.desafio.tarefas.infra.handle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ResponseFactory {

    public static Object ok(Object body) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(LocalDateTime.now(), true, "Consulta realizada com sucesso", 200, body, ""));
    }

    public static Response<Object> ok(Object body, String message) {
        return response(HttpStatus.OK.value(), body, message, "");
    }

    public static Response<Object> ok(Object body, String message, String observation) {
        return response(HttpStatus.OK.value(), body, message, observation);
    }

    public static Object create(Object body, String message, String observation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(LocalDateTime.now(), true, message, 201, body, observation));
    }

    private static Response<Object> response(Serializable code, Object body, String message, String observation) {
        return new Response<>(LocalDateTime.now(), true, message, code, body, observation);
    }

//-----------------------------------------------------------------------------------------------------

    public static Object errorNotFound(String message, String observation) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(LocalDateTime.now(), false, message, 404, null, observation));
    }

    public static Object errorConflict(String message, String observation) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response<>(LocalDateTime.now(), false, message, 409, null, observation));
    }

    public static Object errorBadRequest(String message, String observation) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(LocalDateTime.now(), false, message, 500, null, observation));
    }

    public static Object errorBadRequest() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(LocalDateTime.now(), false, "erro não catalogado", 500, null, "entre em contato com o desenvolvedor"));
    }

}
