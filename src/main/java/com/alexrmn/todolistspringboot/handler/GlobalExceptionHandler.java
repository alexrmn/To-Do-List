package com.alexrmn.todolistspringboot.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handle(EntityNotFoundException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(AuthorizationServiceException.class)
    public String handle(AuthorizationServiceException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
}
