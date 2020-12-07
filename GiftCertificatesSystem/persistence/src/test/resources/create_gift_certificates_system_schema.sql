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
  active INTEGER NOT NULL
);

CREATE TABLE user_role
(
  user_id BIGINT NOT NULL,
  role VARCHAR(50) NOT NULL PRIMARY KEY,
  FOREIGN KEY (user_id) REFERENCES user_account(id)
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

CREATE TABLE REVINFO (
   REV INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
   REVTSTMP BIGINT
);

CREATE TABLE gift_certificate_aud
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1024) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    create_date DATETIME NOT NULL,
    last_update_date DATETIME NOT NULL,
    duration INT NOT NULL,
    REV INTEGER NOT NULL,
    REVTYPE tinyint,
    FOREIGN KEY (REV) REFERENCES REVINFO (REV)
);

CREATE TABLE tag_aud
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    REV INTEGER NOT NULL,
    REVTYPE tinyint,
    FOREIGN KEY (REV) REFERENCES REVINFO (REV)
);

CREATE TABLE gift_certificate_has_tag_aud
(
  gift_certificate_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  REV INTEGER NOT NULL,
  REVTYPE tinyint,
  FOREIGN KEY (REV) REFERENCES REVINFO (REV)
);

CREATE TABLE user_account_aud
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  active INTEGER NOT NULL,
  REV INTEGER NOT NULL,
  REVTYPE tinyint,
  FOREIGN KEY (REV) REFERENCES REVINFO (REV)
);

CREATE TABLE user_role_aud
(
  user_id BIGINT NOT NULL,
  role VARCHAR(50) NOT NULL PRIMARY KEY,
  REV INTEGER NOT NULL,
  REVTYPE tinyint,
  FOREIGN KEY (user_id) REFERENCES user_account(id),
  FOREIGN KEY (REV) REFERENCES REVINFO (REV)
);

CREATE TABLE user_order_aud
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cost DECIMAL(20,2) NOT NULL,
  date DATETIME NOT NULL,
  user_account_id BIGINT NOT NULL,
  REV INTEGER NOT NULL,
  REVTYPE tinyint,
  FOREIGN KEY (REV) REFERENCES REVINFO (REV),
  FOREIGN KEY (user_account_id) REFERENCES user_account(id)
  ON DELETE CASCADE
);

CREATE TABLE order_has_gift_certificate_aud
(
  order_id BIGINT NOT NULL,
  gift_certificate_id BIGINT NOT NULL,
  REV INTEGER NOT NULL,
  REVTYPE tinyint,
  FOREIGN KEY (REV) REFERENCES REVINFO (REV)
);
