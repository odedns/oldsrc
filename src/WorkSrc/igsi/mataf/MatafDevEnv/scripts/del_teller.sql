delete from instances where parent=(
select timestamp  from groups  a where a.name='Teller' and a.parent=
(select  timestamp from groups b where b.name='Fields')
)

delete from instances where parent=(
select  timestamp  from groups  a where a.name='H' and a.parent=
(select   timestamp from groups b where b.name='Teller' and b.parent=
(select timestamp from groups c where c.name='Records'))
)

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Teller'  and a.parent=
(select timestamp from groups c where c.name='Fields'))

delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Teller' and b.parent=
(select timestamp from groups c where c.name='Fields'))
)

