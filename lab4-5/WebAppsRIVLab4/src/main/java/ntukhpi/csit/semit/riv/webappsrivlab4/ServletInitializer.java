package ntukhpi.csit.semit.riv.webappsrivlab4;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer for configuring the Spring Boot application when deployed in a traditional servlet container.
 * This class extends `SpringBootServletInitializer` and overrides the `configure` method to specify the main application class.
 * <p>
 * Key functionalities:
 * - Configures the Spring Boot application when running within a servlet container.
 * - Ensures the correct setup of the Spring application context during deployment.
 * <p>
 * This class is essential for deploying Spring Boot applications to an external servlet container (like Tomcat or Jetty).
 * It allows the application to function as a traditional WAR (Web Application Archive) file.
 *
 * @author Inessa Repeshko CS-222a
 * @see SpringBootServletInitializer
 * @see WebAppsRivLab4Application
 */

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebAppsRivLab4Application.class);
    }

}
