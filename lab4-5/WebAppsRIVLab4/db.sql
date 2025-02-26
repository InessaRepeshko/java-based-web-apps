# DROP DATABASE IF EXISTS webappsrivlab4;
# CREATE DATABASE webappsrivlab4;
#
# DROP TABLE webappsrivlab4.entrants;
# DROP TABLE webappsrivlab4.students;
#
# USE webappsrivlab4;
# SHOW TABLES LIKE 'entrant';
#
# DESCRIBE entrant;
#
# USE webappsrivlab4;
# SHOW TABLES LIKE 'students';


DROP TABLE webappsrivlab4.userEntities;
DROP TABLE webappsrivlab4.roles;
DROP TABLE webappsrivlab4.user_roles;

-- Table: userEntities

CREATE TABLE userEntities (
                       id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
)
ENGINE = InnoDB;

-- Table: roles

CREATE TABLE roles (
                       id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL
)
ENGINE = InnoDB;

-- Table for mapping userEntity and roles: user_roles

CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES userEntities (id),
                            FOREIGN KEY (role_id) REFERENCES roles (id),
                            UNIQUE (user_id, role_id)
)
ENGINE = InnoDB;

-- Insert data

INSERT INTO roles VALUES (1, 'USER');
INSERT INTO roles VALUES (2, 'ADMIN');
INSERT INTO userEntities VALUES (1, 'admin', '$2a$11$uSXS6rLJ9WjgOHhEGDx..VGs7MkkZV68LV5r1uwFu7HgtRn3dcXG');
INSERT INTO user_roles VALUES (1, 2);


CREATE TABLE userEntities (
    password VARCHAR(50) NOT NULL CHECK (REGEXP_LIKE(password, '^(?=.{8,50}$)(?=.*[A-Z])(?=.*[!@#$%^&*()_+\-=\\[\\]{};'':"\\|,.<>/?~])[A-Za-z!@#$%^&*()_+\-=\\[\\]{};''":\\|,.<>/?~]*$'))
);
