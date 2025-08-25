-- Ініціалізаційний скрипт для TiDB
-- Створення таблиць та початкових даних

-- Створення бази даних (якщо потрібно)
-- CREATE DATABASE IF NOT EXISTS webappsrivlab4;
-- USE webappsrivlab4;

-- Створення таблиці ролей
CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
) ENGINE = InnoDB;

-- Створення таблиці користувачів
CREATE TABLE IF NOT EXISTS user_entities (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    corporate_email VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    surname VARCHAR(255),
    patronymic VARCHAR(255),
    role VARCHAR(50) NOT NULL DEFAULT 'USER'
) ENGINE = InnoDB;

-- Створення таблиці зв'язків користувач-роль
CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_entities (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    UNIQUE (user_id, role_id)
) ENGINE = InnoDB;

-- Створення таблиці токенів для скидання паролю
CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id INT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_entities (id) ON DELETE CASCADE
) ENGINE = InnoDB;

-- Створення таблиці абітурієнтів
CREATE TABLE IF NOT EXISTS entrants (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    corporate_email VARCHAR(255) UNIQUE,
    birthday DATE,
    gender VARCHAR(10),
    case_number VARCHAR(50) UNIQUE,
    rating_score DECIMAL(5,2)
) ENGINE = InnoDB;

-- Створення таблиці студентів
CREATE TABLE IF NOT EXISTS students (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    corporate_email VARCHAR(255) UNIQUE,
    funding_type VARCHAR(50),
    scholarship_status VARCHAR(50)
) ENGINE = InnoDB;

-- Вставка початкових ролей
INSERT IGNORE INTO roles (id, name) VALUES (1, 'USER');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ADMIN');

-- Вставка початкового адміністратора (пароль: Admin123!)
INSERT IGNORE INTO user_entities (id, username, password, role) 
VALUES (1, 'admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMIN');

-- Прив'язка адміністратора до ролі
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (1, 2);
