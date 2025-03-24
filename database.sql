CREATE DATABASE PickleballDB;
USE PickleballDB;

-- Bảng User
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES Role(id) ON DELETE SET NULL
);

-- Bảng Role
CREATE TABLE Role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Bảng Court (Sân pickleball)
CREATE TABLE Court (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status ENUM('available', 'booked', 'maintenance') DEFAULT 'available'
);

-- Bảng Booking (Lịch đặt sân)
CREATE TABLE Booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    court_id INT,
    booking_time DATETIME NOT NULL,
    duration INT NOT NULL COMMENT 'Thời gian thuê theo giờ',
    total_price DECIMAL(10,2),
    status ENUM('pending', 'confirmed', 'cancelled') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (court_id) REFERENCES Court(id) ON DELETE CASCADE
);

-- Bảng Payment (Thanh toán)
CREATE TABLE Payment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT,
    amount DECIMAL(10,2) NOT NULL,
    payment_method ENUM('cash', 'credit_card', 'paypal') NOT NULL,
    status ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES Booking(id) ON DELETE CASCADE
);

-- Bảng Review (Đánh giá sân)
CREATE TABLE Review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    court_id INT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (court_id) REFERENCES Court(id) ON DELETE CASCADE
);
