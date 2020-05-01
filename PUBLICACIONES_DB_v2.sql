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

--INSERT TABLA PUBLIC_STATUS
insert into PUBLIC_STATUS values('PEN','Pendiente', to_date('11-04-2020','DD-MM-YYYY '),to_date ('11-04-2020','DD-MM-YYYY'),0);--pending
insert into PUBLIC_STATUS values('ACT','Activo',    to_date('11-04-2020','DD-MM-YYYY '),to_date ('11-04-2020','DD-MM-YYYY'),0);--active
insert into PUBLIC_STATUS values('DEB','Con deuda', to_date('11-04-2020','DD-MM-YYYY '),to_date ('11-04-2020','DD-MM-YYYY'),0);--debt(MOROSA)
insert into PUBLIC_STATUS values('INA','Inactivo',  to_date('11-04-2020','DD-MM-YYYY '),to_date ('11-04-2020','DD-MM-YYYY'),0);--unactive

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
    'Servicio Suzuki.Chevrolet,Mazda, Mantenciones de kilometraje y desabolladura.Personal especializado en las marcas.Garant�a por trabajos realizados.20 a�os de experiencia en el rubro.',--public_desc
    'De 8:30 a 19:30, abierto toda la semana',--schedule
    'Mec�nica general, Mantenciones preventivas, Alineaci�n, Balanceo, Frenos, Embrague, Afinamiento, Scanner, Limpieza de tapiz, Cambio de aceite, Tren delantero, Emergencia 24 horas,
    Simunizado y encerado, Diagnostico Computarizado',--services
    'Automotriz J.D.C',--bussiness_name
    'Calle Fern�ndez Concha 146, �u�oa, Regi�n Metropolitana',--adress
    '�u�oa',--comuna
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
    'MEC',--user_type_id
    'ACT',--public_status_id
    to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('05-04-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0,--deleted
    'Kaiser Lubricentro',--title
    'Kaiser Lubricentro un taller automotriz dedicado a dar servicios de lubricaci�n en general tanto para veh�culos bencineros como diesel. cambiamos el aceite de motor, caja de cambio, mec�nica o autom�tica, diferenciales, cajas de transparencias, direcci�n, hidr�ulica, etc.',--public_desc
    'De 9:30 a 18:30, de lunes a viernes',--schedule
    'CAMBIO DE ACEITE,El cambio de aceite se debe realizar cada 5.000 kms o cada 10.000 kms dependiento del aceite que se utilice.
     AFINAMIENTO, El afinamiento se debe realizar cada 15.000 o 20.000 kms. (Una vez al a�o aproximadamente)
     MECANICA, Mec�nica express consiste en trabajos que tardan desde 2 hrs. a 1 d�a o 2 dependiendo de lo que necesite.
     ACCESORIOS,Accesorios para diferentes cosas del auto, dependiendo de lo que necesite.',--services
    'Kaiser Lubricentro',--bussiness_name
    'Avenida Col�n 2878,Valpara�so, Regi�n V',--adress
    'Valpara�so',--comuna
    'Regi�n V',--region
    '(32)2492905',--landline
    '0',--mobile_number
    'KaiserContacto@gmail.com',--email
    65--views
);

commit;