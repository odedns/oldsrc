CREATE TABLE Player (
		id INTEGER NOT NULL,
		name VARCHAR(255) NOT NULL,
		salary DOUBLE NOT NULL,
		pos INTEGER NOT NULL,
		team_id INTEGER
	);

CREATE TABLE Team (
		id INTEGER NOT NULL,
		name VARCHAR(255) NOT NULL,
		city VARCHAR(255) NOT NULL
	);

CREATE TABLE Order (
		orderId INTEGER NOT NULL,
		description VARCHAR(255) NOT NULL
	);

CREATE TABLE Vehicle (
		model VARCHAR(255) NOT NULL,
		year INTEGER NOT NULL,
		id BIGINT NOT NULL,
		desc VARCHAR(255) NOT NULL,
		make VARCHAR(255) NOT NULL,
		horsePower INTEGER NOT NULL
	);

CREATE TABLE OrderItem (
		itemId INTEGER NOT NULL,
		description VARCHAR(255) NOT NULL,
		price REAL NOT NULL
	);

ALTER TABLE OrderItem ADD CONSTRAINT OrderItem_PK PRIMARY KEY (itemId);

ALTER TABLE Team ADD CONSTRAINT Team_PK PRIMARY KEY (id);

ALTER TABLE Order ADD CONSTRAINT Order_PK PRIMARY KEY (orderId);

ALTER TABLE Vehicle ADD CONSTRAINT Vehicle_PK PRIMARY KEY (id);

ALTER TABLE Player ADD CONSTRAINT Player_PK PRIMARY KEY (id);

