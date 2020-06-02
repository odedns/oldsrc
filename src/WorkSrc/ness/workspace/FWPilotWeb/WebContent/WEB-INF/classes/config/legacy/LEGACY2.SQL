
--  Generate SQL 

--  Version:                   	V5R2M0 020719 

--  Generated on:              	07/11/04 13:49:44 

--  Relational Database:       	S65D7C7C 

--  Standards Option:          	DB2 UDB AS/400 

  

drop PROCEDURE FWPILOT.SP_LEGACY3;

CREATE PROCEDURE FWPILOT.SP_LEGACY3 ( 

	IN VAR01 INTEGER , 

	OUT VAR02 INTEGER ) 

	DYNAMIC RESULT SETS 4 

	LANGUAGE SQL 

	SPECIFIC FWPILOT.SP_LEGACY3 

	NOT DETERMINISTIC 

	MODIFIES SQL DATA 

	CALLED ON NULL INPUT 

	P1 : BEGIN 

		 

 -- Declare customer cursor 

DECLARE CURSOR1 CURSOR FOR 

SELECT ID , FIRST_NAME , LAST_NAME , SEX , SMOKING , CHAR ( BIRTH_DATE ) AS BIRTH_DATE_STR , BIRTH_DATE , TELEPHONE 
FROM FWPILOT.CUSTOMER 
order by id;



 -- Declare customer professions cursor 

DECLARE CURSOR2 CURSOR FOR 

select CUSTOMER_ID, PROFESSION_ID from FWPILOT.CUST_PROF
 order by CUSTOMER_ID;



 -- Declare customer addresses cursor 

DECLARE CURSOR3 CURSOR FOR 

select ID, PARENT_ID, CITY, STREET, STREET_NUMBER, MAIN, TELEPHONE, FAX 
FROM FWPILOT.ADDRESS 
WHERE TYPE = 1 
order by PARENT_ID, ID;

DECLARE CURSOR4 CURSOR FOR 

select ID, ADD_ID, DESCRIPTION
FROM FWPILOT.TELEPHONE 
order by ADD_ID, ID ;
			 

--open cursors

OPEN CURSOR1;

OPEN CURSOR2;

OPEN CURSOR3;

OPEN CURSOR4;
		 
SELECT COUNT ( * ) INTO VAR02 FROM HARELTEST . CUSTOMER ; 

		 

END P1 ; 

  

