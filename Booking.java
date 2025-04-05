package com.pickleball.model;

public class Booking {
    private Long id;
    private Long userId;
    private Long courtId;

    public Booking(Long id, Long userId, Long courtId) {
        this.id = id;
        this.userId = userId;
        this.courtId = courtId;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getCourtId() { return courtId; }
}