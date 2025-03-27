package ut.edu.Pickleball_Booking.models;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Code", length = 50, nullable = false)
    private String code;

    @Column(name = "permission_name", length = 100, nullable = false)
    private String permissionName;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getPermissionName() {
        return permissionName;
    }
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @ManyToMany(mappedBy = "permission")
    private Set<User> users = new HashSet<>();

}