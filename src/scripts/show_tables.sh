#!/bin/sh 

echo "Retrieving table name in database ..."
sqlplus cctm/cctm  << EOF
select table_name from user_tables;
EOF
