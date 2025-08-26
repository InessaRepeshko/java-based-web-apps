package ntukhpi.csit.semit.riv.webappsrivlab4.config;

import ntukhpi.csit.semit.riv.webappsrivlab4.model.DAO.user.UserEntity;
import ntukhpi.csit.semit.riv.webappsrivlab4.service.entity.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserDetailsService interface for user authentication and authorization.
 * This service is responsible for loading user-specific data, such as username, password, and roles,
 * from the database to be used by Spring Security during the authentication process.
 * <p>
 * The class utilizes the UserService to fetch user details based on a given username or corporate email.
 * If the user is not found, a UsernameNotFoundException is thrown to indicate authentication failure.
 * <p>
 * Key responsibilities:
 * - Fetching the UserEntity object from the database.
 * - Converting UserEntity data into a UserDetails object required by Spring Security.
 * <p>
 * This implementation ensures secure user management and seamless integration with Spring Security.
 *
 * @author Inessa Repeshko CS-222a
 * @see UserService
 * @see UserEntity
 * @see UserDetails
 * @see UsernameNotFoundException
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userService.findUserByUsernameOrCorporateEmail(userIdentifier);

            if (userEntity == null) {
                throw new UsernameNotFoundException("No userEntity found with the specified data.");
            }

            return org.springframework.security.core.userdetails.User.builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .roles(userEntity.getRole().name())
                    .build();
                    
        } catch (Exception e) {
            logger.error("Error loading user by username: " + userIdentifier, e);
            throw new UsernameNotFoundException("Authentication failed for user: " + userIdentifier, e);
        }
    }
}

