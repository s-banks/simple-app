-- Create the database
CREATE DATABASE IF NOT EXISTS simpleapp_db;

-- Switch to the database
USE simpleapp_db;

-- Create the employee table
CREATE TABLE IF NOT EXISTS `employee` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255),
    `last_name` VARCHAR(255),
    `email` VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

-- Create the admin table
CREATE TABLE IF NOT EXISTS `admin` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL,
    `employee_id` BIGINT UNSIGNED NOT NULL UNIQUE,
    `is_super_admin` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
);
