INSERT INTO status (name) VALUES ('blabla');

INSERT INTO users (username, email, password, salt_hash, first_name, last_name, token, status_id, budget) 
	VALUES ('as', 'ads', 'asads', 'gagas', 'asdas', 'ada', 'anan', 1, -50.56);
	
SELECT * FROM users;

INSERT INTO status (name) VALUES ('Inactive');
INSERT INTO status (name) VALUES ('Active');
INSERT INTO status (name) VALUES ('Disabled');