sourceList
===
select * from TFW_PARAMETER

list
===
select p.*,e.name STATUSNAME from TFW_PARAMETER p left join (select num,name from tfw_dict where code=901) e on p.status=e.num 