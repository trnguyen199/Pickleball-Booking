CREATE TABLE `User` (
  `user_id` int PRIMARY KEY AUTO_INCREMENT,
  `full_name` varchar(50) NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `phone` varchar(20),
  `password_hash` varchar(255) NOT NULL,
  `role` enum('admin', 'customer') DEFAULT 'customer',
  `created_at` timestamp DEFAULT 'CURRENT_TIMESTAMP'
);

CREATE TABLE `Courts` (
  `court_id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `location` text NOT NULL,
  `price_per_hour` int NOT NULL,
  `status` enum('available', 'maintenance') DEFAULT 'available'
);

CREATE TABLE `Bookings` (
  `booking_id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `court_id` int,
  `booking_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `total_price` int NOT NULL,
  `status` enum('pending', 'confirmed', 'cancelled') DEFAULT 'pending',
  `created_at` timestamp DEFAULT 'CURRENT_TIMESTAMP'
);

CREATE TABLE `Payment` (
  `ayment_id` int PRIMARY KEY AUTO_INCREMENT,
  `booking_id` int,
  `amount` int NOT NULL,
  `payment_method` enum('credit_card', 'paypal', 'bank_transfer') NOT NULL,
  `status` ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
  `payment_date` timestamp DEFAULT 'CURRENT_TIMESTAMP'
);

CREATE TABLE `Reviews` (
  `review_id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `court_id` int,
  `rating` int check (rating between 1 and 5),
  `comment` text,
  `created_at` timestamp DEFAULT 'CURRENT_TIMESTAMP'
);

ALTER TABLE `Bookings` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

ALTER TABLE `Bookings` ADD FOREIGN KEY (`court_id`) REFERENCES `Courts` (`court_id`);

ALTER TABLE `Payment` ADD FOREIGN KEY (`booking_id`) REFERENCES `Bookings` (`booking_id`);

ALTER TABLE `Reviews` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);

ALTER TABLE `Reviews` ADD FOREIGN KEY (`court_id`) REFERENCES `Courts` (`court_id`);
