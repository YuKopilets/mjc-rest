INSERT INTO gift_certificates_system.tag (name) VALUES ('rest');
INSERT INTO gift_certificates_system.tag (name) VALUES ('spa');
INSERT INTO gift_certificates_system.tag (name) VALUES ('holiday');
INSERT INTO gift_certificates_system.tag (name) VALUES ('feast');
INSERT INTO gift_certificates_system.tag (name) VALUES ('sport');
INSERT INTO gift_certificates_system.tag (name) VALUES ('tourism');

INSERT INTO gift_certificates_system.gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('Sport AS certificate',
'The AS Gift Certificate will be a welcome gift for sports fans and fans of stylish sportswear.',
10.20, '2007-03-01 13:00:30.234', '2007-03-01 13:00:30.234', 10);
INSERT INTO gift_certificates_system.gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('Happy spa day!',
'If you are tired of the daily hustle and bustle and want to relax in the SPA became your constant leisure - the "Antistress light" program will introduce you to the salon and plunge you into the atmosphere of a real fairy tale.',
20.23, '2010-09-02 13:00:20.354', '2010-09-02 13:00:20.354', 10);
INSERT INTO gift_certificates_system.gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('Sightseeing tour of Minsk on the retro bus ZIS-8',
'An unforgettable trip around Minsk on a replica of the ZIS-8 bus is a non-standard gift that will delight both adults and children. With a certificate for a sightseeing tour of Minsk on a retro bus, you can get acquainted with the most interesting places and sights of the city, with its history and breathtaking legends.
And as a souvenir, you can take beautiful photographs in the retro atmosphere of factories of the early 20th century.',
40.20, '2012-12-12 12:12:12.354', '2012-12-12 12:12:12.354', 12);

INSERT INTO gift_certificates_system.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 5);
INSERT INTO gift_certificates_system.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 2);
INSERT INTO gift_certificates_system.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 3);
INSERT INTO gift_certificates_system.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 1);
INSERT INTO gift_certificates_system.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 6);

INSERT INTO gift_certificates_system.user_account (active) VALUES (1);
INSERT INTO gift_certificates_system.user_role (user_id, role) VALUES (1, 'USER');
INSERT INTO gift_certificates_system.registration_type (user_id, registration) VALUES (1, 'LOCAL');
INSERT INTO gift_certificates_system.local_account (id, login, password) VALUES (1, 'user', 'user');

INSERT INTO gift_certificates_system.user_order (user_account_id, cost, date) VALUES (1, 30.43, '2009-10-04 15:42:20.134');
INSERT INTO gift_certificates_system.user_order (user_account_id, cost, date) VALUES (1, 10.20, '2010-03-01 13:48:10.224');

INSERT INTO gift_certificates_system.order_has_gift_certificate (order_id, gift_certificate_id) VALUES (1, 1);
INSERT INTO gift_certificates_system.order_has_gift_certificate (order_id, gift_certificate_id) VALUES (1, 2);
INSERT INTO gift_certificates_system.order_has_gift_certificate (order_id, gift_certificate_id) VALUES (2, 1);