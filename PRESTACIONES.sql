DROP TABLE SERVICE;

DROP TABLE SERVICE_STATUS;

DROP TABLE PRODUCT;

DROP TABLE PRODUCT_STATUS;

CREATE TABLE SERVICE (
serv_id	            VARCHAR(32)		NOT NULL PRIMARY KEY,
name                VARCHAR(120)     NOT NULL,
price               NUMERIC(15)     NOT NULL,
serv_desc               VARCHAR2(400)   NOT NULL,
estimated_time      NUMERIC(4,2)     NOT NULL,
serv_status      VARCHAR(32)		NOT NULL,
create_at           DATE,
update_at           DATE,
deleted             NUMERIC(1) NOT NULL
);

CREATE TABLE SERVICE_STATUS (
status_id	        VARCHAR(32)		NOT NULL PRIMARY KEY,
status              VARCHAR(64)     NOT NULL,
create_at           DATE,
update_at           DATE,
deleted             numeric(1) NOT NULL
);

ALTER TABLE SERVICE ADD CONSTRAINT serv_status_fk FOREIGN KEY(serv_status) REFERENCES SERVICE_STATUS(status_id);

CREATE TABLE PRODUCT(
product_id	        VARCHAR(32)		NOT NULL PRIMARY KEY,
name                VARCHAR(120)     NOT NULL,
product_desc        VARCHAR2(300)    NOT NULL,
stock               NUMERIC(12)     NOT NULL,
brand               VARCHAR(120)    NOT NULL,
measurement         VARCHAR(12)     NOT NULL,
product_status      VARCHAR(32)		NOT NULL,
create_at           DATE,
update_at           DATE,
deleted             numeric(1) NOT NULL
);

CREATE TABLE PRODUCT_STATUS (
status_id	        VARCHAR(32)		NOT NULL PRIMARY KEY,
status              VARCHAR(64)     NOT NULL,
create_at           DATE,
update_at           DATE,
deleted             numeric(1) NOT NULL
);

CREATE TABLE UNIT(
abbreviation        VARCHAR(32)		NOT NULL PRIMARY KEY,
name                VARCHAR(32)		NOT NULL,
plural_name         VARCHAR(32)		NOT NULL
);

ALTER TABLE PRODUCT ADD CONSTRAINT product_status_fk FOREIGN KEY(product_status) REFERENCES PRODUCT_STATUS(status_id);
ALTER TABLE PRODUCT ADD CONSTRAINT measurement_fk FOREIGN KEY(measurement) REFERENCES UNIT(abbreviation);

----------------------------:3----------------------------
--insert tabla SERVICE_STATUS
insert into SERVICE_STATUS values('ACT','ACTIVE',to_date ('20-04-2020','DD-MM-YYYY '),to_date ('20-04-2020','DD-MM-YYYY'),0);--active
insert into SERVICE_STATUS values('INA','INACTIVE',to_date ('20-04-2020','DD-MM-YYYY '),to_date ('20-04-2020','DD-MM-YYYY'),0);--unactive

--insert tabla service
--servicio cambio de aceite 
INSERT INTO SERVICE
(serv_id,name,price,serv_desc,estimated_time,serv_status,create_at,update_at,deleted) 
VALUES('09F686761827CF8AE040578CB20B7491',--serv_id
'Cambio De Aceite',--serv_name
28000,--serv_price
'Utilizamos las mejores marcas para el cambio de aceite de su vehículo',--serv_desc
00.28,--estimated_time
'ACT',--serv_status
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

--servicio accesorio
INSERT INTO SERVICE
(serv_id,name,price,serv_desc,estimated_time,serv_status,create_at,update_at,deleted) 
VALUES('09F686761828CF8AE040578CB20B7491',--serv_id
'Correas Accesorio',--serv_name
40000,--serv_price
'Utilizamos las mejores marcas para el cambio de correas y accesorios de su vehículo',--serv_desc
00.28,--estimated_time
'ACT',--serv_status
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

--servicio mano de obra
INSERT INTO SERVICE
(serv_id,name,price,serv_desc,estimated_time,serv_status,create_at,update_at,deleted) 
VALUES('09F686761829CF8AE040578CB20B7491',--serv_id
'Desabolladura y Pintura',--serv_name
55000,--serv_price
'Brindamos una solución integral de desabolladura y pintura en tiempo record. Reparamos pintura, abollones, rayas, piquetes y medianas colisiones.',--serv_desc
00.45,--estimated_time
'ACT',--serv_status
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);

--servicio repuestos
INSERT INTO SERVICE
(serv_id,name,price,serv_desc,estimated_time,serv_status,create_at,update_at,deleted) 
VALUES('09F68676182ACF8AE040578CB20B7491',--serv_id
'Correas Accesorio',--serv_name
40000,--serv_price
'Utilizamos las mejores marcas para el cambio de correas y accesorios de su vehículo',--serv_desc
00.28,--estimated_time
'ACT',--serv_status
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
0--deleted
);
----------------------------:3----------------------------
--insert tabla PRODUCT_STATUS
insert into PRODUCT_STATUS values('ACT','ACTIVE',to_date ('21-04-2020','DD-MM-YYYY '),to_date ('21-04-2020','DD-MM-YYYY'),0);--active
insert into PRODUCT_STATUS values('INA','INACTIVE',to_date ('21-04-2020','DD-MM-YYYY '),to_date ('21-04-2020','DD-MM-YYYY'),0);--unactive

--insert tabla PRODUCT
INSERT INTO PRODUCT (product_id,name,product_desc,stock,brand,weight,product_status,create_at,update_at,deleted)
VALUES(
SYS_GUID(),--product_id	        
'Aceite de motor CASTROL EDGE',--name                
'Castrol EDGE es un aceite para motor totalmente sintético desarrollado para conductores que solo quieren lo mejor de sus motores.',--product_desc        
15,--stock               
'CASTROL EDGE',--brand               
'L',--measurement             
'ACT',--product_status      
to_date ('21-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at           
to_date ('21-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at           
0--deleted 
);

----------------------------:3----------------------------
--insert tabla UNIT
INSERT INTO UNIT (abbreviation, name, plural_name) 
VALUES(
'L',--abbreviation
'Litro',--name
'Litros'--plural_name
);

INSERT INTO UNIT (abbreviation, name, plural_name) 
VALUES(
'U',--abbreviation
'Unidad',--name
'Unidades'--plural_name
);

INSERT INTO UNIT (abbreviation, name, plural_name) 
VALUES(
'K',--abbreviation
'Kilo',--name
'Kilos'--plural_name
);

