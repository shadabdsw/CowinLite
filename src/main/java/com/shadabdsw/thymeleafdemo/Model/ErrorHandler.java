package com.shadabdsw.thymeleafdemo.Model;

public class ErrorHandler {

    private boolean status;
    private String message;

    public ErrorHandler() {
        this.status = false;
        this.message = "";
    }

    public ErrorHandler(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorHandler{" + "status=" + status + ", message=" + message + '}';
    }
    
}