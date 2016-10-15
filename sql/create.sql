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
	currency_short_name varchar NOT NULL,
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
	currency_id int NOT NULL,
	age int,
	gender char,
	occupation_id int,
	deals varchar,
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
	detail varchar NOT NULL,
	goal_amount NUMERIC NOT NULL,
	goal_type int NOT NULL,
	category int NOT NULL,
	frequency varchar NOT NULL,
	CONSTRAINT goal_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE currency (
	id serial NOT NULL,
	short_name varchar NOT NULL,
	long_name varchar NOT NULL,
	CONSTRAINT currency_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);



ALTER TABLE users ADD CONSTRAINT users_fk0 FOREIGN KEY (status_id) REFERENCES status(id);

ALTER TABLE transaction ADD CONSTRAINT transaction_fk0 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE transaction ADD CONSTRAINT transaction_fk2 FOREIGN KEY (category_id) REFERENCES category(id);

ALTER TABLE session ADD CONSTRAINT session_fk0 FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE user_detail ADD CONSTRAINT user_detail_fk0 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_detail ADD CONSTRAINT user_detail_fk1 FOREIGN KEY (occupation_id) REFERENCES occupation(id);
ALTER TABLE user_detail ADD CONSTRAINT user_detail_fk2 FOREIGN KEY (currency_id) REFERENCES currency(id);

ALTER TABLE goal ADD CONSTRAINT goal_fk0 FOREIGN KEY (user_id) REFERENCES users(id);

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
INSERT INTO occupation(id, name) VALUES(2, 'Others');

INSERT INTO currency(id, short_name, long_name) VALUES(1, 'DZD', 'Algeria Dinars');
INSERT INTO currency(id, short_name, long_name) VALUES(2, 'ARS', 'Argentina Pesos');
INSERT INTO currency(id, short_name, long_name) VALUES(3, 'AUD', 'Australia Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(4, 'BHD', 'Bahrain Dinars');
INSERT INTO currency(id, short_name, long_name) VALUES(5, 'BRL', 'Brazil Reais');
INSERT INTO currency(id, short_name, long_name) VALUES(6, 'BGN', 'Bulgaria Leva');
INSERT INTO currency(id, short_name, long_name) VALUES(7, 'CAD', 'Canada Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(8, 'CLP', 'Chile Pesos');
INSERT INTO currency(id, short_name, long_name) VALUES(9, 'CNY', 'China Yuan Renminbi (RMB)');
INSERT INTO currency(id, short_name, long_name) VALUES(10, 'COP', 'Colombia Pesos');
INSERT INTO currency(id, short_name, long_name) VALUES(11, 'CRC', 'Costa Rica Colones');
INSERT INTO currency(id, short_name, long_name) VALUES(12, 'HRK', 'Croatia Kuna');
INSERT INTO currency(id, short_name, long_name) VALUES(13, 'CZK', 'Czech Republic Koruny');
INSERT INTO currency(id, short_name, long_name) VALUES(14, 'DKK', 'Denmark Kroner');
INSERT INTO currency(id, short_name, long_name) VALUES(15, 'DOP', 'Dominican Republic Pesos');
INSERT INTO currency(id, short_name, long_name) VALUES(16, 'EGP', 'Egypt Pounds');
INSERT INTO currency(id, short_name, long_name) VALUES(17, 'EEK', 'Estonia Krooni');
INSERT INTO currency(id, short_name, long_name) VALUES(18, 'EUR', 'Euro');
INSERT INTO currency(id, short_name, long_name) VALUES(19, 'FJD', 'Fiji Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(20, 'HKD', 'Hong Kong Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(21, 'HUF', 'Hungary Forint');
INSERT INTO currency(id, short_name, long_name) VALUES(22, 'ISK', 'Iceland Kronur');
INSERT INTO currency(id, short_name, long_name) VALUES(23, 'INR', 'India Rupees');
INSERT INTO currency(id, short_name, long_name) VALUES(24, 'IDR', 'Indonesia Rupiahs');
INSERT INTO currency(id, short_name, long_name) VALUES(25, 'ILS', 'Israel New Shekels');
INSERT INTO currency(id, short_name, long_name) VALUES(26, 'JMD', 'Jamaica Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(27, 'JPY', 'Japan Yen');
INSERT INTO currency(id, short_name, long_name) VALUES(28, 'JOD', 'Jordan Dinars');
INSERT INTO currency(id, short_name, long_name) VALUES(29, 'KES', 'Kenya Shillings');
INSERT INTO currency(id, short_name, long_name) VALUES(30, 'KWD', 'Kuwait Dinars');
INSERT INTO currency(id, short_name, long_name) VALUES(31, 'LBP', 'Lebanon Pounds');
INSERT INTO currency(id, short_name, long_name) VALUES(32, 'MYR', 'Malaysia Ringgits');
INSERT INTO currency(id, short_name, long_name) VALUES(33, 'MUR', 'Mauritius Rupees');
INSERT INTO currency(id, short_name, long_name) VALUES(34, 'MXN', 'Mexico Pesos');
INSERT INTO currency(id, short_name, long_name) VALUES(35, 'MAD', 'Morocco Dirhams');
INSERT INTO currency(id, short_name, long_name) VALUES(36, 'NZD', 'New Zealand Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(37, 'NOK', 'Norway Kroner');
INSERT INTO currency(id, short_name, long_name) VALUES(38, 'OMR', 'Oman Rials');
INSERT INTO currency(id, short_name, long_name) VALUES(39, 'PKR', 'Pakistan Rupees');
INSERT INTO currency(id, short_name, long_name) VALUES(40, 'PEN', 'Peru Nuevos Soles');
INSERT INTO currency(id, short_name, long_name) VALUES(41, 'PHP', 'Philippines Pesos');
INSERT INTO currency(id, short_name, long_name) VALUES(42, 'PLN', 'Poland Zlotych');
INSERT INTO currency(id, short_name, long_name) VALUES(43, 'QAR', 'Qatar Riyals');
INSERT INTO currency(id, short_name, long_name) VALUES(44, 'RON', 'Romania New Lei');
INSERT INTO currency(id, short_name, long_name) VALUES(45, 'RUB', 'Russia Rubles');
INSERT INTO currency(id, short_name, long_name) VALUES(46, 'SAR', 'Saudi Arabia Riyals');
INSERT INTO currency(id, short_name, long_name) VALUES(47, 'SGD', 'Singapore Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(48, 'SKK', 'Slovakia Koruny');
INSERT INTO currency(id, short_name, long_name) VALUES(49, 'ZAR', 'South Africa Rand');
INSERT INTO currency(id, short_name, long_name) VALUES(50, 'KRW', 'South Korea Won');
INSERT INTO currency(id, short_name, long_name) VALUES(51, 'LKR', 'Sri Lanka Rupees');
INSERT INTO currency(id, short_name, long_name) VALUES(52, 'SEK', 'Sweden Kronor');
INSERT INTO currency(id, short_name, long_name) VALUES(53, 'CHF', 'Switzerland Francs');
INSERT INTO currency(id, short_name, long_name) VALUES(54, 'TWD', 'Taiwan New Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(55, 'THB', 'Thailand Baht');
INSERT INTO currency(id, short_name, long_name) VALUES(56, 'TTD', 'Trinidad and Tobago Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(57, 'TND', 'Tunisia Dinars');
INSERT INTO currency(id, short_name, long_name) VALUES(58, 'TRY', 'Turkey Lira');
INSERT INTO currency(id, short_name, long_name) VALUES(59, 'AED', 'United Arab Emirates Dirhams');
INSERT INTO currency(id, short_name, long_name) VALUES(60, 'GBP', 'United Kingdom Pounds');
INSERT INTO currency(id, short_name, long_name) VALUES(61, 'USD', 'United States Dollars');
INSERT INTO currency(id, short_name, long_name) VALUES(62, 'VEB', 'Venezuela Bolivares');
INSERT INTO currency(id, short_name, long_name) VALUES(63, 'VND', 'Vietnam Dong');
INSERT INTO currency(id, short_name, long_name) VALUES(64, 'ZMK', 'Zambia Kwacha');
