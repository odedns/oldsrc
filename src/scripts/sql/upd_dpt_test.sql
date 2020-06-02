
insert into dpt1057 a select * from pilot.dpt1057@dbpr b
where b.kod_shgia like '2200%' and not exists
(select c.kod_shgia from dpt1057 c where c.kod_shgia = b.kod_shgia)


insert into dpt1024 a select * from pilot.dpt1024@dbpr b
where b.kod_sibat_sgira not exists 
( select c.kod_sibat_sgira from dpt1024 where c.kod_sibat_sgira = b.kod_sibat_sgira)
