select table_name,column_name from user_tab_columns where table_name like 'DPT%'
and column_id in (7,8) and column_name != 'LAST_UPDATE_DATE' order by table_name,column_id
