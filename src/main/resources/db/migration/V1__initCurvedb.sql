CREATE SEQUENCE IF NOT EXISTS user_sequence START 2 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS user_role_sequence START 2 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS company_sequence START 1 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS vpn_client_sequence START 1 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS vpn_server_sequence START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS user_app(
	id     		INTEGER NOT NULL CONSTRAINT user_pkey PRIMARY KEY,
    user_name   VARCHAR(80) NOT NULL UNIQUE,
	password 	VARCHAR(80) NOT NULL CONSTRAINT password_valid CHECK(CHARACTER_LENGTH(password)>7),
    is_active 	BOOLEAN DEFAULT FALSE NOT NULL
);
CREATE TABLE IF NOT EXISTS user_role(
	id     		INTEGER NOT NULL CONSTRAINT role_pkey PRIMARY KEY,
	role_name   VARCHAR(80) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS user_role_user(
	user_app 		INTEGER REFERENCES user_app(id)
					MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	user_role 		INTEGER REFERENCES user_role(id)
					MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE TABLE IF NOT EXISTS company(
	id     			INTEGER NOT NULL CONSTRAINT company_pkey PRIMARY KEY,
	company_name   	VARCHAR(80) NOT NULL UNIQUE,
    network   	    VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS vpn_server(
	id     			INTEGER NOT NULL CONSTRAINT vpn_server_pkey PRIMARY KEY,
	servername		VARCHAR(80) NOT NULL UNIQUE,
	endpoint 		VARCHAR(50) NOT NULL UNIQUE,
	public_key 		VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS vpn_client(
	id     			INTEGER NOT NULL CONSTRAINT vpn_client_pkey PRIMARY KEY,
	client_name   	VARCHAR(80) NOT NULL,
	allowed_address VARCHAR(20) NOT NULL,
	private_key 	VARCHAR(100),
	public_key 		VARCHAR(100),
	preshared_key 	VARCHAR(100),
    date_generate   timestamp,
    allowed_ips 	VARCHAR(300) NOT NULL,
	vpn_server 		INTEGER REFERENCES vpn_server(id)
					MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	company 		INTEGER REFERENCES company(id)
					MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS tasks(
      id     			INTEGER NOT NULL CONSTRAINT tasks_pkey PRIMARY KEY,
      task_text   	    VARCHAR(300) NOT NULL,
      date_generate     timestamp NOT NULL,
      date_execution    timestamp,
      is_active   	    BOOLEAN NOT NULL default true
);

INSERT INTO user_app VALUES(1, 'admin', 'admin2023', true);
INSERT INTO user_role VALUES(1, 'administrator');
INSERT INTO user_role_user VALUES(1, 1);
