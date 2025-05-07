package ut.edu.pickleball_booking.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.services.AuthService;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ApplicationContext context;

    public CustomLoginSuccessHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        AuthService authService = context.getBean(AuthService.class);
        String username = authentication.getName();
        User user = authService.getUserByUsername(username);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getId());
            
            // Kiểm tra nếu là tài khoản admin
            if ("admin".equals(username) || 
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/dashboard");
                return;
            }
        }

        // Nếu không phải admin thì chuyển về trang chủ
        response.sendRedirect("/trangchu");
    }
}
