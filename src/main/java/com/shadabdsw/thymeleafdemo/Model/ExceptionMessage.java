package com.shadabdsw.thymeleafdemo.Model;

public class ExceptionMessage {

    private Exception exception;
    private String message;

    public ExceptionMessage(Exception exception, String message) {
        this.exception = exception;
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExceptionMessage{" + "exception=" + exception + ", message=" + message + '}';
    }
    
}
