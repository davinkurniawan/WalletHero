CREATE TABLE users (
	id serial NOT NULL,
	username varchar NOT NULL UNIQUE,
	email varchar NOT NULL UNIQUE,
	password varchar NOT NULL,
	salt_hash varchar NOT NULL,
	first_name varchar NOT NULL,
	middle_name varchar,
	last_name varchar NOT NULL,
	token varchar NOT NULL,
	status_id int NOT NULL,
	budget NUMERIC NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE transaction (
	id serial NOT NULL,
	user_id int NOT NULL,
	date varchar NOT NULL,
	recur_id int,
	detail varchar,
	amount NUMERIC NOT NULL,
	category_id int,
	is_income BOOLEAN NOT NULL,
	CONSTRAINT transaction_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE session (
	id varchar NOT NULL,
	user_id int NOT NULL,
	last_access varchar NOT NULL,
	CONSTRAINT session_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE user_detail (
	id serial NOT NULL,
	user_id int NOT NULL,
	currency varchar,
	age int,
	gender char,
	occupation_id int,
	CONSTRAINT user_detail_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE category (
	id serial NOT NULL,
	name varchar NOT NULL UNIQUE,
	CONSTRAINT category_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE occupation (
	id serial NOT NULL,
	name varchar NOT NULL,
	CONSTRAINT occupation_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE status (
	id serial NOT NULL,
	name varchar NOT NULL,
	CONSTRAINT status_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE recurrence (
	id serial NOT NULL,
	type varchar NOT NULL,
	reccur_num int,
	remaining int,
	transaction_id int,
	CONSTRAINT recurrence_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE goal (
	id serial,
	user_id int NOT NULL,
	recur_id int NOT NULL,
	end_date varchar NOT NULL,
	goal_amount NUMERIC NOT NULL,
	active BOOLEAN NOT NULL,
	CONSTRAINT goal_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

ALTER TABLE users ADD CONSTRAINT users_fk0 FOREIGN KEY (status_id) REFERENCES status(id);

ALTER TABLE transaction ADD CONSTRAINT transaction_fk0 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE transaction ADD CONSTRAINT transaction_fk2 FOREIGN KEY (category_id) REFERENCES category(id);

ALTER TABLE session ADD CONSTRAINT session_fk0 FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE user_detail ADD CONSTRAINT user_detail_fk0 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_detail ADD CONSTRAINT user_detail_fk1 FOREIGN KEY (occupation_id) REFERENCES occupation(id);

ALTER TABLE goal ADD CONSTRAINT goal_fk0 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE goal ADD CONSTRAINT goal_fk1 FOREIGN KEY (recur_id) REFERENCES recurrence(id);

INSERT INTO status (id, name) VALUES (1, 'Inactive');
INSERT INTO status (id, name) VALUES (2, 'Active');
INSERT INTO status (id, name) VALUES (3, 'Disabled');

INSERT INTO category VALUES (0,  'Others');
INSERT INTO category VALUES (1,  'Business');
INSERT INTO category VALUES (2,  'Interest');
INSERT INTO category VALUES (3,  'Accounting/Legal');
INSERT INTO category VALUES (4,  'Auto');
INSERT INTO category VALUES (5,  'Capital Expenditure');
INSERT INTO category VALUES (6,  'Education');
INSERT INTO category VALUES (7,  'Food/Drink');
INSERT INTO category VALUES (8,  'Health');
INSERT INTO category VALUES (9,  'Maintenance');
INSERT INTO category VALUES (10, 'Office');
INSERT INTO category VALUES (11, 'Postage');
INSERT INTO category VALUES (12, 'Properties');
INSERT INTO category VALUES (13, 'Rent');
INSERT INTO category VALUES (14, 'Taxes');
INSERT INTO category VALUES (15, 'Telephone/Mobile');
INSERT INTO category VALUES (16, 'Utilities');

INSERT INTO occupation(id, name) VALUES(1, 'Student');
