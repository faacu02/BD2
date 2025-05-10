CREATE USER 'user'@'localhost' IDENTIFIED BY 'bd2pass';
GRANT SELECT,DELETE, INSERT, UPDATE, CREATE, DROP, ALTER ON bd2_tours_1.* TO 'user'@'localhost';
