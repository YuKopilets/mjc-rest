INSERT INTO tag (name) VALUES ('rest');
INSERT INTO tag (name) VALUES ('spa');
INSERT INTO tag (name) VALUES ('holiday');
INSERT INTO tag (name) VALUES ('feast');
INSERT INTO tag (name) VALUES ('sport');
INSERT INTO tag (name) VALUES ('tourism');

INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('firstCertificate',
'The First Certificate description',
10.20, '2007-03-01 13:00:30.234', '2007-03-01 13:00:30.234', 10);
INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('secondCertificate',
'The Second Certificate description',
20.23, '2010-09-02 13:00:20.354', '2010-09-02 13:00:20.354', 10);
INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('thirdCertificate',
'The Third Certificate description',
40.20, '2012-12-12 12:12:12.354', '2012-12-12 12:12:12.354', 12);

INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 5);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 2);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 3);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 1);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 6);

INSERT INTO user_account (login) VALUES ('user');

INSERT INTO user_order (user_account_id, cost, date) VALUES (1, 30.43, '2009-10-04 15:42:20.134');
INSERT INTO user_order (user_account_id, cost, date) VALUES (1, 10.20, '2010-03-01 13:48:10.224');

INSERT INTO order_has_gift_certificate (order_id, gift_certificate_id) VALUES (1, 1);
INSERT INTO order_has_gift_certificate (order_id, gift_certificate_id) VALUES (1, 2);
INSERT INTO order_has_gift_certificate (order_id, gift_certificate_id) VALUES (2, 1);