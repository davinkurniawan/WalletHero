INSERT INTO status (id, name) VALUES (1, 'Inactive');
INSERT INTO status (id, name) VALUES (2, 'Active');
INSERT INTO status (id, name) VALUES (3, 'Disabled');

INSERT INTO occupation(id, name) VALUES(1, 'Student');

INSERT INTO users (id, username, email, password, salt_hash, first_name, middle_name, last_name, token, status_id, budget) 
	VALUES (1, 'wallethero', 'wallethero@wallethero.com', 'wallethero', 'gagas', 'Wallet', 'Something', 'Hero', 'lol', 2, 0.0);
	
INSERT INTO user_detail(id, user_id, currency, age, gender, occupation_id)
	VALUES (1, 1, 'AUD', 22, 'M', 1);

SELECT * FROM status;
SELECT * FROM occupation;
SELECT * FROM users;
SELECT * FROM user_detail;
