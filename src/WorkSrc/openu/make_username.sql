CREATE FUNCTION [dbo].[make_username] (@user_id int)  
RETURNS varchar(2000)  AS  
BEGIN 
declare @idata varchar(2000)
DECLARE t_c CURSOR
FOR
   select username as idata from openu_users where id=@user_id
OPEN t_c

FETCH NEXT FROM t_c INTO @idata

CLOSE t_c
DEALLOCATE t_c
return ( @idata )
END



