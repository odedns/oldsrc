CREATE TABLE FWPILOT.T_SEX ( 
	ID INTEGER NOT NULL , 
	NAME VARGRAPHIC(50) CCSID 13488 NOT NULL , 
	CREATION_USER INTEGER NOT NULL , 
	CREATION_DATE TIMESTAMP NOT NULL , 
	UPDATING_USER INTEGER NOT NULL , 
	UPDATING_DATE TIMESTAMP NOT NULL , 
	CONSTRAINT FWPILOT.T_SEX_PK PRIMARY KEY( ID ) ) ; 


CREATE TABLE FWPILOT.T_FAMILY_STATUS ( 
	ID INTEGER NOT NULL , 
	NAME VARGRAPHIC(50) CCSID 13488 NOT NULL , 
	CREATION_USER INTEGER NOT NULL , 
	CREATION_DATE TIMESTAMP NOT NULL , 
	UPDATING_USER INTEGER NOT NULL , 
	UPDATING_DATE TIMESTAMP NOT NULL , 
	CONSTRAINT FWPILOT.T_FAMILY_STATUS_PK PRIMARY KEY( ID ) ) ; 


CREATE TABLE FWPILOT.T_RELATED_TYPE ( 
	ID INTEGER NOT NULL , 
	NAME VARGRAPHIC(50) CCSID 13488 NOT NULL , 
	CREATION_USER INTEGER NOT NULL , 
	CREATION_DATE TIMESTAMP NOT NULL , 
	UPDATING_USER INTEGER NOT NULL , 
	UPDATING_DATE TIMESTAMP NOT NULL , 
	CONSTRAINT FWPILOT.T_RELATED_TYPE_PK PRIMARY KEY( ID ) ) ; 



  