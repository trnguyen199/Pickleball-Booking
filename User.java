package pic.booking.implementClass;


import jakarta.persistence.*;
import pic.booking.enums.Role;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // Tên bảng trong database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    private Long id;

    @Column(nullable = false, unique = true) // Không được null, giá trị duy nhất
    private String username;

    @Column(nullable = false) // Không được null
    private String password;

    @Column(nullable = false, unique = true) // Không được null, giá trị duy nhất
    private String email;

    @Column(nullable = true) // Có thể null
    private String phoneNumber;

    @Enumerated(EnumType.STRING) // Lưu kiểu Enum dưới dạng chuỗi
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String accountStatus; // Active, Inactive, Suspended

    @Column(nullable = false, updatable = false) // Không được cập nhật sau khi tạo
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Constructor mặc định (bắt buộc cho JPA)
    public User() {}

    // Constructor đầy đủ
    public User(Long id, String username, String password, String email, String phoneNumber, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.accountStatus = "Active"; // Trạng thái mặc định
    }

    // Tự động set thời gian khi tạo bản ghi
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Tự động set thời gian khi cập nhật bản ghi
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters và Setters
    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Phương thức kiểm tra vai trò
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public boolean isCourtManager() {
        return this.role == Role.COURT_MANAGER;
    }

    public boolean isPlayer() {
        return this.role == Role.PLAYER;
    }
}