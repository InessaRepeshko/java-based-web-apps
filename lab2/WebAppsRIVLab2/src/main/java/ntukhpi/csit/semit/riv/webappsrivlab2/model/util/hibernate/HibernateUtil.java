package ntukhpi.csit.semit.riv.webappsrivlab2.model.util.hibernate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

/**
 * HibernateUtil is a utility class that provides methods for managing the Hibernate {@link SessionFactory}
 * and the {@link Validator} for entity validation. This class is responsible for initializing,
 * configuring, and shutting down these resources.
 *
 * It supports:
 * - SessionFactory initialization and management for Hibernate sessions.
 * - Validator initialization and management for validating entities using JSR-303/JSR-380 Bean Validation.
 * - Graceful shutdown of Hibernate resources and the Validator factory.
 *
 * This class is designed to be thread-safe and ensures that the resources are initialized lazily when needed.
 *
 * @author Inessa Repeshko CS-222a
 */
public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    static {
        buildSessionFactory();
        buildValidator();
    }

    private static void buildSessionFactory() {
        registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        try {
            MetadataSources sources = new MetadataSources(registry);
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            System.err.println("Error during sessionFactory initialization: " + e.getMessage());
            e.printStackTrace(System.out);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void buildValidator() {
        try {
            validatorFactory = Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory();
            validator = validatorFactory.getValidator();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Validator.", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }

        return sessionFactory;
    }

    public static Validator getValidator() {
        if (validator == null) {
            buildValidator();
        }

        return validator;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }

        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        if (validatorFactory != null) {
            validatorFactory.close();
        }
    }
}
