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