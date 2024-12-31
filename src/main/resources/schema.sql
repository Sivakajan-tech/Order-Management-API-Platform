CREATE TABLE
    `clients` (
        `client_id` bigint NOT NULL AUTO_INCREMENT,
        `first_name` varchar(255) NOT NULL,
        `last_name` varchar(255) NOT NULL,
        `email` varchar(255) NOT NULL,
        `password` varchar(255) NOT NULL,
        PRIMARY KEY (`client_id`),
        UNIQUE KEY `client_id_UNIQUE` (`client_id`),
        UNIQUE KEY `email_UNIQUE` (`email`)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE
    `products` (
        `order_id` bigint NOT NULL AUTO_INCREMENT,
        `created_at` datetime (6) NOT NULL,
        `product_name` varchar(255) NOT NULL,
        `shipping_address` varchar(255) NOT NULL,
        `status` enum ('CANCELLED', 'DISPATCHED', 'NEW') NOT NULL,
        PRIMARY KEY (`order_id`)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;