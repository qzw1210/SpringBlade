list
===
select d.*,(select simpleName from tfw_dept  where id=d.pId) PNAME from tfw_dept d 