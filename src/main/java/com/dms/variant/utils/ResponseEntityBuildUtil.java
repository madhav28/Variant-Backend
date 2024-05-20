package com.dms.variant.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public class ResponseEntityBuildUtil {

    public static ResponseEntity<Object> getResponseEntity(boolean isSuccess, String message, HttpStatus status) {
        return new ResponseEntity<>(new Response( isSuccess, message), status);
    }

    @Getter
    static class Response implements Serializable {
        private final boolean success;
        private final String message;
        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
