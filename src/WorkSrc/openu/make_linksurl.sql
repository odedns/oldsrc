CREATE FUNCTION [dbo].[make_linksurl] (@node int)  
RETURNS varchar(200)  AS  
BEGIN 
declare @idata varchar(200)
declare @idata2 varchar(200)
declare @prop_inst varchar(20)
declare @myString varchar(200)
DECLARE t_c CURSOR
FOR
   select data as idata,prop_inst as prop_inst from data_char where data_char.obj_id=@node and 
	prop_id=2 order by prop_inst
OPEN t_c

FETCH NEXT FROM t_c INTO @idata,@prop_inst
set @mystring=''
WHILE (@@FETCH_STATUS = 0)
BEGIN
	DECLARE roy CURSOR
	FOR
	   select data as idata2 from data_char where data_char.obj_id=@node and 
		prop_id=1  and prop_inst=@prop_inst

	OPEN roy
	FETCH NEXT FROM roy INTO @idata2
	CLOSE roy
	DEALLOCATE roy
	if @idata2 is null
		set @idata2=@idata
	 set @mystring = @mystring +  '<A HREF="'+rtrim(ltrim(@idata)) + '">'+rtrim(ltrim(@idata2))+'</A><BR>'

FETCH NEXT FROM t_c INTO @idata,@prop_inst
END
CLOSE t_c
DEALLOCATE t_c
return ( @mystring )
END



