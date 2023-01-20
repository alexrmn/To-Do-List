package com.alexrmn.todolistspringboot.handler;

import com.alexrmn.todolistspringboot.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public String handle(BusinessException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
}
