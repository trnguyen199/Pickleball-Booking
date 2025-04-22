package ut.edu.pickleball_booking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
            .anyRequest().permitAll() // Cho phép tất cả các yêu cầu mà không cần xác thực
            .and()
            .csrf().disable(); // Tắt CSRF nếu không cần thiết

        return http.build();
    }
}