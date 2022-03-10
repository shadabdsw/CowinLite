package com.shadabdsw.thymeleafdemo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CowinExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    public String handleAnyException(Exception ex, Model model) {
        System.out.println("Exception occured: " + ex.getMessage());

        if (ex.getMessage().contains("403")) {
            model.addAttribute("error", "You are not authorized to access this page");
            return "error/403";
        } else if (ex.getMessage().contains("404")) {
            model.addAttribute("error", "Page not found");
            return "error/404";
        } else if (ex.getMessage().contains("405")) {
            model.addAttribute("error", "Method not allowed");
            return "error/405";
        } else if (ex.getMessage().contains("409")) {
            model.addAttribute("error", "Conflict");
            return "error/409";
        } else {
            model.addAttribute("error", "Something went wrong");
            return "error/500";
        }
    }

}
