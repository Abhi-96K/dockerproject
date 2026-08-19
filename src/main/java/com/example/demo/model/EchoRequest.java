package com.example.demo.model;

public class EchoRequest {

    private String name;
    private String message;

    public EchoRequest() {
    }

    public EchoRequest(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
