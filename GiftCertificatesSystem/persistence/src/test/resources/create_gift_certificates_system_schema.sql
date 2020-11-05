CREATE TABLE gift_certificate
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    create_date DATETIME NOT NULL,
    last_update_date DATETIME NOT NULL,
    duration INT NOT NULL
);

CREATE TABLE tag
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE gift_certificate_has_tag
(
  gift_certificate_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tag(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);