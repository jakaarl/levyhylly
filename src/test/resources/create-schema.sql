DROP TABLE track IF EXISTS;
DROP TABLE album IF EXISTS;
DROP TABLE artist IF EXISTS;

CREATE TABLE artist (
	id NUMERIC GENERATED ALWAYS AS IDENTITY (START WITH 1),
	name VARCHAR(128) NOT NULL,
	PRIMARY KEY (id)
);

DROP INDEX  artist_name_idx IF EXISTS;
CREATE INDEX artist_name_idx ON artist (name);

CREATE TABLE album (
	id NUMERIC GENERATED ALWAYS AS IDENTITY (START WITH 1),
	name VARCHAR(128) NOT NULL,
	year SMALLINT,
	artist_id NUMERIC,
	PRIMARY KEY (id),
	FOREIGN KEY (artist_id) REFERENCES artist (id)
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
