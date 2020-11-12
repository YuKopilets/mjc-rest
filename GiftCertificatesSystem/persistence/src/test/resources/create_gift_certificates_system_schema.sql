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
  ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tag(id)
  ON DELETE CASCADE
);

CREATE TABLE user_account
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(50) NOT NULL
);

CREATE TABLE user_order
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cost DECIMAL(20,2) NOT NULL,
  date DATETIME NOT NULL,
  user_account_id BIGINT NOT NULL,
  FOREIGN KEY (user_account_id) REFERENCES user_account(id)
  ON DELETE CASCADE
);

CREATE TABLE order_has_gift_certificate
(
  order_id BIGINT NOT NULL,
  gift_certificate_id BIGINT NOT NULL,
  FOREIGN KEY (order_id) REFERENCES user_order(id)
  ON DELETE CASCADE,
  FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate(id)
  ON DELETE CASCADE
);
