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
                .ignoringRequestMatchers("/api/**","/danhchochusan/manage-timeslots/save")
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/profile", "/courts/create").authenticated()
                    .requestMatchers(HttpMethod.POST, "/profile/update").authenticated()
                    .requestMatchers("/login","/forgot-password","verify-code","/reset-password").permitAll()
                    .requestMatchers("/", "/danhchochusan/thanhtoan","/danhchochusan/manage-timeslots/save", "/danhsachsan", "/assets/**").permitAll()
                    .anyRequest().permitAll()
                .and()
                    .rememberMe()
                    .key("uniqueAndSecret") // Khóa bí mật để mã hóa cookie
                    .tokenValiditySeconds(7 * 24 * 60 * 60) // Thời gian sống của cookie (7 ngày)
                    .rememberMeParameter("remember-me") // Tên tham số checkbox "Nhớ mật khẩu"
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
