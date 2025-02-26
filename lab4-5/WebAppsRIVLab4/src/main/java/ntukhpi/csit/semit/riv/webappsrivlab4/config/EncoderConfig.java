package ntukhpi.csit.semit.riv.webappsrivlab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for defining the PasswordEncoder bean.
 * This class is annotated with @Configuration, indicating that it declares one or more @Bean methods
 * and can be processed by the Spring container to generate bean definitions and service requests.
 * <p>
 * The defined PasswordEncoder bean uses BCryptPasswordEncoder to hash passwords securely.
 * BCrypt is a strong hashing algorithm widely used for password storage to ensure
 * passwords are not stored in plaintext and are resistant to brute force attacks.
 *
 * @author Inessa Repeshko CS-222a
 * @see Configuration
 * @see PasswordEncoder
 * @see BCryptPasswordEncoder
 */

@Configuration
public class EncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
