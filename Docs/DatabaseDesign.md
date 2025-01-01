## Database Design

### Entity-Relationship Diagram (ERD)
- **Clients**:
  - `id`, `email`, `password`, `firstName`, `lastName`.
- **Orders**:
  - `id`, `orderReference`, `itemName`, `quantity`, `shippingAddress`, `status`, `timestamp`, `client_id`.

### DDL Scripts
```sql
CREATE TABLE clients (
    `client_id` binary(16) NOT NULL,
    `email` varchar(255) NOT NULL,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`client_id`)
);

CREATE TABLE orders (
    `order_id` binary(16) NOT NULL,
    `created_at` datetime(6) NOT NULL,
    `order_name` varchar(255) NOT NULL,
    `quantity` int NOT NULL,
    `shipping_address` varchar(255) NOT NULL,
    `status` enum('CANCELLED','DISPATCHED','NEW') NOT NULL,
    `client_id` binary(16) NOT NULL,
    PRIMARY KEY (`order_id`),
    KEY `FKcw5e0vbqc8vvtjcsr566dyhuk` (`client_id`),
    CONSTRAINT `FKcw5e0vbqc8vvtjcsr566dyhuk` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
);
```

### ER Diagram
![ER Diagram](/statics/Images/ER%20Diagram.PNG)
