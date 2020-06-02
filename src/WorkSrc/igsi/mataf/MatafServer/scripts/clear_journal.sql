delete from SLIKA_JOURNAL_1
go
delete from SLIKA_JOURNAL_2
go
delete from SLIKA_JOURNAL_3
go

update DSEJOUCT set WRAP_N=0 , LAST_DATE=null WHERE ENTITY_NAME='SLIKA_JOURNAL'
go