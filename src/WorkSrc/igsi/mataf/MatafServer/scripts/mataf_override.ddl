DROP TABLE MATAF_OVERRIDE;
CREATE TABLE MATAF_OVERRIDE (
	IDNUMBER varchar(20) NOT NULL,
	TRXID varchar(20) NOT NULL,
	STATUS integer,	
	REASON varchar(80)
);

ALTER TABLE mataf_override ADD CONSTRAINT mataf_override_pk PRIMARY KEY (IDNUMBER);
CREATE INDEX mataf_override_inx ON mataf_override(IDNUMBER);
