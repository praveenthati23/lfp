CREATE TABLE IF NOT EXISTS presigned_url_tbl
(
	id			SERIAL 	PRIMARY KEY,
	directory		varchar, 
	file_type		varchar,
	name			varchar,
	size			integer,
	extension		varchar,
	key			varchar,
	created_at	 	timestamp without time zone,
	modified_at 		timestamp without time zone
);

CREATE TABLE IF NOT EXISTS addresses (
	id serial PRIMARY KEY,
	address TEXT,
	country_id INT,
	city VARCHAR ( 100 ),
    state VARCHAR ( 100 ),
    zip VARCHAR ( 25 )
);

CREATE TABLE IF NOT EXISTS Users (
	id serial PRIMARY KEY,
	google_open_id VARCHAR ( 255 )  UNIQUE,
	iam_id VARCHAR ( 255 )  UNIQUE,
	first_name VARCHAR ( 255 ),
    last_name VARCHAR ( 255 ),
	birth_date DATE,
	gender VARCHAR ( 50 ),
	photo_url TEXT,
    facebook_url TEXT,
    x_url TEXT,
    insta_url TEXT,
    tiktok_url TEXT,
    deceased BOOLEAN,
    death_date DATE,
    is_first_letter_created BOOLEAN,
    is_first_video_created BOOLEAN,
    is_first_audio_created BOOLEAN,
    has_written BOOLEAN,
    address_id BIGINT,
    birth_address_id BIGINT,
    contact_number VARCHAR ( 25 ),
    secondary_email VARCHAR ( 255 ),
    created_on TIMESTAMP NOT NULL,
	updated_on TIMESTAMP,
	email VARCHAR ( 255 )  UNIQUE NOT NULL,
	email_verified BOOLEAN NOT NULL,
    status BOOLEAN,
    is_trustor BOOLEAN,
    last_login TIMESTAMP,
    role_id INT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE,
    FOREIGN KEY (birth_address_id) REFERENCES addresses(id) ON DELETE CASCADE
);

ALTER TABLE users
RENAME COLUMN is_trustor TO is_messenger;