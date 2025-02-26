package ntukhpi.csit.semit.riv.webappsrivlab4.config;

import jakarta.servlet.SessionTrackingMode;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.EnumSet;

/**
 * Configuration class for security settings in the application.
 * This class is annotated with @Configuration and @EnableWebSecurity, making it the central configuration
 * for securing the application using Spring Security.
 * <p>
 * Key functionalities provided by this class:
 * <p>
 * - **AuthenticationManager**: Configures the authentication manager to handle user authentication.
 * - **SecurityFilterChain**: Defines the security filter chain to manage HTTP security, including login, logout,
 * session management, and access control.
 * - **Session Management**: Configures session timeout, tracking modes, and invalid session handling.
 * - **Role Hierarchy**: Defines hierarchical roles to simplify access control configuration.
 * - **Remember-Me Functionality**: Implements a "remember me" mechanism for user authentication persistence.
 * - **Authorization Rules**: Specifies access control for various endpoints based on roles.
 * - **HttpSessionEventPublisher**: Publishes HTTP session events for tracking and management purposes.
 * <p>
 * Notable configurations:
 * - Login and logout URLs, along with session expiration handling.
 * - Custom session tracking using cookies.
 * - Use of BCrypt for password hashing (via UserDetailsService).
 * - Role hierarchy to simplify role-based access control logic.
 *
 * @author Inessa Repeshko CS-222a
 * @see AuthenticationManager
 * @see SecurityFilterChain
 * @see HttpSecurity
 * @see Role
 * @see UserDetailsService
 * @see HttpSessionEventPublisher
 * @see SessionTrackingMode
 * @see ServletContextInitializer
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("corporateEmailOrUsername")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", false)
                        .failureUrl("/login?error=true")
                        .failureUrl("/errors/unauthorized")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .deleteCookies("remember-me")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .invalidSessionUrl("/login?sessionExpired=true")
                        .maximumSessions(1)
                        .expiredUrl("/login?sessionInvalidated=true")
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("rememberMeSecretKey")
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .rememberMeParameter("remember-me")
                        .userDetailsService(userDetailsService)
                )
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/image/**").permitAll()
                        .requestMatchers("/", "/login", "/register", "/reset-password", "/change-password", "/errors/**").permitAll()
                        .requestMatchers("/home", "/profile", "/profile/change-credentials").authenticated()
                        .requestMatchers("/users", "/users/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/entrants").hasRole(Role.ENTRANT_VIEWER.name())
                        .requestMatchers("/students").hasRole(Role.STUDENT_VIEWER.name())
                        .requestMatchers("/entrants/{id}/view").hasRole(Role.ENTRANT_VIEWER.name())
                        .requestMatchers("/students/{id}/view").hasRole(Role.STUDENT_VIEWER.name())
                        .requestMatchers("/entrants/add", "/entrants/{id}/edit", "/entrants/{id}/delete").hasRole(Role.ENTRANT_MANAGER.name())
                        .requestMatchers("/students/add", "/students/{id}/edit", "/students/{id}/delete").hasRole(Role.STUDENT_MANAGER.name())
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
            servletContext.setSessionTimeout(90);
            servletContext.getSessionCookieConfig().setMaxAge(60 * 60);
        };
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(Role.ADMIN.name()).implies(Role.ENTRANT_MANAGER.name(), Role.STUDENT_MANAGER.name())
                .role(Role.ENTRANT_MANAGER.name()).implies(Role.ENTRANT_VIEWER.name(), Role.STUDENT_VIEWER.name())
                .role(Role.STUDENT_MANAGER.name()).implies(Role.STUDENT_VIEWER.name(), Role.ENTRANT_VIEWER.name())
                .build();
    }
}
