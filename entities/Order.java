package pic.booking.entities;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private String role;
    
    // Getters and Setters
}

@Entity
@Table(name = "courts")
public class Court {
    @Id
    @GeneratedValue
    private UUID id;
    
    private String name;
    private String location;
    private double pricePerHour;
    private boolean isAvailable;
    
    // Getters and Setters
}

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @Column(nullable = false)
    private String status;
    
    // Getters and Setters
}

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private UUID id;
    
    private String name;
    private double price;
    
    // Getters and Setters
}

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    private int quantity;
    private LocalDateTime orderDate;
    
    // Getters and Setters
}
