CREATE TABLE gift_certificate
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    create_date DATETIME NOT NULL,
    last_update_date DATETIME NOT NULL,
    duration INT NOT NULL
);

INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('firstCertificate', 'The First Certificate description', 10.20, '2007-03-01 13:00:30.234', '2007-03-01 13:00:30.234', 10);
INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('secondCertificate', 'The Second Certificate description', 20.23, '2010-09-02 13:00:20.354', '2010-09-02 13:00:20.354', 10);
INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('thirdCertificate', 'The Third Certificate description', 40.20, '2012-12-12 12:12:12.354', '2012-12-12 12:12:12.354', 12);
