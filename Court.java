package com.pickleball.model;

public class Court {
    private Long id;
    private String location;

    public Court(Long id, String location) {
        this.id = id;
        this.location = location;
    }

    public Long getId() { return id; }
    public String getLocation() { return location; }
}