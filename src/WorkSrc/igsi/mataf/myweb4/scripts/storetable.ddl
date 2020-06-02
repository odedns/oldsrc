
------------------------------------------------

-- DDL Statements for table "ODEDN   "."STORETABLE"

------------------------------------------------

 

 CREATE TABLE STORETABLE  (

		  "DSERECID" INTEGER NOT NULL , 

		  "DSERECMK" INTEGER NOT NULL , 

		  "ACCOUNT_NUMBER" CHAR(14) , 

		  "AMOUNT" INTEGER , 

		  "DESCRIPTION" VARCHAR(50) , 

		  "THE_DATE" DATE )   

		 IN "USERSPACE1" ; 



-- DDL Statements for primary key on Table "ODEDN   "."STORETABLE"



ALTER TABLE "ODEDN   "."STORETABLE" 

	ADD PRIMARY KEY

		("DSERECID");






