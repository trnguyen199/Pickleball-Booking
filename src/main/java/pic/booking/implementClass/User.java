package pic.booking.implementClass;
import pic.booking.enums.Role;

public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Role role;

    // Constructor
    public User(Long id, String username, String password, String email, String phoneNumber, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getter username
    public String getUsername() {
        return username;
    }

    // Các getter khác nếu cần
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    // Setter cho username (nếu cần)
    public void setUsername(String username) {
        this.username = username;
    }
}
