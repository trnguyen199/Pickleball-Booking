package com.pbs.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Court {
    private UUID id;
    private String name;
    private String address;
    private String city;
    private String description;
    private int numberOfCourts;
    private double pricePerHour;
    private boolean indoor;
    private List<String> facilities;
    private String imageUrl;
    private double rating;
    private int reviewCount;
    private UUID managerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;

    public Court() {
        this.id = UUID.randomUUID();
        this.facilities = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
        this.rating = 0.0;
        this.reviewCount = 0;
    }

    public Court(String name, String address, String city, String description, int numberOfCourts, 
                 double pricePerHour, boolean indoor, UUID managerId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.city = city;
        this.description = description;
        this.numberOfCourts = numberOfCourts;
        this.pricePerHour = pricePerHour;
        this.indoor = indoor;
        this.facilities = new ArrayList<>();
        this.managerId = managerId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
        this.rating = 0.0;
        this.reviewCount = 0;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfCourts() {
        return numberOfCourts;
    }

    public void setNumberOfCourts(int numberOfCourts) {
        this.numberOfCourts = numberOfCourts;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public UUID getManagerId() {
        return managerId;
    }

    public void setManagerId(UUID managerId) {
        this.managerId = managerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addFacility(String facility) {
        if (!facilities.contains(facility)) {
            facilities.add(facility);
        }
    }

    public void removeFacility(String facility) {
        facilities.remove(facility);
    }

    public void addReview(double newRating) {
        double totalRating = (rating * reviewCount) + newRating;
        reviewCount++;
        rating = totalRating / reviewCount;
    }
}
