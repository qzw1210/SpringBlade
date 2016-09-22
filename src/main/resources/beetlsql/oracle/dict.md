list
===
select d.*,(select name from tfw_dict  where id=d.pId) PNAME from tfw_dict d 

diy
===
select ID,pId as PID,name as TEXT from  TFW_DICT 