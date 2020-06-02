DROP TABLE mataf_oe;
CREATE TABLE mataf_oe (
	instance varchar(120) NOT NULL,
	owner varchar(20) NOT NULL,
	ts TIMESTAMP
);
ALTER TABLE ADD CONSTRAINT mataf_oe_pk PRIMARY_KEY (instance);
CREATE INDEX mataf_oed_inx ON mataf_oe(instance);


