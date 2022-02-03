package com.shadabdsw.thymeleafdemo.Model;

public class UserReq {
    private String name;   

    public UserReq(){
    }

    public UserReq(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserReq [name=" + name + "]";
    }
}




