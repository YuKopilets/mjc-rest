INSERT INTO tag (name) VALUES ('rest');
INSERT INTO tag (name) VALUES ('spa');
INSERT INTO tag (name) VALUES ('holiday');
INSERT INTO tag (name) VALUES ('feast');
INSERT INTO tag (name) VALUES ('sport');
INSERT INTO tag (name) VALUES ('tourism');

INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('Sport AS certificate',
'The AS Gift Certificate will be a welcome gift for sports fans and fans of stylish sportswear.',
10.20, '2007-03-01 13:00:30.234', '2007-03-01 13:00:30.234', 10);
INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('Happy spa day!',
'If you are tired of the daily hustle and bustle and want to relax in the SPA became your constant leisure - the "Antistress light" program will introduce you to the salon and plunge you into the atmosphere of a real fairy tale.',
20.23, '2010-09-02 13:00:20.354', '2010-09-02 13:00:20.354', 10);
INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('Sightseeing tour of Minsk on the retro bus ZIS-8',
'An unforgettable trip around Minsk on a replica of the ZIS-8 bus is a non-standard gift that will delight both adults and children. With a certificate for a sightseeing tour of Minsk on a retro bus, you can get acquainted with the most interesting places and sights of the city, with its history and breathtaking legends.
And as a souvenir, you can take beautiful photographs in the retro atmosphere of factories of the early 20th century.',
40.20, '2012-12-12 12:12:12.354', '2012-12-12 12:12:12.354', 12);

INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 5);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 2);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (2, 3);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 1);
INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (3, 6);