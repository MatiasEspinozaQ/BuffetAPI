-------------------------------- DROP ORIGINAL TABLES --------------------------------
DROP TABLE APP_USER CASCADE CONSTRAINTS;

DROP TABLE USER_TYPE CASCADE CONSTRAINTS;

DROP TABLE USER_STATUS CASCADE CONSTRAINTS;

DROP TABLE VERIFICATIONTOKEN;
-------------------------------- CREATE NEW TABLES --------------------------------
CREATE TABLE USER_TYPE(
	user_type_id 	    VARCHAR(32)		        NOT NULL PRIMARY KEY,
	name				VARCHAR(50)		        NOT NULL UNIQUE,
	updated_at		    DATE					NOT NULL,
	deleted				NUMERIC(1)			    NOT NULL,
	created_at			DATE					NOT NULL
);

CREATE TABLE USER_STATUS(
    status_id   		VARCHAR(32)		        NOT NULL PRIMARY KEY,
	status      		VARCHAR(32)		    NOT NULL,
	
	updated_at		    DATE					    NOT NULL,
	deleted				NUMERIC(1)			    NOT NULL,
	created_at			DATE					NOT NULL
	
);

CREATE TABLE APP_USER (
	appuser_id		    VARCHAR(32)			    NOT NULL PRIMARY KEY,
	username			VARCHAR(50) 			NOT NULL UNIQUE,
	hash				VARCHAR(255)			NOT NULL,
	email				VARCHAR(50)			    NOT NULL UNIQUE,
	name				VARCHAR(100)			NOT NULL,
	last_names	        VARCHAR(100)			NOT NULL,
    rut                 VARCHAR(13)			    NOT NULL,
	adress				VARCHAR(100),
	phone				VARCHAR(100),
	birthday			DATE,
    lastLogin			DATE					NOT NULL,
    mail_confirmed	    NUMERIC(1)	        	    NOT NULL,
	user_type_id    	VARCHAR(32)	    	    NOT NULL,
	status_id       	VARCHAR(32)	    	    NOT NULL,
	updated_at		    DATE						NOT NULL,
	deleted				NUMERIC(1)				NOT NULL,
	created_at			DATE					NOT NULL
);

ALTER TABLE APP_USER ADD CONSTRAINT user_type_fk FOREIGN KEY(user_type_id) REFERENCES USER_TYPE(user_type_id);
ALTER TABLE APP_USER ADD CONSTRAINT user_status_fk FOREIGN KEY(status_id) REFERENCES USER_STATUS(status_id);

CREATE TABLE VERIFICATIONTOKEN (
	token				VARCHAR(32)			NOT NULL PRIMARY KEY,
	appuser_id		    VARCHAR(50) 		NOT NULL,
	expiry_date		    DATE				NOT NULL
);

ALTER TABLE VERIFICATIONTOKEN ADD CONSTRAINT VARIF_USER_FK FOREIGN KEY(appuser_id) REFERENCES APP_USER(appuser_id);


INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME, CREATED_AT , UPDATED_AT , DELETED ) VALUES('ADM', 'Administrador', SYSDATE, SYSDATE, 0);
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME, CREATED_AT , UPDATED_AT , DELETED) VALUES('CLI', 'Cliente', SYSDATE, SYSDATE, 0);
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME, CREATED_AT , UPDATED_AT , DELETED) VALUES('CAJ', 'Cajero', SYSDATE, SYSDATE, 0);
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME, CREATED_AT , UPDATED_AT , DELETED) VALUES('VEN', 'Vendedor', SYSDATE, SYSDATE, 0);
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME, CREATED_AT , UPDATED_AT , DELETED) VALUES('SUP', 'Supervisor', SYSDATE, SYSDATE, 0);
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME, CREATED_AT , UPDATED_AT , DELETED) VALUES('MEC', 'Mecánico', SYSDATE, SYSDATE, 0);
INSERT INTO USER_TYPE ( USER_TYPE_ID, NAME, CREATED_AT , UPDATED_AT , DELETED) VALUES('TES', 'Testing', SYSDATE, SYSDATE, 0);

INSERT INTO USER_STATUS ( STATUS_ID , STATUS , CREATED_AT , UPDATED_AT , DELETED) VALUES('BAN', 'Desactivado', SYSDATE, SYSDATE, 0);
INSERT INTO USER_STATUS ( STATUS_ID , STATUS , CREATED_AT , UPDATED_AT , DELETED) VALUES('ACT', 'Activo', SYSDATE, SYSDATE, 0);


-- ADMIN SEBA
INSERT INTO APP_USER (APPUSER_ID, USERNAME, HASH, EMAIL, NAME, LAST_NAMES, RUT, ADRESS, PHONE, BIRTHDAY, LASTLOGIN, MAIL_CONFIRMED, USER_TYPE_ID, STATUS_ID, UPDATED_AT, DELETED, CREATED_AT)
VALUES(
	SYS_GUID(), -- APPUSER_ID
	'sebaherrera', -- USERNAME
	'42437a69a8fd1d5f3a9d1a01f072187282a663cf327ffbf11412f25ff01cd29a', -- HASH : alfalfa8.
	'seherreraluna@gmail.cl', -- EMAIL
	'Sebastian', -- NAME
	'Herrera Luna', -- LAST_NAMES
    '18.594.857-9',--RUT
	'Calle los Michis #469', -- ADRESS
	'+56966666666', -- PHONE
	TO_DATE('1994/06/18 00:00:00', 'yyyy/mm/dd hh24:mi:ss'), -- BIRTHDAY
	TO_DATE('2020/04/10 20:36:20', 'yyyy/mm/dd hh24:mi:ss'), -- LASTLOGIN
	1, -- MAIL_CONFIRMED
	'ADM', -- USER_TYPE_ID
	'ACT', -- STATUS_ID
	TO_DATE('2020/03/20 09:02:11', 'yyyy/mm/dd hh24:mi:ss'), -- UPDATED_AT
	0, -- DELETED
	TO_DATE('2020/03/20 09:02:11', 'yyyy/mm/dd hh24:mi:ss') -- CREATED_AT
);

-- ADMIN MATI
INSERT INTO APP_USER (APPUSER_ID, USERNAME, HASH, EMAIL, NAME, LAST_NAMES,RUT, ADRESS, PHONE, BIRTHDAY, LASTLOGIN, MAIL_CONFIRMED, USER_TYPE_ID, STATUS_ID, UPDATED_AT, DELETED, CREATED_AT)
VALUES(
	SYS_GUID(), -- APPUSER_ID
	'matiespinoza', -- USERNAME
	'42437a69a8fd1d5f3a9d1a01f072187282a663cf327ffbf11412f25ff01cd29a', -- HASH : mati
	'mati.esp@gmail.cl', -- EMAIL
	'Matias', -- NAME
	'Espinoza', -- LAST_NAMES
    '19.387.716-8',--RUT
	'Calle Campo #69', -- ADRESS
	'+56955555555', -- PHONE
	TO_DATE('1996/05/20 00:00:00', 'yyyy/mm/dd hh24:mi:ss'), -- BIRTHDAY
	TO_DATE('2020/04/10 20:36:20', 'yyyy/mm/dd hh24:mi:ss'), -- LASTLOGIN
	1, -- MAIL_CONFIRMED
	'ADM', -- USER_TYPE_ID
	'ACT', -- STATUS_ID
	TO_DATE('2020/03/20 09:02:11', 'yyyy/mm/dd hh24:mi:ss'), -- UPDATED_AT
	0, -- DELETED
	TO_DATE('2020/03/20 09:02:11', 'yyyy/mm/dd hh24:mi:ss') -- CREATED_AT
);

-- ADMIN IRINA
INSERT INTO APP_USER (APPUSER_ID, USERNAME, HASH, EMAIL, NAME, LAST_NAMES, RUT, ADRESS, PHONE, BIRTHDAY, LASTLOGIN, MAIL_CONFIRMED, USER_TYPE_ID, STATUS_ID, UPDATED_AT, DELETED, CREATED_AT)
VALUES(
	SYS_GUID(), -- APPUSER_ID
	'irinmunoz', -- USERNAME
	'753923b3a91b753ca991116df61a90c06f97721277cb2829b21a4a209f7aa932', -- HASH : irin.munoz
	'irin.munoz@gmail.cl', -- EMAIL
	'Irina', -- NAME
	'Muñoz', -- LAST_NAMES
    '20.124.502-8',--RUT
	'Calle los Dogo #696', -- ADRESS
	'+5697777777', -- PHONE
	TO_DATE('1999/06/01 00:00:00', 'yyyy/mm/dd hh24:mi:ss'), -- BIRTHDAY
	TO_DATE('2020/04/01 20:36:20', 'yyyy/mm/dd hh24:mi:ss'), -- LASTLOGIN
	1, -- MAIL_CONFIRMED
	'ADM', -- USER_TYPE_ID
	'ACT', -- STATUS_ID
	TO_DATE('2020/03/20 09:02:11', 'yyyy/mm/dd hh24:mi:ss'), -- UPDATED_AT
	0, -- DELETED
	TO_DATE('2020/03/20 09:02:11', 'yyyy/mm/dd hh24:mi:ss') -- CREATED_AT
);


-- MECÁNICA CON MAIL CONFIRMADO
INSERT INTO APP_USER (APPUSER_ID, USERNAME, HASH, EMAIL, NAME, LAST_NAMES, RUT, ADRESS, PHONE, BIRTHDAY, LASTLOGIN, MAIL_CONFIRMED, USER_TYPE_ID, STATUS_ID, UPDATED_AT, DELETED, CREATED_AT)
VALUES(
	SYS_GUID(), -- APPUSER_ID
	'ReGarrido', -- USERNAME
	'55648da29f4d04cbddcd9f82f43c26068ffdc63749a6b2bda0b617ab8286dd61', -- HASH : Renata1659
	'rgarrido@gmail.cl', -- EMAIL
	'Renata', -- NAME
	'Garrido Gonzales', -- LAST_NAMES
    '17.359.326-4',--RUT
	'Calle Serrano #14', -- ADRESS
	'+56921659878', -- PHONE
	TO_DATE('1990/05/12 00:00:00', 'yyyy/mm/dd hh24:mi:ss'), -- BIRTHDAY
	TO_DATE('2020/04/09 18:02:43', 'yyyy/mm/dd hh24:mi:ss'), -- LASTLOGIN
	1, -- MAIL_CONFIRMED
	'MEC', -- USER_TYPE_ID
	'ACT', -- STATUS_ID
	TO_DATE('2020/02/18 17:08:11', 'yyyy/mm/dd hh24:mi:ss'), -- UPDATED_AT
	0, -- DELETED
	TO_DATE('2020/02/18 17:08:11', 'yyyy/mm/dd hh24:mi:ss') -- CREATED_AT
);


-- MECÁNICA CON MAIL SIN CONFIRMAR
INSERT INTO APP_USER (APPUSER_ID, USERNAME, HASH, EMAIL, NAME, LAST_NAMES, RUT, ADRESS, PHONE, BIRTHDAY, LASTLOGIN, MAIL_CONFIRMED, USER_TYPE_ID, STATUS_ID, UPDATED_AT, DELETED, CREATED_AT)
VALUES(
	SYS_GUID(), -- APPUSER_ID
	'EdMartinez', -- USERNAME
	'512d71cd1dd152f74cee02d2e6b1be673291529c1557557f97a02ec2f8cc5a58', -- HASH : Renata1659
	'edu.martinez@gmail.cl', -- EMAIL
	'Eduardo', -- NAME
	'Martinez', -- LAST_NAMES
    '15.684.326-0',--RUT
	'Calle las Ilusiones #1965', -- ADRESS
	'+56965987845', -- PHONE
	TO_DATE('1985/02/28 00:00:00', 'yyyy/mm/dd hh24:mi:ss'), -- BIRTHDAY
	TO_DATE('2020/03/25 18:02:43', 'yyyy/mm/dd hh24:mi:ss'), -- LASTLOGIN
	0, -- MAIL_CONFIRMED
	'MEC', -- USER_TYPE_ID
	'ACT', -- STATUS_ID
	TO_DATE('2020/03/25 17:08:11', 'yyyy/mm/dd hh24:mi:ss'), -- UPDATED_AT
	0, -- DELETED
	TO_DATE('2020/03/25 17:08:11', 'yyyy/mm/dd hh24:mi:ss') -- CREATED_AT
);
--MECÁNICO PUBLICACIÓN 1
INSERT INTO APP_USER (APPUSER_ID, USERNAME, HASH, EMAIL, NAME, LAST_NAMES, RUT, ADRESS, PHONE, BIRTHDAY, LASTLOGIN, MAIL_CONFIRMED, USER_TYPE_ID, STATUS_ID, UPDATED_AT, DELETED, CREATED_AT)
VALUES(
	'7CD5B7769DF75CEFE034080020825436', -- APPUSER_ID
	'AutSuzuki', -- USERNAME
	'b905fd76b66e4d0a108d603e27c86989a6b079e3bee1787755929e1ecc2fb359', -- HASH : suzukimotriz
	'automotriz.contacto@gmail.cl', -- EMAIL
	'Raul', -- NAME
	'Cerda Martinez', -- LAST_NAMES
    '12.035.261-5',--RUT
	'Calle los Cardenales #14', -- ADRESS
	'+56921659878', -- PHONE
	TO_DATE('1976/11/02 00:00:00', 'yyyy/mm/dd hh24:mi:ss'), -- BIRTHDAY
	TO_DATE('2020/04/19 12:02:43', 'yyyy/mm/dd hh24:mi:ss'), -- LASTLOGIN
	1, -- MAIL_CONFIRMED
	'MEC', -- USER_TYPE_ID
	'ACT', -- STATUS_ID
	TO_DATE('2020/04/05 19:08:11', 'yyyy/mm/dd hh24:mi:ss'), -- UPDATED_AT
	0, -- DELETED
	TO_DATE('2020/04/05 19:08:11', 'yyyy/mm/dd hh24:mi:ss') -- CREATED_AT
);

--MECÁNICO PUBLICACIÓN 2
INSERT INTO APP_USER (APPUSER_ID, USERNAME, HASH, EMAIL, NAME, LAST_NAMES, RUT, ADRESS, PHONE, BIRTHDAY, LASTLOGIN, MAIL_CONFIRMED, USER_TYPE_ID, STATUS_ID, UPDATED_AT, DELETED, CREATED_AT)
VALUES(
	'7CD5B7769DF85CEFE034080020825436', -- APPUSER_ID
	'KaiserMecanic', -- USERNAME
	'4220bb5fbeae8daa7d1b1b350d55af45a6b6b53488db5ff1ebfb2bc8f778b423', -- HASH : Kaiser1991
	'Kaiser1991@gmail.cl', -- EMAIL
	'Ariana', -- NAME
	'Fernandez Ortega', -- LAST_NAMES
    '18.215.891-k',--RUT
	'Villa san Matin, Melipilla', -- ADRESS
	'+56924521786', -- PHONE
	TO_DATE('1991/01/25 00:00:00', 'yyyy/mm/dd hh24:mi:ss'), -- BIRTHDAY
	TO_DATE('2020/04/18 12:03:26', 'yyyy/mm/dd hh24:mi:ss'), -- LASTLOGIN
	1, -- MAIL_CONFIRMED
	'MEC', -- USER_TYPE_ID
	'ACT', -- STATUS_ID
	TO_DATE('2020/04/18 12:02:43', 'yyyy/mm/dd hh24:mi:ss'), -- UPDATED_AT
	0, -- DELETED
	TO_DATE('2020/04/05 12:02:43', 'yyyy/mm/dd hh24:mi:ss') -- CREATED_AT
);



-----------------------------------------------------o-----------------------------------------------------
--                                              PUBLICACIONES
-----------------------------------------------------o-----------------------------------------------------
drop table PUBLICATION CASCADE CONSTRAINTS;

drop table PUBLIC_STATUS;

CREATE TABLE PUBLICATION(
    public_id		        VARCHAR(32)		NOT NULL PRIMARY KEY,
    appuser_id              VARCHAR(32)		NOT NULL,
    user_type_id            VARCHAR(32)		NOT NULL,
    public_status_id        VARCHAR(32)		NOT NULL,
    created_at               DATE,
    updated_at               DATE,
    deleted                 numeric(1),
    title                   VARCHAR(50)		NOT NULL,
    public_desc             VARCHAR2(500)	NOT NULL,
    schedule                VARCHAR(50)            NOT NULL,
    services                VARCHAR2(500)	NOT NULL,
    bussiness_name          VARCHAR(50),
    address                  VARCHAR(100)	NOT NULL,
    comuna                  VARCHAR(100)	NOT NULL,
    region                  VARCHAR(100)	NOT NULL,
    landline                VARCHAR(32),
    mobile_number           VARCHAR(32),
    email                   VARCHAR(40)		NOT NULL,
    views                   NUMERIC(10)     NOT NULL
);


CREATE TABLE PUBLIC_STATUS(
    public_status_id		VARCHAR(32)		NOT NULL PRIMARY KEY,
    status_name             VARCHAR(32)		NOT NULL,
    created_at               DATE,
    updated_at               DATE,
    deleted                 NUMERIC(1)
);

--CONSTRAINT
ALTER TABLE PUBLICATION ADD CONSTRAINT appuser_id_fk FOREIGN KEY(appuser_id) REFERENCES APP_USER(appuser_id);
ALTER TABLE PUBLICATION ADD CONSTRAINT user_type_id_fk FOREIGN KEY(user_type_id) REFERENCES USER_TYPE(user_type_id);

--VERIFICACION DE USER_TYPE MECANICO
alter table PUBLICATION
add constraint mechanic_verify
check (user_type_id='MEC');

--INSERT TABLA PUBLIC_STATUS
insert into PUBLIC_STATUS values('PEN','Pendiente', to_date('11-04-2020','DD-MM-YYYY '),to_date ('11-04-2020','DD-MM-YYYY'),0);--pending
insert into PUBLIC_STATUS values('ACT','Activo',    to_date('11-04-2020','DD-MM-YYYY '),to_date ('11-04-2020','DD-MM-YYYY'),0);--active
insert into PUBLIC_STATUS values('DEB','Con deuda', to_date('11-04-2020','DD-MM-YYYY '),to_date ('11-04-2020','DD-MM-YYYY'),0);--debt(MOROSA)
insert into PUBLIC_STATUS values('INA','Inactivo',  to_date('11-04-2020','DD-MM-YYYY '),to_date ('11-04-2020','DD-MM-YYYY'),0);--unactive

--INSERT TABLA PUBLICATION
insert into PUBLICATION --publicacion 1
(public_id,appuser_id,user_type_id,public_status_id,created_at,updated_at,deleted,title,public_desc,schedule,services,bussiness_name,address,comuna,region,landline,mobile_number,email,views)
values
    (SYS_GUID(),--public_id
    '7CD5B7769DF75CEFE034080020825436',--mecha_id
    'MEC',--user_type_id
    'ACT',--public_status_id
    to_date ('13-04-2020 10:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('13-04-2020 10:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0,--deleted
    'Automotriz J.D.C Suzuki',--title
    'Servicio Suzuki.Chevrolet,Mazda, Mantenciones de kilometraje y desabolladura.Personal especializado en las marcas.Garantía por trabajos realizados.20 años de experiencia en el rubro.',--public_desc
    'De 8:30 a 19:30, abierto toda la semana',--schedule
    'Mecánica general, Mantenciones preventivas, Alineación, Balanceo, Frenos, Embrague, Afinamiento, Scanner, Limpieza de tapiz, Cambio de aceite, Tren delantero, Emergencia 24 horas,
    Simunizado y encerado, Diagnostico Computarizado',--services
    'Automotriz J.D.C',--bussiness_name
    'Calle Fernández Concha 146, Ñuñoa, Región Metropolitana',--adress
    'Ñuñoa',--comuna
    'Metropolitana',--region
    '(2)22744400',--landline
    '0',--mobile_number
    'jdc.contacto@gmail.com',--email
    5--views
);

insert into PUBLICATION  --publicacion 2
(public_id,appuser_id,user_type_id,public_status_id,created_at,updated_at,deleted,title,public_desc,schedule,services,bussiness_name,address,comuna,region,landline,mobile_number,email,views)
values
    (SYS_GUID(),--public_id
    '7CD5B7769DF85CEFE034080020825436',--mecha_id
    'MEC',--user_type_id
    'ACT',--public_status_id
    to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0,--deleted
    'Kaiser Lubricentro',--title
    'Kaiser Lubricentro un taller automotriz dedicado a dar servicios de lubricación en general tanto para vehículos bencineros como diesel. cambiamos el aceite de motor, caja de cambio, mecánica o automática, diferenciales, cajas de transparencias, dirección, hidráulica, etc.',--public_desc
    'De 9:30 a 18:30, de lunes a viernes',--schedule
    'CAMBIO DE ACEITE,El cambio de aceite se debe realizar cada 5.000 kms o cada 10.000 kms dependiento del aceite que se utilice.
     AFINAMIENTO, El afinamiento se debe realizar cada 15.000 o 20.000 kms. (Una vez al año aproximadamente)
     MECANICA, Mecánica express consiste en trabajos que tardan desde 2 hrs. a 1 día o 2 dependiendo de lo que necesite.
     ACCESORIOS,Accesorios para diferentes cosas del auto, dependiendo de lo que necesite.',--services
    'Kaiser Lubricentro',--bussiness_name
    'Avenida Colón 2878,Valparaíso, Región V',--adress
    'Valparaíso',--comuna
    'Región V',--region
    '(32)2492905',--landline
    '0',--mobile_number
    'KaiserContacto@gmail.com',--email
    65--views
);


-----------------------------------------------------o-----------------------------------------------------
--                                              PRESTACIONES/SERVICIOS
-----------------------------------------------------o-----------------------------------------------------

DROP TABLE SERVICE CASCADE CONSTRAINTS;

DROP TABLE SERVICE_STATUS;

CREATE TABLE SERVICE (
serv_id	            VARCHAR(32)		NOT NULL PRIMARY KEY,
name                VARCHAR(120)     NOT NULL,
price               NUMERIC(15)     NOT NULL,
serv_desc           VARCHAR2(400)   NOT NULL,
estimated_time      NUMERIC(4)     NOT NULL,
serv_status         VARCHAR(32)		NOT NULL,
created_at           DATE,
updated_at           DATE,
deleted             NUMERIC(1) NOT NULL
);

CREATE TABLE SERVICE_STATUS (
status_id	        VARCHAR(32)		NOT NULL PRIMARY KEY,
status              VARCHAR(64)     NOT NULL,
created_at           DATE,
updated_at           DATE,
deleted             numeric(1) NOT NULL
);

ALTER TABLE SERVICE ADD CONSTRAINT serv_status_fk FOREIGN KEY(serv_status) REFERENCES SERVICE_STATUS(status_id);

insert into SERVICE_STATUS values('ACT','Activo',   to_date ('20-04-2020','DD-MM-YYYY '),to_date ('20-04-2020','DD-MM-YYYY'),0);--active
insert into SERVICE_STATUS values('INA','Inactivo', to_date ('20-04-2020','DD-MM-YYYY '),to_date ('20-04-2020','DD-MM-YYYY'),0);--unactive

--insert tabla service
--servicio cambio de aceite 
INSERT INTO SERVICE
(serv_id,name,price,serv_desc,estimated_time,serv_status,created_at,updated_at,deleted) 
VALUES('09F686761827CF8AE040578CB20B7491',--serv_id
'Cambio De Aceite',--serv_name
28000,--serv_price
'Utilizamos las mejores marcas para el cambio de aceite de su vehículo',--serv_desc
28,--estimated_time
'ACT',--serv_status
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

--servicio accesorio
INSERT INTO SERVICE
(serv_id,name,price,serv_desc,estimated_time,serv_status,created_at,updated_at,deleted) 
VALUES('09F686761828CF8AE040578CB20B7491',--serv_id
'Correas Accesorio',--serv_name
40000,--serv_price
'Utilizamos las mejores marcas para el cambio de correas y accesorios de su vehículo',--serv_desc
28,--estimated_time
'ACT',--serv_status
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

--servicio mano de obra
INSERT INTO SERVICE
(serv_id,name,price,serv_desc,estimated_time,serv_status,created_at,updated_at,deleted) 
VALUES('09F686761829CF8AE040578CB20B7491',--serv_id
'Desabolladura y Pintura',--serv_name
55000,--serv_price
'Brindamos una solución integral de desabolladura y pintura en tiempo record. Reparamos pintura, abollones, rayas, piquetes y medianas colisiones.',--serv_desc
45,--estimated_time
'ACT',--serv_status
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

--servicio repuestos
INSERT INTO SERVICE
(serv_id,name,price,serv_desc,estimated_time,serv_status,created_at,updated_at,deleted) 
VALUES('09F68676182ACF8AE040578CB20B7491',--serv_id
'Cambio de neumáticos',--serv_name
30000,--serv_price
'Utilizamos las mejores marcas en neumáticos para cambiarselos a tu coche',--serv_desc
120,--estimated_time
'ACT',--serv_status
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

-----------------------------------------------------o-----------------------------------------------------
--                                              PRESTACIONES/PRODUCTOS
-----------------------------------------------------o-----------------------------------------------------
DROP TABLE PRODUCT CASCADE CONSTRAINTS;

DROP TABLE PRODUCT_STATUS;

DROP TABLE UNIT;

CREATE TABLE PRODUCT(
product_id	        VARCHAR(32)		NOT NULL PRIMARY KEY,
name                VARCHAR(120)     NOT NULL,
product_desc        VARCHAR2(300)    NOT NULL,
price               NUMERIC(15)     NOT NULL,
stock               NUMERIC(12)     NOT NULL,
stock_alert         NUMERIC(12)     NOT NULL,
brand               VARCHAR(120)    NOT NULL,
unit_id             VARCHAR(12)     NOT NULL,
product_status      VARCHAR(32)		NOT NULL,
created_at           DATE,
updated_at           DATE,
deleted             numeric(1) NOT NULL
);

CREATE TABLE PRODUCT_STATUS (
status_id	        VARCHAR(32)		NOT NULL PRIMARY KEY,
status              VARCHAR(64)     NOT NULL,
created_at           DATE,
updated_at           DATE,
deleted             numeric(1) NOT NULL
);

CREATE TABLE UNIT(
abbreviation        VARCHAR(32)		NOT NULL PRIMARY KEY,
name                VARCHAR(32)		NOT NULL,
plural_name         VARCHAR(32)		NOT NULL,
created_at           DATE,
updated_at           DATE,
deleted             numeric(1) NOT NULL
);

ALTER TABLE PRODUCT ADD CONSTRAINT product_status_fk FOREIGN KEY(product_status) REFERENCES PRODUCT_STATUS(status_id);
ALTER TABLE PRODUCT ADD CONSTRAINT unit_id_fk FOREIGN KEY(unit_id) REFERENCES UNIT(abbreviation);

insert into PRODUCT_STATUS values('ACT','Activo',to_date ('21-04-2020','DD-MM-YYYY '),to_date ('21-04-2020','DD-MM-YYYY'),0);--active
insert into PRODUCT_STATUS values('INA','Inactivo',to_date ('21-04-2020','DD-MM-YYYY '),to_date ('21-04-2020','DD-MM-YYYY'),0);--unactive

--insert tabla UNIT
INSERT INTO UNIT (abbreviation, name, plural_name,created_at,updated_at,deleted) 
VALUES(
'Lt',--abbreviation
'Litro',--name
'Litros',--plural_name
to_date ('24-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('24-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

INSERT INTO UNIT (abbreviation, name, plural_name,created_at,updated_at,deleted) 
VALUES(
'Uni',--abbreviation
'Unidad',--name
'Unidades',--plural_name
to_date ('24-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('24-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

INSERT INTO UNIT (abbreviation, name, plural_name,created_at,updated_at,deleted) 
VALUES(
'Kg',--abbreviation
'Kilo',--name
'Kilos',--plural_name
to_date ('24-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('24-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

--insert tabla PRODUCT
INSERT INTO PRODUCT (product_id,name,product_desc,price,stock,stock_alert,brand,unit_id,product_status,created_at,updated_at,deleted)
VALUES(
SYS_GUID(),--product_id	        
'Aceite de motor CASTROL EDGE',--name                
'Castrol EDGE es un aceite para motor totalmente sintético desarrollado para conductores que solo quieren lo mejor de sus motores.',--product_desc        
15000,--price
15,--stock 
5,--stock_alert
'CASTROL EDGE',--brand               
'Lt',--unit_id             
'ACT',--product_status      
to_date ('21-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at           
to_date ('21-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at           
0--deleted 
);


INSERT INTO PRODUCT (product_id,name,product_desc,price,stock,stock_alert,brand,unit_id,product_status,created_at,updated_at,deleted)
VALUES(
SYS_GUID(),--product_id	        
'Neumáticos TURANZA ER33',--name                
'A los coches de lujo les vendrá como anillo al dedo el Bridgestone ER33, un neumático creado especialmente para este tipo de automóviles gracias a su alto rendimiento.',--product_desc        
35000,--price
20,--stock   
12,--stock_alert            
'BRIDGESTONE',--brand               
'Uni',--unit_id             
'ACT',--product_status      
to_date ('24-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at           
to_date ('24-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at           
0--deleted 
);


commit;