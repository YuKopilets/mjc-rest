INSERT INTO user_account (id, active) VALUES (11, true);
INSERT INTO user_role (user_id, role) VALUES (11, 'USER');
INSERT INTO user_role (user_id, role) VALUES (11, 'ADMIN');
INSERT INTO registration_type (user_id, registration) VALUES (11, 'LOCAL');
INSERT INTO local_account (id, login, password) VALUES (11, 'admin', ${admin-password});