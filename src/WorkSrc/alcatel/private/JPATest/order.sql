

CREATE TABLE Orders (
		orderId INTEGER NOT NULL,
		description VARCHAR(255) NOT NULL
	);

CREATE TABLE OrderItem (
		itemId INTEGER NOT NULL,
		description VARCHAR(255) NOT NULL,
		price REAL NOT NULL,
		orderId INTEGER NOT NULL
	);




ALTER TABLE OrderItem ADD CONSTRAINT OrderItem_PK PRIMARY KEY (itemId);

ALTER TABLE Orders ADD CONSTRAINT Order_PK PRIMARY KEY (orderId);


