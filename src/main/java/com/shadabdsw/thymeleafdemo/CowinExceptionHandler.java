// package com.shadabdsw.thymeleafdemo;

// import com.shadabdsw.thymeleafdemo.Model.ErrorHandler;

// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice
// public class CowinExceptionHandler {

//     ErrorHandler error = new ErrorHandler();

//     @ExceptionHandler(Exception.class)
//     public String handleAnyException(Exception ex, Model model) {
//         System.err.println("Exception occured: " + ex.getMessage());

//         // model.addAttribute("error", ex.getMessage());

//         // if (ex.getMessage().contains("401")) {
//         //     return "error/401";
//         // } else if (ex.getMessage().contains("403")) {
//         //     model.addAttribute("user", new User());
//         //     error.setStatus(true);
//         //     model.addAttribute("error", error);
//         //     return "register";
//         // } else if (ex.getMessage().contains("404")) {
//         //     model.addAttribute("user", new User());        
//         //     error.setStatus(true);
//         //     error.setMessage("User Not Found!");
//         //     model.addAttribute("error", error);
//         //     return "register";
//         // } else if (ex.getMessage().contains("405")) {
//         //     // model.addAttribute("error", "Method not allowed!");
//         //     return "error/405";
//         // } else if (ex.getMessage().contains("409")) {
//         //     model.addAttribute("user", new User());        
//         //     error.setStatus(true);
//         //     error.setMessage("User Already Exists!");
//         //     model.addAttribute("error", error);
//         //     return "register";
//         // } else {
//         //     // model.addAttribute("error", "Something went wrong!");
//         //     return "error/500";
//         // }

//         return "error/500";

//     }


// }
