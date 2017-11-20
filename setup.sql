
DROP DATABASE IF EXISTS ColiseumDatabase;
CREATE DATABASE ColiseumDatabase;
USE ColiseumDatabase;

CREATE TABLE Users (
	username VARCHAR(25) PRIMARY KEY NOT NULL,
	pass VARCHAR(50) NOT NULL
);

INSERT INTO Users (username, pass)
	VALUES ('testUser', 'b62a565853f37fb1ec1efc287bfcebf9');

