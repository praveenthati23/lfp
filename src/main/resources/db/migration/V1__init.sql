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

CREATE TABLE IF NOT EXISTS messengers (
	id serial PRIMARY KEY,
	messenger_for INT NOT NULL,
	first_name VARCHAR ( 255 ),
    last_name VARCHAR ( 255 ),
    email VARCHAR ( 255 ) NOT NULL,
    is_confirmed BOOLEAN,
    invitation_token    TEXT,
    messenger_user_id INT,
    custom_message TEXT,
    created_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP
);

CREATE TABLE IF NOT EXISTS death_report (
	id serial PRIMARY KEY,
	user_id INT NOT NULL,
	death_date DATE NOT NULL,
    attachment TEXT,
    attachment_filename VARCHAR ( 255 ),
    obituary_link TEXT,
    is_verified BOOLEAN,
    verified_by INT,
    verified_at TIMESTAMP,
    status VARCHAR ( 50 ),
    status_note TEXT,
    custom_note TEXT,
    created_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP
);

CREATE TABLE IF NOT EXISTS recipient (
	id serial PRIMARY KEY,
	user_id INT NOT NULL,
	first_name VARCHAR ( 255 ),
    last_name VARCHAR ( 255 ),
    email VARCHAR ( 255 ) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP,
    is_user_recipient BOOLEAN
);

CREATE TABLE IF NOT EXISTS Messages (
        id serial PRIMARY KEY,
        user_id INT NOT NULL,
	recipient_id INT,
	messenger_id INT,
	status VARCHAR ( 50 ),
	message_type VARCHAR ( 50 ) NOT NULL,
	title TEXT,
	description TEXT,
	content TEXT,
	file_name TEXT,
	deliver_on_death BOOLEAN,
	delivery_method VARCHAR ( 50 ),
	schedule_type VARCHAR ( 50 ) NOT NULL,
	delivery_date DATE,
    created_on TIMESTAMP NOT NULL,
    updated_on TIMESTAMP
);

ALTER TABLE Messages
ADD COLUMN event_title VARCHAR ( 255 );

CREATE TABLE IF NOT EXISTS shedlock (
  name VARCHAR(64),
  lock_until TIMESTAMP(3) NULL,
  locked_at TIMESTAMP(3) NULL,
  locked_by VARCHAR(255),
  PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS memorial (
  id serial PRIMARY KEY,
  user_id INT NOT NULL,
  background_image TEXT,
  headshot TEXT,
  epitaph TEXT,
  obituary TEXT,
  alias VARCHAR ( 255 ),
  is_tribute_page BOOLEAN,
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP
);