-- Create the database
CREATE DATABASE IF NOT EXISTS simpleapp_db;

-- Switch to the database
USE simpleapp_db;

-- Create the employee table
CREATE TABLE IF NOT EXISTS `employee` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `firstName` VARCHAR(255),
    `lastName` VARCHAR(255),
    `email` VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

-- Create the admin table
CREATE TABLE IF NOT EXISTS `admin` (
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL,
    `employee_id` BIGINT(20) UNSIGNED NOT NULL UNIQUE,
    `isSuperAdmin` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
);
