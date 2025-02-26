package ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.token;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing a password reset token.
 * This class is used to manage password reset functionality for users, storing unique tokens
 * and their expiration details.
 * <p>
 * Key functionalities:
 * - Associates a unique password reset token with a specific `UserEntity`.
 * - Defines an expiration date for each token to ensure security.
 * - Provides methods for generating a secure token using UUID.
 * <p>
 * Key attributes:
 * - `id`: Unique identifier for the token entity.
 * - `token`: Unique string representing the password reset token.
 * - `expiryDate`: Expiration date for the token, ensuring it cannot be used indefinitely.
 * - `user`: The associated `UserEntity` for whom the token was generated.
 * <p>
 * Relationships:
 * - `user`: One-to-one relationship with the `UserEntity` class, with cascade and foreign key constraints.
 * <p>
 * Constraints:
 * - Token length must be exactly 36 characters.
 * - Expiry date must be in the future.
 * - User association is mandatory.
 * <p>
 * Hibernate annotations are used to define the schema structure and enforce constraints.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserEntity
 * @see UUID
 * @see LocalDateTime
 * @see jakarta.persistence.Entity
 */

@Entity
@Table(name = "password_reset_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true, nullable = false, length = 36)
    @Length(min = 36, max = 36, message = "The password reset token should be 36 characters long.")
    private String token;

    @Column(name = "expiry_date", nullable = false)
    @NotNull(message = "The expiry date must not be null.")
    @Future(message = "The expiry date must be in the future.")
    private LocalDateTime expiryDate;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_password_reset_token_user",
                    foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE"))
    @Valid
    private UserEntity user;

    public PasswordResetToken(UserEntity user) {
        setToken(generateToken());
        setExpiryDate(LocalDateTime.now().plusMinutes(EXPIRATION));
        setUser(user);
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
