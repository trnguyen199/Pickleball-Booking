package ut.edu.pickleball_booking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false, length = 50)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;

    // Thêm constructor rỗng
    public User() {}

    // Thêm constructor có tham số
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Thêm getter và setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}