DROP TABLE mataf_data_field;
CREATE TABLE mataf_data_field (
	id varchar(30) NOT NULL,
	shortName varchar(10) NOT NULL,
	idNumber varchar(10) NOT NULL,
	description varchar(50),
	group varchar(50),
	value varchar(50),
	length varchar(10),
	trimZero varchar(10),
	nullEnded varchar(10),
	blankZero varchar(10),
	constValue varchar(20),
	type varchar(20)
);

ALTER TABLE ADD CONSTRAINT mataf_df_pk PRIMARY_KEY (id);
CREATE INDEX mataf_df_inx ON mataf_data_field(id);

DROP TABLE mataf_scr_attrs;
CREATE TABLE mataf_scr_attrs(
		id varchar(30) NOT NULL,
		autotab varchar(10),
		chain varchar(10),
		checkAlways varchar(10),
		checkEmpty varchar(10), 
		checksumFields varchar(80),
		commaEdit varchar(10),
		entryFunc varchar(20),
		exitFunc varchar(20),
		externDevOnly varchar(10),
		flagField varchar(60),
		helpText varchar(40),
		intableSlice varchar(20),
		intableTable varchar(20),
		listOfValues varchar(80),
		minChars varchar(10),
		rangeFieldFrom varchar(80),
		rangeFieldTo varchar(80),
		rangeValues varchar(130),
		select1Field varchar(20),
		select1Slice varchar(10),
		select1Table varchar(20),
		select2Field varchar(20),
		select2Slice varchar(10),
		select2Table varchar(20),
		slashEdit varchar(10),
		intableFilter varchar(10),
		intableCheck varchar(10),
		checkSum varchar(10),
		helpPanel varchar(20),
		initField varchar(10),
		externDevice varchar(20),
		select1View varchar(10),
		select2View varchar(10),
		select1Filter varchar(10),
		select2Filter varchar(10),
		initValue varchar(20)
);

ALTER TABLE ADD CONSTRAINT mataf_scr_pk PRIMARY_KEY (id);
CREATE INDEX mataf_scr_inx ON mataf_scr_attrs(id);

DROP TABLE mataf_record;
CREATE TABLE mataf_record (
	id varchar(20) NOT NULL,
	shortName varchar(10) NOT NULL,
	idNumber varchar(10),
	description varchar(50),
	type varchar(20),
	conversion varchar(30)
);
ALTER TABLE ADD CONSTRAINT mataf_record_pk PRIMARY_KEY (id);
CREATE INDEX mataf_rec_inx ON mataf_record(id);


DROP TABLE mataf_record_details;
CREATE TABLE mataf_record_details (
	id varchar(20) NOT NULL,
	refId varchar(20) NOT NULL,
    entryFunc varchar(20),
    evsActive varchar(10),
	exitFunc varchar(20),
    fvsActive varchar(10),
    checkType varchar(20),
    blankZero varchar(10),
    trimZero varchar(10),
    isEbcedic varchar(10),
	nullEnded varchar(10),
	mashovFld varchar(20),
	type varchar(20),
	length varchar(10),
	order varchar(10),
	engStructName varchar(20),
	dictCode varchar(10)
);
ALTER TABLE ADD CONSTRAINT mataf_rd_pk PRIMARY_KEY (id, refId);
CREATE INDEX mataf_rd_inx1 ON mataf_record_details(id);
CREATE INDEX mataf_rd_inx2 ON mataf_record_details(refId);
