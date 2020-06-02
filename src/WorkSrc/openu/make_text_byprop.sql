CREATE FUNCTION [dbo].[make_text_by_prop] (@node int,@prop_id int,@prop_inst int)  
RETURNS varchar(2000)  AS  
BEGIN 
declare @idata varchar(2000)
set @idata=''
DECLARE t_c CURSOR
FOR
   select data as idata from data_text where data_text.obj_id=@node and 
	prop_id=@prop_id and prop_inst=@prop_inst
OPEN t_c

FETCH NEXT FROM t_c INTO @idata

CLOSE t_c
DEALLOCATE t_c
return ( @idata )
END



