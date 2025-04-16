CREATE USER 'user'@'localhost' IDENTIFIED BY 'bd2pass';
GRANT ALL PRIVILEGES ON bd2_tour_1.* TO 'user'@'localhost';
FLUSH PRIVILEGES;
