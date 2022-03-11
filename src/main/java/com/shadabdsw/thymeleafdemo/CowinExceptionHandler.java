package com.shadabdsw.thymeleafdemo;

import com.shadabdsw.thymeleafdemo.Model.User;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CowinExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    public String handleAnyException(Exception ex, Model model) {
        System.out.println("Exception occured: " + ex.getMessage());

        model.addAttribute("error", ex.getMessage());

        if (ex.getMessage().contains("401")) {
            // model.addAttribute("error", "Unauthorized!");
            return "error/401";
        } else if (ex.getMessage().contains("403")) {
            model.addAttribute("user", new User());
            return "register";
        } else if (ex.getMessage().contains("404")) {
            model.addAttribute("user", new User());
            // model.addAttribute("error", "User not found!");
            return "register";
        } else if (ex.getMessage().contains("405")) {
            // model.addAttribute("error", "Method not allowed!");
            return "error/405";
        } else if (ex.getMessage().contains("409")) {
            // model.addAttribute("error", "User already exists!");
            return "error/409";
        } else {
            // model.addAttribute("error", "Something went wrong!");
            return "error/500";
        }
    }

}
