package ut.edu.pickleball_booking.configurations;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import ut.edu.pickleball_booking.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf() // Bật CSRF
        .and()
        .authorizeHttpRequests()
            .requestMatchers("/profile").authenticated() // Yêu cầu xác thực cho /profile
            .anyRequest().permitAll() // Cho phép các yêu cầu khác
        .and()
        .formLogin()
            .loginPage("/login") // Trang đăng nhập tùy chỉnh
            .defaultSuccessUrl("/profile", true) // Chuyển hướng đến /profile sau khi đăng nhập thành công
        .and()
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true"); // Chuyển hướng đến /login sau khi đăng xuất

        return http.build();
    }
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}