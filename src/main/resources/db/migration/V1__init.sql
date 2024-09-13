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

CREATE TABLE IF NOT EXISTS photos (
  id serial PRIMARY KEY,
  user_id INT NOT NULL,
  filename VARCHAR ( 255 ),
  alt_text VARCHAR ( 255 ),
  caption VARCHAR ( 255 ),
  sort_order INT,
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP
);


CREATE TABLE IF NOT EXISTS subscription (
  id serial PRIMARY KEY,
  name VARCHAR ( 255 )  NOT NULL,
  payment_id INT,
  plan_id  INT  NOT NULL,
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP
);

INSERT INTO subscription(name, payment_id, plan_id, created_on) VALUES ('Freemium', 0, 1, CURRENT_TIMESTAMP);
INSERT INTO subscription(name, payment_id, plan_id, created_on) VALUES ('Legacy Edition', 2, 2, CURRENT_TIMESTAMP);

CREATE TABLE IF NOT EXISTS plan (
  id serial PRIMARY KEY,
  name VARCHAR ( 255 )  NOT NULL,
  extras JSONB,
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP
);

INSERT INTO plan(name, extras, created_on) VALUES ('Freemium', '{"photos":{"dataCountLimit":-1,"uploadSizeLimit":5},"lastAudios":{"lengthLimit":3,"dataCountLimit":3,"uploadSizeLimit":25},"lastVideos":{"lengthLimit":2,"dataCountLimit":3,"uploadSizeLimit":50},"lastLetters":{"dataCountLimit":3,"uploadSizeLimit":5},"memorialPage":{"headshot":{"uploadSizeLimit":-1},"coverPhoto":{"uploadSizeLimit":-1}}}', CURRENT_TIMESTAMP);
INSERT INTO plan(name, extras, created_on) VALUES ('Legacy Edition', '{"photos":{"dataCountLimit":-1,"uploadSizeLimit":5},"lastAudios":{"lengthLimit":10,"dataCountLimit":10,"uploadSizeLimit":25},"lastVideos":{"lengthLimit":10,"dataCountLimit":10,"uploadSizeLimit":50},"lastLetters":{"dataCountLimit":-1,"uploadSizeLimit":5},"memorialPage":{"headshot":{"uploadSizeLimit":-1},"coverPhoto":{"uploadSizeLimit":-1}}}', CURRENT_TIMESTAMP);


CREATE TABLE IF NOT EXISTS feature (
  id serial PRIMARY KEY,
  name VARCHAR ( 255 )  NOT NULL,
  extras JSONB,
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP
);

INSERT INTO feature(name, extras, created_on) VALUES ('last_letters',null,CURRENT_TIMESTAMP);
INSERT INTO feature(name, extras, created_on) VALUES ('last_videos',null,CURRENT_TIMESTAMP);
INSERT INTO feature(name, extras, created_on) VALUES ('last_audios',null,CURRENT_TIMESTAMP);
INSERT INTO feature(name, extras, created_on) VALUES ('messengers',null,CURRENT_TIMESTAMP);
INSERT INTO feature(name, extras, created_on) VALUES ('memorial_page',null,CURRENT_TIMESTAMP);
INSERT INTO feature(name, extras, created_on) VALUES ('photos',null,CURRENT_TIMESTAMP);

CREATE TABLE IF NOT EXISTS plan_feature (
  plan_id INT NOT NULL,
  feature_id INT NOT NULL
);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (1,1);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (1,2);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (1,3);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (1,4);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (1,5);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (1,6);

INSERT INTO plan_feature(plan_id,feature_id) VALUES (2,1);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (2,2);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (2,3);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (2,4);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (2,5);
INSERT INTO plan_feature(plan_id,feature_id) VALUES (2,6);


ALTER TABLE Users
ADD COLUMN subscription_id INT DEFAULT 1;


ALTER TABLE recipient ALTER COLUMN email DROP NOT NULL;

------- DO NOT USE YET ---------
CREATE TABLE IF NOT EXISTS playlists (
  id serial PRIMARY KEY,
  user_id INT NOT NULL,
  name VARCHAR ( 255 ),
  external_id VARCHAR ( 255 ),
  image_url TEXT,
  duration_ms BIGINT,
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP
);
--------------------------------

