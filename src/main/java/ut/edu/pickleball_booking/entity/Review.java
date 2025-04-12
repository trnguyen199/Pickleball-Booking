package ut.edu.pickleball_booking.entiny;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer rating; // Rating score (1-5)
    
    @Column(length = 1000)
    private String comment;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // Một trong hai trường này sẽ có giá trị, tùy thuộc vào đánh giá cho court hay product
    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
