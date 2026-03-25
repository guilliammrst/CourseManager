package com.esgi.coursemanager.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Result<T> {

    private final boolean success;
    private final T data;
    private final HttpStatus errorStatus;
    private final String error;

    private Result(boolean success, T data, HttpStatus errorStatus, String error) {
        this.success = success;
        this.data = data;
        this.errorStatus = errorStatus;
        this.error = error;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data, null, null);
    }

    public static Result<Void> success() {
        return new Result<>(true, null, null, null);
    }

    public static <T> Result<T> failure(HttpStatus status, String error) {
        return new Result<>(false, null, status, error);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }

    public String getError() {
        return error;
    }

    public ResponseEntity<?> toResponseEntity (HttpStatus successStatus) {
        if (!success)
            return ResponseEntity.status(errorStatus).body(error);

        if (data == null)
            return ResponseEntity.noContent().build();

        return ResponseEntity.status(successStatus).body(data);
    }
}