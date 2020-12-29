INSERT INTO gift_certificates_system.user_account (id, active) VALUES (11, 1);
INSERT INTO gift_certificates_system.user_role (user_id, role) VALUES (11, 'USER');
INSERT INTO gift_certificates_system.user_role (user_id, role) VALUES (11, 'ADMIN');
INSERT INTO gift_certificates_system.registration_type (user_id, registration) VALUES (11, 'LOCAL');
INSERT INTO gift_certificates_system.local_account (id, login, password) VALUES (11, 'admin', ${admin-password});