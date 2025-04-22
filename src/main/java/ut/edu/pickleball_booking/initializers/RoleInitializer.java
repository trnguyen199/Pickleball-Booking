package ut.edu.pickleball_booking.initializers;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ut.edu.pickleball_booking.entity.Role;
import ut.edu.pickleball_booking.repositories.RoleRepository;

@Component
public class RoleInitializer {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeRoles() {
        if (roleRepository.findByName("ROLE_CUSTOMER").isEmpty()) {
            Role customerRole = new Role();
            customerRole.setName("ROLE_CUSTOMER");
            roleRepository.save(customerRole);
        }

        if (roleRepository.findByName("ROLE_OWNER").isEmpty()) {
            Role ownerRole = new Role();
            ownerRole.setName("ROLE_OWNER");
            roleRepository.save(ownerRole);
        }
    }
}