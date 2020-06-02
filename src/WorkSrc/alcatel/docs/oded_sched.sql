-- Scriptfile to create schema for Oracle
-- Process this script using SQL*Plus
-- 1. Replace all occurrances of HSCHED_ to the Table Prefix you will use in the
--    configured Scheduler resource.
-- 2. Replace all occurrances of USERS with a valid tablespace that was 
--    created by the createTablesapceOracle.ddl script.
-- Example: 
--  o  sqlplus username/password@mydb @createSchemaOracle.ddl
--  o  or, at the sqlplus prompt, enter
--     SQL> @createSchemaOracle.ddl

DROP TABLE "TB_HSCHED_ODED_TASK" ;
DROP TABLE "TB_HSCHED_ODED_TREG";
DROP TABLE "TB_HSCHED_ODED_LMGR";
DROP TABLE "TB_HSCHED_ODED_LMPR";
DROP TABLE "TB_HSCHED_ODED_TASK_INFO";


CREATE TABLE "TB_HSCHED_ODED_TASK" 
(
    "TASKID"                  NUMBER(19)            NOT NULL, 
    "VERSION"                 VARCHAR2(5)           NOT NULL, 
    "ROW_VERSION"             NUMBER(10)            NOT NULL, 
    "TASKTYPE"                NUMBER(10)            NOT NULL, 
    "TASKSUSPENDED"           NUMBER(1)             NOT NULL, 
    "CANCELLED"               NUMBER(1)             NOT NULL, 
    "NEXTFIRETIME"            NUMBER(19)            NOT NULL, 
    "STARTBYINTERVAL"         VARCHAR2(254), 
    "STARTBYTIME"             NUMBER(19), 
    "VALIDFROMTIME"           NUMBER(19), 
    "VALIDTOTIME"             NUMBER(19), 
    "REPEATINTERVAL"          VARCHAR2(254), 
    "MAXREPEATS"              NUMBER(10)            NOT NULL, 
    "REPEATSLEFT"             NUMBER(10)            NOT NULL, 
    "TASKINFO"                BLOB,     
    "NAME"                    VARCHAR2(254), 
    "AUTOPURGE"               NUMBER(10)            NOT NULL, 
    "FAILUREACTION"           NUMBER(10), 
    "MAXATTEMPTS"             NUMBER(10), 
    "QOS"                     NUMBER(10), 
    "PARTITIONID"             NUMBER(10), 
    "OWNERTOKEN"              VARCHAR2(200)         NOT NULL,
    "CREATETIME"              NUMBER(19)            NOT NULL, 
    PRIMARY KEY ( "TASKID" )
)  
    TABLESPACE "USERS";

CREATE INDEX "TB_HSCHED_ODED_TASK_IDX1" 
    ON "TB_HSCHED_ODED_TASK"
(
    "TASKID","OWNERTOKEN"
) 
    TABLESPACE "USERS";

CREATE INDEX "TB_HSCHED_ODED_TASK_IDX2" 
    ON "TB_HSCHED_ODED_TASK"
(
    "NEXTFIRETIME" ASC, "REPEATSLEFT", "PARTITIONID"
) 
    TABLESPACE "USERS";

CREATE TABLE "TB_HSCHED_ODED_TREG"
(
    "REGKEY"                                   VARCHAR2(254)         NOT NULL ,
    "REGVALUE"                                 VARCHAR2(254)         ,
    PRIMARY KEY ( "REGKEY" )
)
    TABLESPACE "USERS";


-- Lease Manager
CREATE TABLE "TB_HSCHED_ODED_LMGR"
(
	"LEASENAME"              VARCHAR2(254) NOT NULL,
	"LEASEOWNER"             VARCHAR2(254),
	"LEASE_EXPIRE_TIME"      NUMBER(19),
	"DISABLED"               VARCHAR2(254), 
    PRIMARY KEY ( "LEASENAME" )
)  
    TABLESPACE "USERS";

CREATE TABLE "TB_HSCHED_ODED_LMPR"
(
	"LEASENAME"              VARCHAR2(254) NOT NULL,
	"NAME"                   VARCHAR2(254) NOT NULL,
	"VALUE"                  VARCHAR2(254) NOT NULL
)  
    TABLESPACE "USERS";

CREATE INDEX "TB_HSCHED_ODED_LMPR_IDX1" ON "TB_HSCHED_ODED_YB_LMPR"
(
	"LEASENAME", "NAME"
)  
    TABLESPACE "USERS";
    


CREATE TABLE "TB_HSCHED_ODED_TASK_INFO"
(
  TASKID       				NUMBER(19)         NOT NULL,
  TASK_CLASS_NAME           VARCHAR(200)	   NOT NULL
)
TABLESPACE "USERS";

