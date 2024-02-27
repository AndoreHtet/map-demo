package com.example.mapdmeo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public String HandleException(Exception ex, Model model){
        model.addAttribute("message",ex.getMessage());
        return "error";
    }
}
