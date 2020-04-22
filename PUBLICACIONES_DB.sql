drop table PUBLICATION;

drop table PUBLIC_STATUS;

CREATE TABLE PUBLICATION(
    public_id		        VARCHAR(32)		NOT NULL PRIMARY KEY,
    appuser_id              VARCHAR(32)		NOT NULL,
    user_type_id            VARCHAR(32)		NOT NULL,
    public_status_id        VARCHAR(32)		NOT NULL,
    created_at               DATE,
    update_at               DATE,
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
    update_at               DATE,
    deleted                 NUMERIC(1)
);

--CONSTRAINT
ALTER TABLE PUBLICATION ADD CONSTRAINT appuser_id_fk FOREIGN KEY(appuser_id) REFERENCES APP_USER(appuser_id);
ALTER TABLE PUBLICATION ADD CONSTRAINT user_type_id_fk FOREIGN KEY(user_type_id) REFERENCES USER_TYPE(user_type_id);

--VERIFICACION DE USER_TYPE MECANICO
alter table PUBLICATION
add constraint mechanic_verify
check (user_type_id='MEC');

--INSERT TABLA PUBLICATION
insert into PUBLICATION --publicacion 1
(public_id,appuser_id,user_type_id,public_status_id,created_at,update_at,deleted,title,public_desc,schedule,services,bussiness_name,address,comuna,region,landline,mobile_number,email,views)
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
(public_id,appuser_id,user_type_id,public_status_id,created_at,update_at,deleted,title,public_desc,schedule,services,bussiness_name,address,comuna,region,landline,mobile_number,email,views)
values
    (SYS_GUID(),--public_id
    '7CD5B7769DF85CEFE034080020825436',--mecha_id
    'VEN',--user_type_id
    'ACT',--public_status_id
    to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0,--deleted
    'Kaiser Lubricentro',--title
    'Kaiser Lubricentro un taller automotriz dedicado a dar servicios de lubricación en general tanto para vehículos bencineros como diesel. cambiamos el aceite de motor, caja de cambio, mecánica o automática, diferenciales, cajas de transparencias, dirección, hidráulica, etc.',--public_desc
    'De 9:30 a 18:30, de lunes a viernes',--schedule
    'CAMBIO DE ACEITE,El cambio de aceite se debe realizar cada 5.000 kms o cada 10.000 kms dependiento del aceite que se utilice.
     AFINAMIENTO, El afinamiento se debe realizar cada 15.000 o 20.000 kms. (Una vez al año aproximadamente)
     MECANICA,Mecánica express consiste en trabajos que tardan desde 2 hrs. a 1 día o 2 dependiendo de lo que necesite.
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

--INSERT TABLA PUBLIC_STATUS
insert into PUBLIC_STATUS values('PEN','PENDING',to_date ('11-abril-2020','DD-MON-YYYY '),to_date ('11-abril-2020','DD-MON-YYYY'),0);--pending
insert into PUBLIC_STATUS values('ACT','ACTIVE',to_date ('11-abril-2020','DD-MON-YYYY '),to_date ('11-abril-2020','DD-MON-YYYY'),0);--active
insert into PUBLIC_STATUS values('DEB','DEBT',to_date ('11-abril-2020','DD-MON-YYYY '),to_date ('11-abril-2020','DD-MON-YYYY'),0);--debt(MOROSA)
insert into PUBLIC_STATUS values('INA','INACTIVE',to_date ('11-abril-2020','DD-MON-YYYY '),to_date ('11-abril-2020','DD-MON-YYYY'),0);--unactive



commit;
