INSERT INTO user_account (id, active) VALUES (1001, 1);
INSERT INTO user_role (user_id, role) VALUES (1001, 'USER');
INSERT INTO user_role (user_id, role) VALUES (1001, 'ADMIN');
INSERT INTO registration_type (user_id, registration) VALUES (1001, 'LOCAL');
INSERT INTO local_account (id, login, password) VALUES (1001, 'admin', 'admin');