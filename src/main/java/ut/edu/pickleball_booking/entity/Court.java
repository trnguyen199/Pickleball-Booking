package ut.edu.pickleball_booking.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courts")
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User courtOwner;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL)
    private List<TimeSlot> timeSlots;
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getCourtOwner() {
        return courtOwner;
    }

    public void setCourtOwner(User courtOwner) {
        this.courtOwner = courtOwner;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}