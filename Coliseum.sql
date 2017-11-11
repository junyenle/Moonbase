
DROP DATABASE IF EXISTS ColiseumDatabase;
CREATE DATABASE ColiseumDatabase;
USE ColiseumDatabase;

CREATE TABLE Users (
	username VARCHAR(25) PRIMARY KEY NOT NULL,
	pass VARCHAR(50) NOT NULL,
	highScore INT(25)
);

CREATE TABLE HighScores (
	username VARCHAR(25) PRIMARY KEY NOT NULL,
	highScore int(25) NOT NULL
);

INSERT INTO Users (username, pass, highScore)
	VALUES ('testUser', 'testPass', 69420);

INSERT INTO HighScores (username, highScore)
	VALUES ('testUser', 69420);
