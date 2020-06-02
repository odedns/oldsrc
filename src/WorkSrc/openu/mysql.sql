declare @p integer
declare @i integer
declare t_c cursor
for	select obj_id as i, prop_id as p from data_bin
open t_c
fetch next from t_c into @i, @p
while @@fetch_status=0
begin
	print 'go it '
	print @i
	fetch next from t_c into @i, @p
end
close t_c
deallocate t_c
go

