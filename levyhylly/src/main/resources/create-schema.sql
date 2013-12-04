DROP TABLE track IF EXISTS;
DROP TABLE album IF EXISTS;
DROP TABLE artist IF EXISTS;
DROP TABLE track IF EXISTS;
DROP TABLE user_group_map IF EXISTS;
DROP TABLE user_group IF EXISTS;
DROP TABLE user_account IF EXISTS;

CREATE TABLE artist (
	id NUMERIC GENERATED ALWAYS AS IDENTITY (START WITH 1),
	name VARCHAR(128) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (name)
);

DROP INDEX  artist_name_idx IF EXISTS;
CREATE INDEX artist_name_idx ON artist (name);

CREATE TABLE album (
	id NUMERIC GENERATED ALWAYS AS IDENTITY (START WITH 1),
	name VARCHAR(128) NOT NULL,
	year SMALLINT,
	artist_id NUMERIC,
	PRIMARY KEY (id),
	FOREIGN KEY (artist_id) REFERENCES artist (id),
	UNIQUE (name, artist_id)
);

DROP INDEX album_name_idx IF EXISTS;
CREATE INDEX album_name_idx ON album (name);

CREATE TABLE track (
	id NUMERIC GENERATED ALWAYS AS IDENTITY (START WITH 1),
	track_number SMALLINT NOT NULL,
	name VARCHAR(128) NOT NULL,
	track_length SMALLINT,
	album_id NUMERIC,
	PRIMARY KEY (id),
	FOREIGN KEY (album_id) REFERENCES album (id),
	UNIQUE (id, track_number)
);

DROP INDEX track_name_idx IF EXISTS;
CREATE INDEX track_name_idx ON track (name);

CREATE TABLE user_group (
	id NUMERIC GENERATED ALWAYS AS IDENTITY (START WITH 1),
	name VARCHAR(64) NOT NULL,
	PRIMARY KEY (id)
);

DELETE FROM user_group;
INSERT INTO user_group (name) VALUES ('unauthenticated');
INSERT INTO user_group (name) VALUES ('authenticated');
INSERT INTO user_group (name) VALUES ('administrator');

CREATE TABLE user_account (
	id NUMERIC GENERATED ALWAYS AS IDENTITY (START WITH 1),
	login VARCHAR(16) NOT NULL,
	name VARCHAR(128) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (login)
);

CREATE TABLE user_group_map (
	user_id NUMERIC NOT NULL,
	group_id NUMERIC NOT NULL,
	PRIMARY KEY (user_id,  group_id),
	FOREIGN KEY (user_id) REFERENCES user_account (id),
	FOREIGN KEY (group_id) REFERENCES user_group (id)
);


