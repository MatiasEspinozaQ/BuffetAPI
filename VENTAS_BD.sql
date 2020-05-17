DROP TABLE SALE CASCADE CONSTRAINTS;

DROP TABLE SALE_STATUS CASCADE CONSTRAINTS;

DROP TABLE SALE_PROVISION CASCADE CONSTRAINTS;


CREATE TABLE SALE(
    sale_id         VARCHAR(32)  NOT NULL PRIMARY KEY,
    seller_id       VARCHAR(50)	 NOT NULL,
    cashier_id      VARCHAR(50)	 NOT NULL,
    appuser_id      VARCHAR(50)	 NOT NULL,
    sale_status_id  VARCHAR(50)	 NOT NULL,
    date_order      DATE,
    sale_date       DATE,
    code            NUMERIC(15) NOT NULL,
    payment_method  VARCHAR(30) NOT NULL,
    subtotal        NUMERIC(12) NOT NULL,
    total           NUMERIC(12) NOT NULL,
    updated_at		DATE		NOT NULL,
    created_at		DATE		NOT NULL,
    deleted			NUMERIC(1)	NOT NULL
);

CREATE TABLE SALE_STATUS(
    sale_status_id 	    VARCHAR(32)		        NOT NULL PRIMARY KEY,
    name				VARCHAR(50)		        NOT NULL,
    updated_at		    DATE					NOT NULL,
    created_at			DATE					NOT NULL,
    deleted				NUMERIC(1)			    NOT NULL
);

CREATE TABLE SALE_PROVISION(
    provision_id    VARCHAR(32)  NOT NULL PRIMARY KEY,
    sale_id         VARCHAR(32)  NOT NULL,
    product_id      VARCHAR(32)  NOT NULL,
    serv_id         VARCHAR(32),
    quantity        NUMERIC(3)   NOT NULL,
    unit_price      NUMERIC(12)  NOT NULL,
    total           NUMERIC(12)  NOT NULL,
    updated_at		DATE		 NOT NULL,
    created_at		DATE		 NOT NULL,
    deleted			NUMERIC(1)	 NOT NULL
);  


--CONSTRAINT
ALTER TABLE SALE ADD CONSTRAINT sale_status_id_fk FOREIGN KEY(sale_status_id) REFERENCES SALE_STATUS(sale_status_id);
ALTER TABLE SALE ADD CONSTRAINT appuser_fk FOREIGN KEY(appuser_id) REFERENCES APP_USER(appuser_id);

ALTER TABLE SALE_PROVISION ADD CONSTRAINT product_id_fk FOREIGN KEY(product_id) REFERENCES PRODUCT(product_id);
ALTER TABLE SALE_PROVISION ADD CONSTRAINT sale_id_fk FOREIGN KEY(sale_id) REFERENCES SALE(sale_id);

insert into SALE_STATUS values('PEN','Pendiente',   to_date ('15-05-2020','DD-MM-YYYY '),to_date ('15-05-2020','DD-MM-YYYY'),0);
insert into SALE_STATUS values('PAG','Pagado',      to_date ('15-05-2020','DD-MM-YYYY '),to_date ('15-05-2020','DD-MM-YYYY'),0);
insert into SALE_STATUS values('CAN','Cancelado',   to_date ('15-05-2020','DD-MM-YYYY '),to_date ('15-05-2020','DD-MM-YYYY'),0);

--venta 1
INSERT INTO SALE VALUES
    ('9DB8539940214DE2BDE44462E670D7DB',--sale_id
    '701B00A660584D81BDF9C9B6E61CD9E0',--seller_id
    '5093234C73AE4954BB6DD7E841FEEE12',--cashier_id
    '70BA44E0EC9A42018FA0D9465CC695B5',--user_id
    'PEN',--sale_status_id
    to_date('15-05-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--date_order
    to_date('15-05-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--sale_date
    523698556740210,--code
    'Tarjeta Debito',--payment_method
    30000,--subtotal
    30000,--total
    to_date ('15-05-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('15-05-2020 11:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0--deleted
);

--venta 2
INSERT INTO SALE VALUES
    ('176FD7BB0CDF4F2F910FEF5EE4ED4F59',--sale_id
    '85B502163CEC418BA24D8009C1113E1D',--seller_id
    '5093234C73AE4954BB6DD7E841FEEE12',--cashier_id
    '70BA44E0EC9A42018FA0D9465CC695B5',--user_id
    'PAG',--sale_status_id
    to_date('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--date_order
    to_date('15-05-2020 12:05 AM','DD-MM-YYYY HH:MI AM'),--sale_date
    523698556740365,--code
    'Tarjeta Credito',--payment_method
    53000,--subtotal
    53000,--total
    to_date ('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0--deleted
);

--venta 3
INSERT INTO SALE VALUES
    ('0B9104C3B9FE48C5B61B436C50042471',--sale_id
    '8FCD88353380472A8FDD7DC5FBDB2C7A',--seller_id
    '5093234C73AE4954BB6DD7E841FEEE12',--cashier_id
    '70BA44E0EC9A42018FA0D9465CC695B5',--user_id
    'CAN',--sale_status_id
    to_date('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--date_order
    to_date('15-05-2020 12:05 AM','DD-MM-YYYY HH:MI AM'),--sale_date
    523698556740365,--code
    'Tarjeta Credito',--payment_method
    53000,--subtotal
    53000,--total
    to_date ('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0--deleted
);

INSERT INTO SALE_PROVISION VALUES(
    SYS_GUID(),--provision_id
    '9DB8539940214DE2BDE44462E670D7DB',--sale_id
    'D9CCD278B8B849788F1F4FF6E074B499',--product_id
    'A7515A96926141479DEC462D7CC647F0',--serv_id
    2,--quantity
    15000,--unit_price
    30000,--total
    to_date ('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0--deleted
);

INSERT INTO SALE_PROVISION VALUES(
    SYS_GUID(),--provision_id
    '176FD7BB0CDF4F2F910FEF5EE4ED4F59',--sale_id
    'BB5801A8406F49CC953A277F918E770D',--product_id
    '09F68676182ACF8AE040578CB20B7491',--serv_id
    4,--quantity
    35000,--unit_price
    140000,--total
    to_date ('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--create_at
    to_date ('15-05-2020 12:00 AM','DD-MM-YYYY HH:MI AM'),--update_at
    0--deleted
);

commit;
