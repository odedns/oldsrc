
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Other' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);

delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Global' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);

delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Teller' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Tiful' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Queries' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);

delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Common' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Pikdonot' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);

delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Cheshbon' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='NiarotErech' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Matach' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='EmdatLakoach' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);

delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Halvaa' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Pratim' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Shivuk' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);
delete from instances where parent in (
select  timestamp  from groups  a where  a.parent=
(select   timestamp from groups b where b.name='Tashtit' and b.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'))
);



delete from groups where parent =(
select  timestamp  from groups  a where a.name='Teller'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Tiful'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Other'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Queries'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Common'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Pikdonot'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Cheshbon'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));
delete from groups where parent =(
select  timestamp  from groups  a where a.name='NiarotErech'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Matach'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='EmdatLakoach'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Halvaa'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Pratim'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Shivuk'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Tashtit'  and a.parent=
(select timestamp from groups c where c.name='Fields' and c.description='fields'));





delete from groups where parent =(
select  timestamp  from groups  a where a.name='Teller'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Tiful'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Other'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Queries'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Common'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Pikdonot'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Cheshbon'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));
delete from groups where parent =(
select  timestamp  from groups  a where a.name='NiarotErech'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Matach'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='EmdatLakoach'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Halvaa'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Pratim'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Shivuk'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));

delete from groups where parent =(
select  timestamp  from groups  a where a.name='Tashtit'  and a.parent=
(select timestamp from groups c where c.name='Records' and c.description='records'));



