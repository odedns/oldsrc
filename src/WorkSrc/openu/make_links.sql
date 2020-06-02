CREATE FUNCTION [dbo].[make_links] (@node int)  
RETURNS varchar(200)  AS  
BEGIN 
declare @idata varchar(200)
declare @myString varchar(200)
DECLARE t_c CURSOR
FOR
   select data as idata from data_bin where data_bin.obj_id=@node and 
	prop_id=3 order by prop_inst
OPEN t_c

FETCH NEXT FROM t_c INTO @idata
set @mystring=''
WHILE (@@FETCH_STATUS = 0)
BEGIN
 set @mystring = @mystring +  '<A HREF="/opus/Static/Binaries/Milon/'+rtrim(ltrim(@idata)) + '"> ���� ������ </A><br>'
FETCH NEXT FROM t_c INTO @idata
END
CLOSE t_c
DEALLOCATE t_c
return ( @mystring )
END

