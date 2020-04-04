-------------------------------- DROP ORIGINAL TABLES --------------------------------
DROP TABLE APP_USER;

DROP TABLE USER_TYPE;

DROP TABLE USER_STATUS;

-------------------------------- CREATE NEW TABLES --------------------------------
CREATE TABLE USER_TYPE(
	user_type_id        VARCHAR(32)		NOT NULL PRIMARY KEY,
	name				VARCHAR(50)		NOT NULL UNIQUE
);

CREATE TABLE USER_STATUS(
    status_id   VARCHAR(32)		NOT NULL PRIMARY KEY,
	status      VARCHAR(32)		NOT NULL
);

CREATE TABLE APP_USER (
	appuser_id		 VARCHAR(32)		NOT NULL PRIMARY KEY,
	username		VARCHAR(50) 		NOT NULL UNIQUE,
	hash			VARCHAR(255)		NOT NULL,
	email			VARCHAR(50)		    NOT NULL UNIQUE,
	name			VARCHAR(100)		NOT NULL,
	last_names	    VARCHAR(100)		NOT NULL,
	adress			VARCHAR(100),
	phone			VARCHAR(100),
	description		VARCHAR(500),
    lastLogin		DATE,
	user_type_id    VARCHAR(32)	        NOT NULL,
	status_id       VARCHAR(32)	        NOT NULL,
    loginDelete     NUMERIC(1)          NOT NULL,
    MailConfirmed	NUMERIC(1)	        NOT NULL
);

ALTER TABLE APP_USER ADD CONSTRAINT user_type_fk FOREIGN KEY(user_type_id) REFERENCES USER_TYPE(user_type_id);
ALTER TABLE USER_STATUS ADD CONSTRAINT user_status_fk FOREIGN KEY(status_id) REFERENCES USER_STATUS(status_id);

--------------------------------  INSERT INITIAL DATA --------------------------------
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME) VALUES('ADM', 'ADMIN');
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME) VALUES('CLI', 'CLIENTE');
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME) VALUES('CAJ', 'CAJERO');
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME) VALUES('VEN', 'VENDEDOR');
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME) VALUES('SUP', 'SUPERVISOR');
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME) VALUES('MEC', 'MECANICO');
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME ) VALUES('TES', 'TESTING');

INSERT INTO USER_STATUS ( status_id, status) VALUES(1, 'Banned');
INSERT INTO USER_STATUS ( status_id, status) VALUES(2, 'Active');


INSERT INTO APP_USER( APPUSER_ID , USERNAME , HASH , EMAIL , NAME , LAST_NAMES , ADRESS , PHONE , USER_TYPE_ID, STATUS_ID,isDelete)
VALUES (SYS_GUID(), 'ADMIN_TEST_1', 'prueba1', 'ADMIN_TEST_1@MAIL.CL', 'NAME ADMIN', 'LAST NAME ADMIN', 'ADRESS TEST #88', '988888888', 
		1,1,0);
INSERT INTO APP_USER ( APPUSER_ID , USERNAME , HASH , EMAIL , NAME , LAST_NAMES , ADRESS , PHONE , USER_TYPE_ID, STATUS_ID,isDelete )
VALUES (SYS_GUID(), 'SUPERVISOR_TEST_1', 'prueba2', 'SUPERVISOR_TEST_1@MGAIL.CL', 'NAME SUPERVISOR', 'LAST NAME SUPERVISOR', 'ADRESS SUPERVISOR #11', '911111111',
		1,1,0);
INSERT INTO APP_USER ( APPUSER_ID , USERNAME , HASH , EMAIL , NAME , LAST_NAMES , ADRESS , PHONE , USER_TYPE_ID, STATUS_ID,isDelete )
VALUES (SYS_GUID(), 'CLIENTE_TEST_1', 'prueba3', 'CLIENTE_TEST_1@GMAIL.CL', 'NAME CLIENTE 1', 'LAST NAME CLIENTE 1', 'ADRESS CLIENTE #11', '911111111', 
		1,1,0);
INSERT INTO APP_USER ( APPUSER_ID , USERNAME , HASH , EMAIL , NAME , LAST_NAMES , ADRESS , PHONE , USER_TYPE_ID, STATUS_ID,isDelete )
VALUES (SYS_GUID(), 'CLIENTE_TEST_2', 'prueba4', 'CLIENTE_TEST_2@GMAIL.CL', 'NAME CLIENTE 2', 'LAST NAME CLIENTE 2', 'ADRESS CLIENTE #22', '922222222', 
		1,1,0);

commit;
--------------------------------  CHECK RESULTS  --------------------------------
SELECT *
FROM APP_USER U
JOIN USER_TYPE T ON T.USER_TYPE_ID = U.USER_TYPE_ID 

