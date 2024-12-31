CREATE TABLE
    `clients` (
        `client_id` BINARY(16) NOT NULL UNIQUE,
        `first_name` VARCHAR(255) NOT NULL,
        `last_name` VARCHAR(255) NOT NULL,
        `email` VARCHAR(255) NOT NULL UNIQUE,
        `password` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`client_id`)
    );

CREATE TABLE
    `orders` (
        `order_id` BINARY(16) NOT NULL UNIQUE,
        `client_id` BINARY(16) NOT NULL,
        `order_name` VARCHAR(255) NOT NULL,
        `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `shipping_address` VARCHAR(255) NOT NULL,
        `quantity` INT NOT NULL,
        `status` enum ('CANCELLED', 'DISPATCHED', 'NEW') NOT NULL DEFAULT 'NEW',
        PRIMARY KEY (`order_id`),
        FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
    );
