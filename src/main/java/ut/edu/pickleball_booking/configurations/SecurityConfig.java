package ut.edu.pickleball_booking.configurations;


import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomLoginSuccessHandler loginSuccessHandler) throws Exception {
        http
                .csrf()
                .ignoringRequestMatchers("/api/**")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/profile", "/courts/create").authenticated()
                .requestMatchers(HttpMethod.POST, "/profile/update").authenticated()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/", "/danhchochusan/thanhtoan", "/danhsachsan", "/assets/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler) // inject từ bean đã có
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true");

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
